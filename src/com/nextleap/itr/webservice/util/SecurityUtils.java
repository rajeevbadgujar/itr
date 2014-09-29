package com.nextleap.itr.webservice.util;
import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSAuthInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.SerializationUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import sun.security.pkcs11.wrapper.CK_C_INITIALIZE_ARGS;
import sun.security.pkcs11.wrapper.PKCS11;
import sun.security.x509.X509CertImpl;

import com.nextleap.itr.webservice.beans.ItrInputs;
import com.nextleap.itr.webservice.constants.ITRConstants;
/**
 * @author rajeev
 *
 */
public class SecurityUtils {
	public static final long CKF_OS_LOCKING_OK = 0x00000002L;
	public static class PrivateKeyAndCertChain {
		public Certificate[] certChain;
		public PrivateKey privateKey;
		public Certificate certificate;
	}
	/**
	 * Loads a PKCS12 KeyStore from a PFX file.
	 * @param aFileName
	 * @param aKeyStorePasswd
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	private static KeyStore loadKeyStoreFromPFXFile(String aFileName, String aKeyStorePasswd)
			throws GeneralSecurityException, IOException {
		KeyStore keyStore = KeyStore.getInstance(ITRConstants.PKCS12_KEYSTORE_TYPE);
		FileInputStream keyStoreStream = new FileInputStream(aFileName);
		char[] password = aKeyStorePasswd.toCharArray();
		keyStore.load(keyStoreStream, password);
		return keyStore;
	}
	/**
	 * Loads a PKCS11 KeyStore from a Hard Token.
	 * @param hardTokenPin
	 * @return
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws GeneralSecurityException
	 */
	private static KeyStore loadKeyStoreFromHardToken(String hardTokenPin,String hardwareType) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, Exception {
		KeyStore keyStore = null;
		File f = null;
		FileWriter writer = null;
		try {
			URL dllUrl = new URL("file:///" + getTokenVendor(hardwareType).getDriverInstallPath());
			File dllFile = new File(URLDecoder.decode(dllUrl.getPath(),"UTF-8"));
			CK_C_INITIALIZE_ARGS initArgs = new CK_C_INITIALIZE_ARGS();
			initArgs.flags = CKF_OS_LOCKING_OK;
			PKCS11 tmpPKCS11 = null;
			tmpPKCS11 = PKCS11.getInstance(
					dllFile.getCanonicalPath(), "C_GetFunctionList", initArgs, false);
			long [] slots = tmpPKCS11.C_GetSlotList(true);
			if(slots !=null && slots.length>0){
				Boolean validToken = Boolean.FALSE;
				String notValidTokenMsg = "";
				for(long slot :slots){
					try{
						f= new File("temp.properties");
						writer= new FileWriter(f);
						writer.write("name = pkcs\nslot = "+slot+"\nlibrary="+dllFile.getCanonicalPath());
						writer.flush();
						FileInputStream fs = new FileInputStream(f);
						Provider prov = new sun.security.pkcs11.SunPKCS11(fs);
						Security.addProvider(prov);
						keyStore = KeyStore.getInstance(ITRConstants.PKCS11,prov);
						keyStore.load(null, hardTokenPin.toCharArray());
						validToken = Boolean.TRUE;
						break;
					}catch(Exception ex){
						validToken = Boolean.FALSE;
						notValidTokenMsg = ex.getMessage();
					}
				}
				if(!validToken){
					throw new Exception(notValidTokenMsg);
				}
			} else{
				throw new Exception("Not Able to read the Hardware token..Check if it is properly plugged in.");
			}
		} catch (ProviderException exception) {
			throw new Exception("No Hard Token found");
		}finally{
			if(writer!=null)
				writer.close();
			if(f!=null)
				f.delete();
		}
		return keyStore;
	}
	/**
	 * Gets Certificate, PrivateKey and CertificateChain from a KeyStore.
	 * @param aKeyStore
	 * @param aKeyPassword
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static PrivateKeyAndCertChain getPrivateKeyAndCertChain(KeyStore aKeyStore, String aKeyPassword)
			throws GeneralSecurityException {
		PrivateKeyAndCertChain result = new PrivateKeyAndCertChain();
		if (aKeyStore != null) {
			Enumeration<String> aliasesEnum = aKeyStore.aliases();
			if(aKeyStore.size() >0){
					while (aliasesEnum.hasMoreElements()) {
						String alias = (String) aliasesEnum.nextElement();

						Certificate certificate509 = aKeyStore.getCertificate(alias);
						if (!((X509Certificate)certificate509).getIssuerX500Principal().getName().equals(ITRConstants.CAC_INDIA)) {
							result.certChain = aKeyStore.getCertificateChain(alias);
							if(result.certChain == null){
								result.certChain =  new X509CertImpl[1];
								result.certChain[0] = certificate509;
							}
							result.privateKey = (PrivateKey) aKeyStore.getKey(alias, aKeyPassword.toCharArray());
							if(result.privateKey == null){
								 KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
						            kpg.initialize(512);
						            KeyPair keyPair = kpg.generateKeyPair();
						            result.privateKey = keyPair.getPrivate();
							}
							result.certificate = certificate509;
						}
					}
			}else{
				// Empty keyStore
				throw new KeyStoreException("The keystore is empty with no certificate!");
			}
		} else {
			throw new KeyStoreException("The keystore is empty!");
		}
		return result;
	}
	/**
	 * @return Base64-encoded ASN.1 DER representation of given X.509 certification
	 * chain.
	 */
	public static String encodeX509CertChainToBase64(Certificate[] aCertificationChain)
			throws CertificateException {
		List<? extends Certificate> certList = Arrays.asList(aCertificationChain);
		CertificateFactory certFactory =
				CertificateFactory.getInstance(ITRConstants.X509_CERTIFICATE_TYPE);
		CertPath certPath = certFactory.generateCertPath(certList);
		byte[] certPathEncoded = certPath.getEncoded(ITRConstants.CERT_CHAIN_ENCODING);
		String base64encodedCertChain = Base64.encode(certPathEncoded);
		return base64encodedCertChain;
	}
	/**
	 * @param aPrivateKey
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static String signature(PrivateKey aPrivateKey)
			throws GeneralSecurityException, IOException {
		Signature signatureAlgorithm = Signature.getInstance(ITRConstants.DIGITAL_SIGNATURE_ALGORITHM_NAME);
		signatureAlgorithm.initSign(aPrivateKey);
		byte[] digitalSignature = signatureAlgorithm.sign();
		String digitalSignatureStr = Base64.encode(digitalSignature);
		return digitalSignatureStr;
	}
	
	
	public static DITWSAuthInfo populateAuthInfo(ItrInputs input) throws GeneralSecurityException, IOException, Exception {
		return populateAndStoreAuthInfo(input,Boolean.FALSE);
	}
	/**
	 * @param cc
	 * @param signature
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public static DITWSAuthInfo populateAndStoreAuthInfo(ItrInputs input,Boolean toStore) throws GeneralSecurityException, IOException, Exception {
		DITWSAuthInfo authInfo = new DITWSAuthInfo();
		authInfo.setUserID(input.getEriUserId());
		authInfo.setPassword(input.getEriPassowrd());
		String cc;
		String signature;
		if(input.isUseSoftToken()){
			cc = loadCertificatChain(input.getHardTokenType());
			signature = loadPassword(input.getHardTokenType());
		}else{
			KeyStore ks = null;
			String password = "";
		 if(input.isGenerateSoftToken()) {
			 ks = loadKeyStoreFromHardToken(input.getHardTokenPin(),input.getHardTokenType());
			 password = input.getHardTokenPin();
		 }
		 else {
			 ks = loadKeyStoreFromPFXFile(input.getEriPfxFilePath(), input.getEriPfxFilePassword());
			 password = input.getEriPfxFilePassword();
		 }
		 PrivateKeyAndCertChain pkcc = getPrivateKeyAndCertChain(ks, password);
		  cc = encodeX509CertChainToBase64(pkcc.certChain);
		  signature = signature(pkcc.privateKey);
		}
		if(toStore){
			store(cc,signature,input.getHardTokenType());
		}
		authInfo.setCertChain(cc);
		authInfo.setSignature(signature);
		return authInfo;
	}
	/**
	 * @param inputs
	 * @param fileInputStream
	 * @return
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws GeneralSecurityException
	 * @throws MarshalException
	 * @throws XMLSignatureException
	 * @throws TransformerException
	 */
	public static Document signXml(ItrInputs inputs, InputStream fileInputStream)
			throws FileNotFoundException, SAXException, IOException, ParserConfigurationException,
			GeneralSecurityException, MarshalException, XMLSignatureException, TransformerException, Exception {
		// First, create the DOM XMLSignatureFactory that will be used to
		// generate the XMLSignature
		XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance(ITRConstants.DOM);
		// Instantiate the document to be signed.
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		Document xmlDocument = documentBuilderFactory.newDocumentBuilder().parse(fileInputStream);
		XMLStructure structure = new DOMStructure(xmlDocument.getDocumentElement());
		XMLObject documentObject = signatureFactory.newXMLObject(Collections.singletonList(structure),
				ITRConstants.ITR_TAG, null, null);
		// Next, create a Reference to a same-document URI that is an Object
		// element and specify the SHA1 digest algorithm
		Reference ref = signatureFactory.newReference("#" + ITRConstants.ITR_TAG,
				signatureFactory.newDigestMethod(DigestMethod.SHA1, null));
		// Create the SignedInfo
		SignedInfo signedInfo = signatureFactory.newSignedInfo(
				signatureFactory.newCanonicalizationMethod
				(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
						(C14NMethodParameterSpec) null),
						signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
						Collections.singletonList(ref));
		KeyStore keyStore = null;
		PrivateKeyAndCertChain keyAndCertChain = null;
		if(inputs.isHardToken()) {
			keyStore = loadKeyStoreFromHardToken(inputs.getHardTokenPin(),inputs.getHardTokenType());
			keyAndCertChain = getPrivateKeyAndCertChain(keyStore, inputs.getHardTokenPin());
		} else {
			keyStore = loadKeyStoreFromPFXFile(inputs.getXmlPfxFile(), inputs.getXmlPfxFilePassword());
			keyAndCertChain = getPrivateKeyAndCertChain(keyStore, inputs.getXmlPfxFilePassword());
		}
		// Create the KeyInfo containing the X509Data.
		KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
		List<Object> x509Content = new ArrayList<Object>(2);
		x509Content.add(((X509Certificate)(keyAndCertChain.certificate)).getSubjectX500Principal().getName());
		x509Content.add(keyAndCertChain.certificate);
		X509Data x509Data = keyInfoFactory.newX509Data(x509Content);
		KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
		// Create the XMLSignature, but don't sign it yet.
		XMLSignature signature = signatureFactory.newXMLSignature(signedInfo, keyInfo,
				Collections.singletonList(documentObject), null, null);
		// Create a DOMSignContext and specify the RSA PrivateKey and
		// location of the resulting XMLSignature's parent element.
		DOMSignContext domSignContext = new DOMSignContext(keyAndCertChain.privateKey, xmlDocument);
		// Marshal, generate, and sign the enveloped signature.
		signature.sign(domSignContext);
		return xmlDocument;
	}
	
