package com.nextleap.itr.webservice;

/**
 * The main class to initiate the web service and call the associated web service.
 * @author Ankit_Jain
 *
 */
public class ITRWebService {

	public static void main(String args[]) {
		
		if (args==null || args.length==0) {
			System.out.println("Please provice Password to execute the webservice.");
			System.exit(1);
		} else if(!args[0].equals("itrWeb!123")){
			System.out.println("You are not authorized to use this service.");
			System.exit(1);
		}
				
		SubmitITRService itrService = new SubmitITRService();
		boolean result = itrService.submitITR();
		if (result)
			System.out.println("ITR File uploaded successfully.");
		else 
			System.out.println("ITR File uploaded caused a problem, please check the error file");
	}
	
}
