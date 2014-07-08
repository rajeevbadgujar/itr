package com.nextleap.itr.webservice.util;

import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSAuthInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.KeyStore.PrivateKeyEntry;
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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.nextleap.itr.webservice.beans.ItrInputs;
import com.nextleap.itr.webservice.constants.ITRConstants;

public class SecurityUtils {
	
	public static class PrivateKeyAndCertChain {
		public PrivateKeyEntry mPrivateKeyEntry;
	 	public Certificate certificate;
	 }
	
	public static KeyStore loadKeyStoreFromPFXFile(String aFileName, String aKeyStorePasswd) 
			throws GeneralSecurityException, IOException {	
		KeyStore keyStore = KeyStore.getInstance(ITRConstants.PKCS12_KEYSTORE_TYPE);
		FileInputStream keyStoreStream = new FileInputStream(aFileName);
		char[] password = aKeyStorePasswd.toCharArray();
		keyStore.load(keyStoreStream, password);
		return keyStore;
	}

	public static PrivateKeyAndCertChain getPrivateKeyAndCertChain(KeyStore aKeyStore, String aKeyPassword)
 			throws GeneralSecurityException {
		char[] password = aKeyPassword.toCharArray();
 		Enumeration<String> aliasesEnum = aKeyStore.aliases();
 		if (aliasesEnum.hasMoreElements()) {
 			String alias = (String) aliasesEnum.nextElement();
 			/*Certificate[] certificationChain = aKeyStore
 					.getCertificateChain(alias);*/
 			Certificate certificate509 = aKeyStore.getCertificate(alias);
 
 			KeyStore.PrivateKeyEntry entry = (PrivateKeyEntry) aKeyStore.getEntry(alias, new KeyStore.PasswordProtection(password));
 			PrivateKeyAndCertChain result = new PrivateKeyAndCertChain();
 			result.mPrivateKeyEntry = entry;	
 			/*result.mCertificationChain = certificationChain;*/
 			result.certificate = certificate509;
 			return result;
 		} else {
 			throw new KeyStoreException("The keystore is empty!");
 		}
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

	public static String signature(PrivateKey aPrivateKey) 
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
	public static DITWSAuthInfo populateAuthInfo(ItrInputs input) throws GeneralSecurityException, IOException {
		DITWSAuthInfo authInfo =  new DITWSAuthInfo();
		authInfo.setUserID(input.getEriUserId());
		authInfo.setPassword(input.getEriPassowrd());
		
		KeyStore ks = loadKeyStoreFromPFXFile(input.getEriPfxFilePath(), input.getEriPfxFilePassword());
		PrivateKeyAndCertChain pkcc = getPrivateKeyAndCertChain(ks, input.getEriPfxFilePassword());
		String cc = encodeX509CertChainToBase64(pkcc.mPrivateKeyEntry.getCertificateChain());
		String signature = signature(pkcc.mPrivateKeyEntry.getPrivateKey());
		authInfo.setCertChain(cc);
		authInfo.setSignature(signature);
		return authInfo;
	}
	
	public static Document signXml(ItrInputs inputs, InputStream fileInputStream) 
			throws FileNotFoundException, SAXException, IOException, ParserConfigurationException, 
			GeneralSecurityException, MarshalException, XMLSignatureException, TransformerException {
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
        
        KeyStore keyStore = loadKeyStoreFromPFXFile(inputs.getXmlPfxFile(), inputs.getXmlPfxFilePassword());
        PrivateKeyAndCertChain keyAndCertChain = getPrivateKeyAndCertChain(keyStore, inputs.getXmlPfxFilePassword());
        
        // Create the KeyInfo containing the X509Data.
        KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
        List<Object> x509Content = new ArrayList<Object>();
        x509Content.add(((X509Certificate)(keyAndCertChain.certificate)).getSubjectX500Principal().getName());
        x509Content.add(keyAndCertChain.certificate);
        X509Data x509Data = keyInfoFactory.newX509Data(x509Content);
        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
        
        // Create the XMLSignature, but don't sign it yet.
        XMLSignature signature = signatureFactory.newXMLSignature(signedInfo, keyInfo, 
        		Collections.singletonList(documentObject), null, null);
        
        // Create a DOMSignContext and specify the RSA PrivateKey and
        // location of the resulting XMLSignature's parent element.
        DOMSignContext domSignContext = new DOMSignContext
         (keyAndCertChain.mPrivateKeyEntry.getPrivateKey(), xmlDocument);
     
        // Marshal, generate, and sign the enveloped signature.
        signature.sign(domSignContext);
        
        /*OutputStream os = new FileOutputStream("E:\\WSDL\\signedI2_1106.xml");
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(xmlDocument), new StreamResult(os));*/
        return xmlDocument;
        
	}
}

