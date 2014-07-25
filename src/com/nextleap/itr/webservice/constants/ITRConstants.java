package com.nextleap.itr.webservice.constants;

public interface ITRConstants {

	String USER_ID = "eriUserId";
	
	String USER_PASSWORD = "eriPassword";
	
	String ERI_PFX_FILE_PATH = "pfxFilePath";
	
	String ERI_PFX_FILE_PASSWORD = "pfxFilePassword";
	
	String XML_ZIP_FILE_PATH = "xmlZipFilePath";
	
	String XML_PFX_FILE_PATH = "xmlPfxFilePath";
	
	String XML_PFX_FILE_PASSWORD = "xmlPfxFilePassword";
	
	String RESPONSE_FILE_PATH = "responseFilePath";
	
	String ERROR_FILE_PATH = "errorFilePath";
	
	String XML_SIGNATURE_REQUIRED = "xmlSignature";

	String X509_CERTIFICATE_TYPE = "X.509";

	String CERT_CHAIN_ENCODING = "PkiPath";

	String PKCS12_KEYSTORE_TYPE = "PKCS12";

	// this is what is mentioned in the Documentation of the ITR WSDL.
	String DIGITAL_SIGNATURE_ALGORITHM_NAME = "SHA1withRSA"; 
	
	String TOKEN_NUMBER = "tokenNumber";
	
	String PAN_ID = "panID";

	String SIGNED_XMLS_ZIP_FILE_NAME = "signedXmls.zip";
	
	String DOM = "DOM";

	String ITR_TAG = "ITR";
	
	String CAC_INDIA = "CN=CCA India 2014,O=India PKI,C=IN";
	
	String IS_HARD_TOKEN = "hardToken";
	
	String HARD_TOKEN_PIN = "hardTokenPin";
	
	String PKCS11 = "PKCS11";
	
	String INSTALL_DIR = "installDir";
	
	String GAMALTO_32BIT_DLL = "/lib/IDPrimePKCS11.dll";
	
	String GAMALTO_64BIT_DLL = "/lib/IDPrimePKCS1164.dll";
	
	String SUBMIT_ITR_WSDL = "com/nextleap/itr/webservice/submitITR.wsdl";
	
	String STATUS_ITR_WSDL = "com/nextleap/itr/webservice/getITRV.wsdl";
}
