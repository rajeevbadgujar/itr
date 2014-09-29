package com.nextleap.itr.webservice.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

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
		return readFromFileBytes(new File(filePath));
	}
	
	
	public static byte[] readFromFileBytes(File file) throws FileNotFoundException, IOException {
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

	
	 /**
     * Writes the specified byte[] to the specified File path.
     * 
     * @param theFile File Object representing the path to write to.
     * @param bytes The byte[] of data to write to the File.
     * @throws IOException Thrown if there is problem creating or writing the 
     * File.
     */
    public static void writeBytesToFile(File theFile, byte[] bytes) throws IOException {
      BufferedOutputStream bos = null;
      
    try {
      FileOutputStream fos = new FileOutputStream(theFile);
      bos = new BufferedOutputStream(fos); 
      bos.write(bytes);
    }finally {
      if(bos != null) {
        try  {
          //flush and close the BufferedOutputStream
          bos.flush();
          bos.close();
        } catch(Exception e){}
      }
    }
    }
}
