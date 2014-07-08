package com.nextleap.itr.webservice.util;

import org.junit.Before;
import org.junit.Test;

import com.nextleap.itr.webservice.beans.ItrInputs;

public class TestITRWebService {
	
	private ItrInputs inputs;
	@Before
	public void setUp() {
		inputs = new ItrInputs();
		inputs.setEriUserId("ERIU102411");
		inputs.setEriPassowrd("Digit2k!!!");
		inputs.setEriPfxFilePath("c:\\WSDL\\yogesh.pfx");
		inputs.setEriPfxFilePassword("smart");
		inputs.setXmlZipFilePath("c:\\WSDL\\I2_1106.zip");
		inputs.setResponseFilePath("c:\\WSDL\\itrResponse.txt");
		inputs.setErrorFilePath("c:\\WSDL\\error.txt");
	}
	
	@Test
	public void testSubmitITR(){
		
	}

}
