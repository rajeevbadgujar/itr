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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

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
				System.out.println("Please provide required inputs.");
				System.exit(1);
			}
			
			fileUtils = new FileUtils();
			String itrXmlZipFileName = inputs.getXmlZipFilePath();
			if(inputs.getXmlSignature()) {
				itrXmlZipFileName = ITRConstants.SIGNED_XMLS_ZIP_FILE_NAME;
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
			
			ITRService obj = new ITRService();
			ITRServicePort servicePort = obj.getITRServiceSoapHttpPort();
	      
			DITWSResponse submitITR = servicePort.submitITR(authInfo, fileContent);
			String str  = submitITR.getResult();
			fileUtils.write(inputs.getResponseFilePath(), "Token number for the attached ZIP file " 
					+ inputs.getXmlZipFilePath() + " is :"+ str);
		} catch(ITRInvalidDocFaultException itrInvalidDocExcep) {
			error = itrInvalidDocExcep.getMessage();
			result = false;
        } catch(ITRCredentialFaultException itrCredFaultExcep) {
        	error = itrCredFaultExcep.getMessage();
        	result = false;
        } catch(ITRInvalidCertificateFaultException itrInvalidCertFaultExcep) {
        	error = itrInvalidCertFaultExcep.getMessage();
        	result = false;
        } catch(ITRServiceFaultException itrServiceFaultExcep) {
        	error = itrServiceFaultExcep.getMessage();
        	result = false;
        } catch(ITRBusinessServiceFaultException itrBixEx) {
        	error = itrBixEx.getMessage();
        	result = false;
        } catch(ITRFaultException itrFaultExcep) {
        	error = itrFaultExcep.getMessage();
        	result = false;
        } catch(FileNotFoundException exception) {
			System.out.println(exception.getMessage());
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
