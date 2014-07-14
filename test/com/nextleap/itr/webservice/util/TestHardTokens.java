package com.nextleap.itr.webservice.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;

import org.junit.Test;


public class TestHardTokens {

	@Test
	public void testCertFromToken() throws IOException, GeneralSecurityException {
		
		String configName = "pkcs11.properties";
        Provider p = new sun.security.pkcs11.SunPKCS11(configName);
        Security.addProvider(p);
        
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
		char[] password = {'0','0','0','0'};
		keyStore.load(null, password);
		
		SecurityUtils.getPrivateKeyAndCertChain(keyStore, "0000");
	}

}
