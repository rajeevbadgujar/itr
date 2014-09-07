package com.nextleap.itr.webservice.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.nextleap.itr.webservice.constants.ITRConstants;

public enum TokenVendor {

	GEMALTO("GEMALTO",ITRConstants.GAMALTO_32BIT_DLL),
	FEITIANePASS2003("FEITIANePASS2003",ITRConstants.ETOKEN_2003_32BIT_DLL),
	ETOKENPRO72K("ETOKENPRO72K",ITRConstants.ETOKEN_PRO_32BIT_DLL);

	String key;
	String dllName;
	TokenVendor( String key,String dllName){
		this.key = key;
		this.dllName = dllName;
	}

	String getKey(){
		return key;
	}
	
	String getDriverInstallPath() throws UnsupportedEncodingException{
		String dllPath="";
		switch(this){
		
			case GEMALTO:
				dllPath = InputUtils.installDir + dllName;
				break;
			case FEITIANePASS2003:
				dllPath =  getDefaultWindowsPath()+dllName;
				break;
			case ETOKENPRO72K:
				dllPath =  getDefaultWindowsPath()+dllName;
				break;
			default:
				break;
		}
		return URLEncoder.encode(dllPath,"UTF-8");
		
	}
	
	
	protected static String getDefaultWindowsPath(){
		String content="c:/windows/system32"; 
		String os = System.getProperty("os.name");
        System.out.println("os: "+os);
        
        if(os.contains("Windows")) {
             if(os.contains("Windows 9"))
            	 content = System.getenv("WinDir");
             else
            	 content = System.getenv("SystemRoot");
        }
        return content+"\\system32";
}
	
	
	
}
