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

	public static final String SIGNED_XMLS_ZIP_FILE_NAME = "signedXmls.zip";
	
	public static final String DOM = "DOM";

	public static final String ITR_TAG = "ITR";
}