	private static TokenVendor getTokenVendor(String tokenVendor){
		return TokenVendor.valueOf(tokenVendor);
	}
	
	/**
	 * Store the certificate chain and the password 
	 */
	private static Boolean store(String certificateChain, String signature,String hardwareTokenType) throws IOException{
		Boolean isSuccessful = Boolean.TRUE;
		byte[] sCertificateChain= SerializationUtils.serialize(certificateChain);
		byte[] ssignature= SerializationUtils.serialize(signature);
		TokenVendor token = getTokenVendor(hardwareTokenType);
		File f = new File(InputUtils.installDir+File.separator+token.getKey(),"certificate");
		f.getParentFile().mkdirs();
		File f1 = new File(InputUtils.installDir+File.separator+token.getKey(),"signature");
		f1.getParentFile().mkdirs();
		FileUtils.writeBytesToFile(f,sCertificateChain);
		FileUtils.writeBytesToFile(f1,ssignature);
		return isSuccessful;
	}
	
	private static String loadCertificatChain(String hardwareTokenType) throws Exception{
		    TokenVendor token = getTokenVendor(hardwareTokenType);
			File f = new File(InputUtils.installDir+File.separator+token.getKey(),"certificate");
			byte[] cc = FileUtils.readFromFileBytes(f);
			return	(String)SerializationUtils.deserialize(cc);		
	}

	
	private static String loadPassword(String hardwareTokenType) throws Exception{
		TokenVendor token = getTokenVendor(hardwareTokenType);
		File f = new File(InputUtils.installDir+File.separator+token.getKey(),"signature");
		byte[] pp = FileUtils.readFromFileBytes(f);
		return	(String)SerializationUtils.deserialize(pp);
}

}