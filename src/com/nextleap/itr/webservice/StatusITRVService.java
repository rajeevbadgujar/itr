package com.nextleap.itr.webservice;

import in.gov.incometaxindiaefiling.itrvservice.v_1_0.GetITRVCredentialFaultException;
import in.gov.incometaxindiaefiling.itrvservice.v_1_0.GetITRVInvalidCertificateFaultException;
import in.gov.incometaxindiaefiling.itrvservice.v_1_0.GetITRVServiceFaultException;
import in.gov.incometaxindiaefiling.itrvservice.v_1_0.ITRVService;
import in.gov.incometaxindiaefiling.itrvservice.v_1_0.ITRVServicePortType;
import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSAuthInfo;
import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSResponse;
import in.gov.incometaxindiaefiling.ws.ds.itrv.v_1_0.ITRVByTokenNoRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;

import com.nextleap.itr.webservice.beans.ItrInputs;
import com.nextleap.itr.webservice.util.FileUtils;
import com.nextleap.itr.webservice.util.InputUtils;
import com.nextleap.itr.webservice.util.SecurityUtils;

/**
 * @author rajeev
 *
 */
public class StatusITRVService {

	public boolean itrvStatusService() {
		FileUtils fileUtils = null;
		String error = "";
		ItrInputs inputs = new ItrInputs();
		boolean result = true;
		try{			
			InputUtils.populateItrReturnInputs(inputs);
			if(!InputUtils.validateForITRVStatus(inputs)) {
				System.out.println("Please provide required inputs.");
				System.exit(1);
			}
			
			fileUtils = new FileUtils();	
			DITWSAuthInfo authInfo = SecurityUtils.populateAuthInfo(inputs);
		
			ITRVService obj = new ITRVService();
			ITRVServicePortType servicePort = obj.getITRVServicePort();
			
			ITRVByTokenNoRequest itrvByTokenNoRequest = new ITRVByTokenNoRequest();
			itrvByTokenNoRequest.setAuthInfo(authInfo);
			itrvByTokenNoRequest.setPanID(inputs.getPanID());
			itrvByTokenNoRequest.setTokenNumber(inputs.getTokenNumber());
			DITWSResponse itrStatus = servicePort.getITRVByTokenNo(itrvByTokenNoRequest);
			String ditResult = itrStatus.getResult();
			fileUtils.write(inputs.getResponseFilePath(), ditResult);
		} catch (GetITRVCredentialFaultException
				| GetITRVInvalidCertificateFaultException
				| GetITRVServiceFaultException e) {
			error = e.getMessage();
			result = false;
		} catch (FileNotFoundException e) {
			error = e.getMessage();
			result = false;
		} catch (GeneralSecurityException e) {
			error = e.getMessage();
			result = false;
		} catch (IOException e) {
			error = e.getMessage();
			result = false;
		} catch (Exception ex) {
        	error = ex.getMessage();
        	result = false;
        } finally {
			fileUtils.write(inputs.getErrorFilePath(), error);
		}
		return result;
	}

}
