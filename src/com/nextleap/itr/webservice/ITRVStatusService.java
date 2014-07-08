package com.nextleap.itr.webservice;


/**
 * @author rajeev
 *
 */
public class ITRVStatusService {

	public static void main(String[] args) {
		
		if (args==null || args.length==0) {
			System.out.println("Please provice Password to execute the webservice.");
			System.exit(1);
		} else if(!args[0].equals("itrWeb!123")){
			System.out.println("You are not authorized to use this service.");
			System.exit(1);
		}
		
		StatusITRVService itrvService = new StatusITRVService();
		boolean status = itrvService.itrvStatusService();
		if(status) 
			System.out.println("ITRV status retreived successfully.");
		else 
			System.out.println("Failed to retreive ITRV status, please check the error file");
	}
}
