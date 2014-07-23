package com.nextleap.itr.webservice.util;

import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSAuthInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
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
import java.util.Properties;

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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import sun.security.pkcs11.wrapper.CK_C_INITIALIZE_ARGS;
import sun.security.pkcs11.wrapper.PKCS11;
import sun.security.util.PropertyExpander;

import com.nextleap.itr.webservice.beans.ItrInputs;
import com.nextleap.itr.webservice.constants.ITRConstants;

/**
 * @author rajeev
 *
 */
public class SecurityUtils {
	
	public   final long  CKF_OS_LOCKING_OK                  = 0x00000002L;
	
		public   class PrivateKeyAndCertChain {
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
	private   KeyStore loadKeyStoreFromPFXFile(String aFileName, String aKeyStorePasswd) 
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
	private  KeyStore loadKeyStoreFromHardToken(String hardTokenPin) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, Exception {
		KeyStore keyStore = null;
		File f = null;
		FileWriter writer = null;
        try {
        	System.out.println(this.getClass().getResource("IDPrimePKCS11.dll").getPath());
        	
        	URL l = this.getClass().getResource("IDPrimePKCS11.dll");
        	System.out.println(URLDecoder.decode(l.getFile().substring(6)));
        	CK_C_INITIALIZE_ARGS initArgs = new CK_C_INITIALIZE_ARGS();
              initArgs.flags = CKF_OS_LOCKING_OK;
        	  
        	final PKCS11 tmpPKCS11 = PKCS11.getInstance(
        			 URLDecoder.decode(l.getFile().substring(1)), "C_GetFunctionList", initArgs,
                    false);
        	 long [] slots = tmpPKCS11.C_GetSlotList(true);
        	if(slots !=null && slots.length>0){
        	f=  new File("temp.properties");
        	writer= new FileWriter(f);
        	writer.write("name = pkcs\nslot = "+slots[0]+"\nlibrary="+URLDecoder.decode(l.getFile().substring(6)));
        	writer.flush();
        	FileInputStream fs = new FileInputStream(f);
        	Provider prov = new sun.security.pkcs11.SunPKCS11(fs);
	        Security.addProvider(prov);
	        keyStore = KeyStore.getInstance(ITRConstants.PKCS11);
			keyStore.load(null, hardTokenPin.toCharArray());
			System.out.println("KeyStore loaded");
        	}else{
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
	public   PrivateKeyAndCertChain getPrivateKeyAndCertChain(KeyStore aKeyStore, String aKeyPassword)
 			throws GeneralSecurityException {
		PrivateKeyAndCertChain result = new PrivateKeyAndCertChain();
		if (aKeyStore != null) {
			Enumeration<String> aliasesEnum = aKeyStore.aliases();
	 		/*result.certChain = new Certificate[aKeyStore.size()];*/
	  		while (aliasesEnum.hasMoreElements()) {
	 			String alias = (String) aliasesEnum.nextElement();
	 			Certificate certificate509 = aKeyStore.getCertificate(alias);
	 			if (!((X509Certificate)certificate509).getIssuerX500Principal().getName().equals(ITRConstants.CAC_INDIA)) {
	 				result.certChain = aKeyStore.getCertificateChain(alias);
	 				result.privateKey = (PrivateKey) aKeyStore.getKey(alias, aKeyPassword.toCharArray());
	 				result.certificate = certificate509;
	 			}
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
	public   String encodeX509CertChainToBase64(Certificate[] aCertificationChain) 
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
	public   String signature(PrivateKey aPrivateKey) 
			throws GeneralSecurityException, IOException {		
		Signature signatureAlgorithm = Signature.getInstance(ITRConstants.DIGITAL_SIGNATURE_ALGORITHM_NAME);
		signatureAlgorithm.initSign(aPrivateKey);
		byte[] digitalSignature = signatureAlgorithm.sign();
		String digitalSignatureStr = Base64.encode(digitalSignature);
		return digitalSignatureStr;
	}

	/**
	 * @param cc
	 * @param signature
	 * @return
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	public   DITWSAuthInfo populateAuthInfo(ItrInputs input) throws GeneralSecurityException, IOException, Exception {
		DITWSAuthInfo authInfo =  new DITWSAuthInfo();
		authInfo.setUserID(input.getEriUserId());
		authInfo.setPassword(input.getEriPassowrd());
		
		KeyStore ks = null;
		String password = "";
		// TODO meed this in future when we support auth using hard token
//		if(input.isHardToken()) {
//			ks = loadKeyStoreFromHardToken(input.getHardTokenPin());
//			password = input.getHardTokenPin();
//		}
//		else { 
			ks = loadKeyStoreFromPFXFile(input.getEriPfxFilePath(), input.getEriPfxFilePassword());
			password = input.getEriPfxFilePassword();
//		}
		PrivateKeyAndCertChain pkcc = getPrivateKeyAndCertChain(ks, password);
		String cc = encodeX509CertChainToBase64(pkcc.certChain);
		String signature = signature(pkcc.privateKey);
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
	public   Document signXml(ItrInputs inputs, InputStream fileInputStream) 
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
        	keyStore = loadKeyStoreFromHardToken(inputs.getHardTokenPin());
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
	
	public static   void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, Exception {
		/*loadKeyStoreFromHardToken("0000");*/
//		KeyStore keyStore = loadKeyStoreFromPFXFile("c:\\WSDL\\yogesh.pfx", "smart");
//		PrivateKeyAndCertChain andCertChain = getPrivateKeyAndCertChain(keyStore, "smart");
//		System.out.println(andCertChain.certificate.getType());
//		System.out.println(andCertChain.certificate.toString());
//		System.out.println(andCertChain.privateKey.getAlgorithm());
//		System.out.println(andCertChain.privateKey.getFormat());
//		System.out.println(signature(andCertChain.privateKey));
		SecurityUtils r =  new SecurityUtils();
		KeyStore keyStore1 = r.loadKeyStoreFromHardToken("0000");
		PrivateKeyAndCertChain andCertChain1 = r.getPrivateKeyAndCertChain(keyStore1, "0000");
		System.out.println(andCertChain1.certificate.getType());
		System.out.println(andCertChain1.certificate.toString());
		System.out.println(andCertChain1.privateKey.getAlgorithm());
		System.out.println(andCertChain1.privateKey.getFormat());
		System.out.println(r.signature(andCertChain1.privateKey));
	}
	
		
}

