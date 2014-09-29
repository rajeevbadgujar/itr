package com.nextleap.itr.webservice.beans;

public class ItrInputs {
	
	private String eriUserId;
	private String eriPassowrd;
	private String xmlZipFilePath;
	private String eriPfxFilePath;
	private String eriPfxFilePassword;
	private boolean xmlSignature;
	private String xmlPfxFile;
	private String xmlPfxFilePassword;
	private String responseFilePath;
	private String errorFilePath;
	private String tokenNumber;
	private String panID;
	private boolean hardToken;
	private String hardTokenPin;
	private String hardTokenType;
	private boolean generateSoftToken;
	private boolean useSoftToken;

	public boolean isUseSoftToken() {
		return useSoftToken;
	}

	public void setUseSoftToken(boolean useSoftToken) {
		this.useSoftToken = useSoftToken;
	}

	public boolean isGenerateSoftToken() {
		return generateSoftToken;
	}

	public void setGenerateSoftToken(boolean generateSoftToken) {
		this.generateSoftToken = generateSoftToken;
	}

	public String getHardTokenType() {
		return hardTokenType;
	}

	public void setHardTokenType(String hardTokenType) {
		this.hardTokenType = hardTokenType;
	}

	public String getEriUserId() {
		return eriUserId;
	}

	public void setEriUserId(String eriUserId) {
		this.eriUserId = eriUserId;
	}

	public String getEriPassowrd() {
		return eriPassowrd;
	}

	public void setEriPassowrd(String eriPassowrd) {
		this.eriPassowrd = eriPassowrd;
	}

	public String getXmlZipFilePath() {
		return xmlZipFilePath;
	}

	public void setXmlZipFilePath(String xmlZipFilePath) {
		this.xmlZipFilePath = xmlZipFilePath;
	}

	public String getEriPfxFilePath() {
		return eriPfxFilePath;
	}

	public void setEriPfxFilePath(String eriPfxFilePath) {
		this.eriPfxFilePath = eriPfxFilePath;
	}

	public String getEriPfxFilePassword() {
		return eriPfxFilePassword;
	}

	public void setEriPfxFilePassword(String eriPfxFilePassword) {
		this.eriPfxFilePassword = eriPfxFilePassword;
	}

	public boolean getXmlSignature() {
		return xmlSignature;
	}

	public void setXmlSignature(boolean xmlSignature) {
		this.xmlSignature = xmlSignature;
	}

	public String getXmlPfxFile() {
		return xmlPfxFile;
	}

	public void setXmlPfxFile(String xmlPfxFile) {
		this.xmlPfxFile = xmlPfxFile;
	}

	public String getXmlPfxFilePassword() {
		return xmlPfxFilePassword;
	}

	public void setXmlPfxFilePassword(String xmlPfxFilePassword) {
		this.xmlPfxFilePassword = xmlPfxFilePassword;
	}

	public String getResponseFilePath() {
		return responseFilePath;
	}

	public void setResponseFilePath(String responseFilePath) {
		this.responseFilePath = responseFilePath;
	}

	public String getErrorFilePath() {
		return errorFilePath;
	}

	public void setErrorFilePath(String errorFilePath) {
		this.errorFilePath = errorFilePath;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public String getPanID() {
		return panID;
	}

	public void setPanID(String panID) {
		this.panID = panID;
	}
	
	@Override
	public String toString() {
		return "ItrInputs [eriUserId=" + eriUserId + ", eriPassowrd="
				+ eriPassowrd + ", xmlZipFilePath=" + xmlZipFilePath
				+ ", eriPfxFilePath=" + eriPfxFilePath
				+ ", eriPfxFilePassword=" + eriPfxFilePassword
				+ ", xmlSignature=" + xmlSignature + ", xmlPfxFile="
				+ xmlPfxFile + ", xmlPfxFilePassword=" + xmlPfxFilePassword
				+ ", responseFilePath=" + responseFilePath + ", errorFilePath="
				+ errorFilePath + ", tokenNumber=" + tokenNumber + ", panID="
				+ panID + ", isHardToken=" + hardToken + ", hardTokenPin="
				+ hardTokenPin + "]";
	}

	public boolean isHardToken() {
		return hardToken;
	}

	public void setHardToken(boolean hardToken) {
		this.hardToken = hardToken;
	}

	public String getHardTokenPin() {
		return hardTokenPin;
	}

	public void setHardTokenPin(String hardTokenPin) {
		this.hardTokenPin = hardTokenPin;
	}
}
