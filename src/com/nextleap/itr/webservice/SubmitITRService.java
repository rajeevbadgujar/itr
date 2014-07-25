package com.nextleap.itr.webservice;

import in.gov.incometaxindiaefiling.itrservice.v_1_0.ITRBusinessServiceFaultException;
import in.gov.incometaxindiaefiling.itrservice.v_1_0.ITRCredentialFaultException;
import in.gov.incometaxindiaefiling.itrservice.v_1_0.ITRFaultException;
import in.gov.incometaxindiaefiling.itrservice.v_1_0.ITRInvalidCertificateFaultException;
import in.gov.incometaxindiaefiling.itrservice.v_1_0.ITRInvalidDocFaultException;
import in.gov.incometaxindiaefiling.itrservice.v_1_0.ITRService;
import in.gov.incometaxindiaefiling.itrservice.v_1_0.ITRServiceFaultException;
import in.gov.incometaxindiaefiling.itrservice.v_1_0.ITRServicePort;
import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSAuthInfo;
import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.nextleap.itr.webservice.beans.ItrInputs;
import com.nextleap.itr.webservice.constants.ITRConstants;
import com.nextleap.itr.webservice.util.FileUtils;
import com.nextleap.itr.webservice.util.InputUtils;
import com.nextleap.itr.webservice.util.SecurityUtils;

/**
 * @author rajeev
 *
 */
public class SubmitITRService {

	public boolean submitITR() {
		FileUtils fileUtils = null;
		String error = "";
		ItrInputs inputs = new ItrInputs();
		boolean result = true;
		try{				
			InputUtils.populateItrReturnInputs(inputs);
			if(!InputUtils.validateForITRSubmit(inputs)) {
				System.out.println("Please provide required inputs." + inputs.toString());
				System.exit(1);
			}
			
			fileUtils = new FileUtils();
			String itrXmlZipFileName = inputs.getXmlZipFilePath();
			if(inputs.getXmlSignature()) {
				itrXmlZipFileName = itrXmlZipFileName.replace(".", "_signed.");
				ZipFile zipFile = new ZipFile(new File(inputs.getXmlZipFilePath()));
				Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
				ZipOutputStream outputStream = new ZipOutputStream(
						new FileOutputStream(itrXmlZipFileName));
				while(zipEntries.hasMoreElements()) {
					ZipEntry entry = zipEntries.nextElement();
					InputStream fileInputStream = zipFile.getInputStream(entry);
					Document signedXmlDocument = SecurityUtils.signXml(inputs, fileInputStream);
					outputStream.putNextEntry(new ZipEntry(entry.getName()));
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.transform(new DOMSource(signedXmlDocument), new StreamResult(outputStream));
					outputStream.closeEntry();
				}
				outputStream.close();
				zipFile.close();
			}
			
			byte[] fileContent = FileUtils.readFromFileBytes(itrXmlZipFileName);
			DITWSAuthInfo authInfo = SecurityUtils.populateAuthInfo(inputs);
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			URL wsdlLocation = cl.getResource(ITRConstants.SUBMIT_ITR_WSDL);
			ITRService obj = new ITRService(wsdlLocation);
			ITRServicePort servicePort = obj.getITRServiceSoapHttpPort();
	      
			DITWSResponse submitITR = servicePort.submitITR(authInfo, fileContent);
			String str  = submitITR.getResult();
			fileUtils.write(inputs.getResponseFilePath(), "Token number for the attached ZIP file " 
					+ inputs.getXmlZipFilePath() + " is :"+ str);
		} catch (ITRBusinessServiceFaultException | ITRCredentialFaultException
				| ITRFaultException | ITRInvalidCertificateFaultException
				| ITRInvalidDocFaultException | ITRServiceFaultException e) {
			error = e.getMessage();
			result = false;
		} catch (FileNotFoundException | SAXException | ParserConfigurationException 
				| GeneralSecurityException | MarshalException | XMLSignatureException 
				| TransformerException e) {
			error = e.getMessage();
			result = false;
		} catch (IOException e) {
			error = e.getMessage();
			result = false;
		} catch (Exception e) {
			error = e.getMessage();
			result = false;
		} finally {
			if(!error.isEmpty())
				fileUtils.write(inputs.getErrorFilePath(), error);
        }
		return result;
	}

}
