package com.nextleap.itr.webservice.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {
	
	public void write(String filePath, String line) {
		File file = new File(filePath);
		BufferedWriter bufferFileWriter = null;
		try {
			if(!file.exists()){
				file.createNewFile();
				bufferFileWriter = new  BufferedWriter(new FileWriter(file));
			}
			//do not re-create the file we need to append the data
			else{
				bufferFileWriter = new  BufferedWriter(new FileWriter(file,true));
				bufferFileWriter.newLine();
			}
			
			bufferFileWriter.write(line);
		} catch (IOException e) {
			
		} finally {
			if(bufferFileWriter!=null)
				try {
					bufferFileWriter.close();
				} catch (IOException e) {
					System.out.println("Error closing file " +filePath + " " + e.getMessage());
				}
		}
	}

	public static byte[] readFromFileBytes(String filePath) throws FileNotFoundException, IOException {
		File file = new File(filePath);
	    FileInputStream fin = null;;
	    byte fileContent[] = null;
		try {
			fin = new FileInputStream(file);
			fileContent = new byte[(int) file.length()];
			fin.read(fileContent);
	   
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if(fin!=null)
				fin.close();
		}
		return fileContent;
	}
	
	public static void zipEntry(String filePath) throws ZipException, IOException {
		ZipFile zipFile = new ZipFile(new File(filePath));
		Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(filePath));
		while(zipEntries.hasMoreElements()) {
			ZipEntry entry = zipEntries.nextElement();
			System.out.println(entry.getName());
			System.out.println(entry.getSize());
			System.out.println(entry.getCompressedSize());
			InputStream inputStream = zipFile.getInputStream(entry);
			byte[] byteArray = new byte[(int) entry.getSize()];
			inputStream.read(byteArray);
			System.out.println(new String(byteArray));
			ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream("E:\\WSDL\\signedXmlFile.zip"));
			outputStream.putNextEntry(new ZipEntry(entry));
			outputStream.close();
			inputStream.close();
		}	
	}
	
}
