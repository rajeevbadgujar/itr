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
}
