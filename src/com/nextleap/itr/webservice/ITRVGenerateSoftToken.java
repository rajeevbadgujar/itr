package com.nextleap.itr.webservice;

import com.nextleap.itr.webservice.beans.ItrInputs;
import com.nextleap.itr.webservice.util.InputUtils;
import com.nextleap.itr.webservice.util.SecurityUtils;

public class ITRVGenerateSoftToken {

	public static void main(String args[]) {
	if (args==null || args.length==0) {
		System.out.println("Please provice Password to execute the webservice.");
		System.exit(1);
	} else if(!args[0].equals("itrWeb!123")){
		System.out.println("You are not authorized to use this service.");
		System.exit(1);
	}
	ItrInputs inputs = new ItrInputs();
	InputUtils.populateItrReturnInputs(inputs);
	if(!InputUtils.validateForITRVGenerateSoftToken(inputs)) {
		System.out.println("Please provide required inputs." + inputs.toString());
		System.exit(1);
	}
	
	boolean status = Boolean.TRUE;
			try{
				SecurityUtils.populateAndStoreAuthInfo(inputs, Boolean.TRUE);
			}
		catch(Exception ex){
			status = Boolean.FALSE;
		}
	if(status) 
		System.out.println("Successfully generated the soft token.");
	else 
		System.out.println("Exception in generating the soft token for given hardware");
		
}
}

