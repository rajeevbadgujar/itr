package com.nextleap.itr.webservice.util;

import com.nextleap.itr.webservice.constants.ITRConstants;

public enum TokenVendor {

	GEMALTO("__GEMALTO__",ITRConstants.GAMALTO_32BIT_DLL),
	FEITIANePASS("__FEITIAN_E_PASS_2003__",ITRConstants.ETOKEN_2003_32BIT_DLL),
	ETOKENPRO("__ETOKEN_PRO_72K__",ITRConstants.ETOKEN_PRO_32BIT_DLL);

	String key;
	String dllName;
	TokenVendor( String key,String dllName){
		this.key = key;
		this.dllName = dllName;
	}
	
	String getDllPath(){
		return dllName;
	}
	
	String getKey(){
		return key;
	}
	
	String getDriverInstallPath(TokenVendor toknVendor){
		
		switch(toknVendor){
		
			case GEMALTO:
				break;
			case FEITIANePASS:
				break;
			case ETOKENPRO:
				break;
			default:
				break;
		}
		return "";
		
	}
	
}
