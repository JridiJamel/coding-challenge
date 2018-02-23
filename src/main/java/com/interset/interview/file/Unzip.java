package com.interset.interview.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This utility extracts files and directories of a standard zip file to
 * a destination directory.
 */
public class Unzip {

	private static final int BUFFER_SIZE = 4096;
	/**
	 * Extracts a gzip file specified by the zipFilePath 
	 * @return the path of the file to be processed
	 */
	public static String unzip(String zipFilePath) throws IOException { 
		String file = "";
		file = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
		FileInputStream fis = new FileInputStream(zipFilePath);
		GZIPInputStream gis = new GZIPInputStream(fis);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int len;
		while((len = gis.read(buffer)) != -1){
			fos.write(buffer, 0, len);
		}
		fos.close();
		gis.close();

		return file;
	}
}
