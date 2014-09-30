package com.nextleap.itr.webservice.util;

import com.nextleap.itr.webservice.beans.ItrInputs;
import com.nextleap.itr.webservice.constants.ITRConstants;

public class InputUtils {

	public static String installDir = "";
	
	public static void populateItrReturnInputs(ItrInputs inputs) {
		inputs.setEriUserId(System.getProperty(ITRConstants.USER_ID));
		inputs.setEriPassowrd(System.getProperty(ITRConstants.USER_PASSWORD));
		inputs.setEriPfxFilePath(System.getProperty(ITRConstants.ERI_PFX_FILE_PATH));
		inputs.setEriPfxFilePassword(System.getProperty(ITRConstants.ERI_PFX_FILE_PASSWORD));
		inputs.setXmlZipFilePath(System.getProperty(ITRConstants.XML_ZIP_FILE_PATH));
		inputs.setXmlPfxFile(System.getProperty(ITRConstants.XML_PFX_FILE_PATH));
		inputs.setXmlPfxFilePassword(System.getProperty(ITRConstants.XML_PFX_FILE_PASSWORD));
		inputs.setXmlSignature(new Boolean(System.getProperty(ITRConstants.XML_SIGNATURE_REQUIRED)));
		inputs.setResponseFilePath(System.getProperty(ITRConstants.RESPONSE_FILE_PATH));
		inputs.setErrorFilePath(System.getProperty(ITRConstants.ERROR_FILE_PATH));
		inputs.setPanID(System.getProperty(ITRConstants.PAN_ID));
		inputs.setTokenNumber(System.getProperty(ITRConstants.TOKEN_NUMBER));
		inputs.setHardToken(new Boolean(System.getProperty(ITRConstants.IS_HARD_TOKEN)));
		inputs.setHardTokenPin(System.getProperty(ITRConstants.HARD_TOKEN_PIN));
		inputs.setHardTokenType(System.getProperty(ITRConstants.HARD_TOKEN_TYPE));
		inputs.setGenerateSoftToken(new Boolean(System.getProperty(ITRConstants.GENERATE_SOFT_TOKEN)));
		inputs.setUseSoftToken(new Boolean(System.getProperty(ITRConstants.USE_SOFT_TOKEN)));
		installDir = System.getProperty(ITRConstants.INSTALL_DIR);
	}
	
	public static boolean validateForITRSubmit(ItrInputs inputs) {
		boolean validate = true;
		if(!InputUtils.validateBasic(inputs) 
				|| (inputs.getXmlZipFilePath()==null || inputs.getXmlZipFilePath().equals(""))
				|| validateXmlSigningParam(inputs)) {
			validate = false;
		} 
		return validate;
	}

	public static boolean validateForITRVGenerateSoftToken(ItrInputs inputs) {
		boolean validate = true;
		if((inputs.getHardTokenType()==null || inputs.getHardTokenType().equals(""))
				&& (inputs.getHardTokenPin() == null || inputs.getHardTokenPin().equals(""))) {
			validate = false;
		} 
		return validate;
	}
	
	/**
	 * @param inputs
	 * @return
	 */
	private static boolean validateXmlSigningParam(ItrInputs inputs) {
		return (inputs.getXmlSignature() && (!inputs.isHardToken() || inputs.getHardTokenPin()==null || inputs.getHardTokenPin().equals(""))) 
				&& (inputs.getXmlPfxFile()==null || inputs.getXmlPfxFile().equals("") 
					|| inputs.getXmlPfxFilePassword()==null || inputs.getXmlPfxFilePassword().equals(""));
	}
	
	public static boolean validateBasic(ItrInputs inputs) {
		boolean validate = true;
		if((inputs.getEriUserId()==null || inputs.getEriUserId().equals(""))
				|| (inputs.getEriPassowrd()==null || inputs.getEriPassowrd().equals(""))
				
				|| (!(inputs.isUseSoftToken() &&  (inputs.getHardTokenType() != null && inputs.getHardTokenType().length()>0)) 
				&& ((inputs.getEriPfxFilePath()==null || inputs.getEriPfxFilePath().equals(""))
				|| (inputs.getEriPfxFilePassword()==null || inputs.getEriPfxFilePassword().equals(""))))) {
			validate = false;
		}
		return validate;
	}
	
	public static boolean validateForITRVStatus(ItrInputs inputs) {
		boolean validate = true;
		if(!InputUtils.validateBasic(inputs) 
				|| (inputs.getPanID()==null || inputs.getPanID().equals(""))
				|| (inputs.getTokenNumber()==null || inputs.getTokenNumber().equals(""))
				) {
			validate = false;
		} 
		return validate;
	}
}
