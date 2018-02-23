package com.interset.interview.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.interset.interview.data.InfosRecord;
import com.interset.interview.data.Population;

public class ProcessFile {
	private String path;

	// contains all population data
	private Population population;
	private String fileExt;
	private enum FileType
	{
		JSON,
		CSV, 
		ZIP;
		
		public static FileType fromValue(String v) {
			try {
	            return valueOf(v.toUpperCase());
	        } catch (Exception ex) {
	        	return ZIP;
	        }
		}
	}

	/**
	 * the path of the file to process
	 * @param path
	 */
	public ProcessFile(String path)
	{
		this.path = path;
		this.fileExt = this.getFileExtension(path);
		population = new Population();
	}

	public Population getPopulationRecords()
	{
		return this.population;
	}
	
	private boolean isZipFile()
	{
		switch (FileType.fromValue(this.fileExt))
		{
		case JSON:
		case CSV: 
			return false;
		default : return true;
		}
	}

	/**
	 * get the file extension
	 * @return
	 */
	private String getFileExtension(String path)
	{
		return path.substring(path.lastIndexOf(".") +1 , path.length()).toUpperCase();
	}
	
	/**
	 * method to extruct data from CSV file using the specified Header
	 * @throws IOException
	 */
	public void readCSV() throws IOException
	{
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema csvSchema = csvMapper.typedSchemaFor(InfosRecord.class).withHeader(); 
		population.setRecords( csvMapper.readerFor(InfosRecord.class)
				.with(csvSchema.withColumnSeparator(CsvSchema.DEFAULT_COLUMN_SEPARATOR))
				.readValues(new File(this.path)).readAll());    
	}
	

	/**
	 * method to extract data from JSON file
	 * @throws IOException
	 */
	public void readJSON() throws IOException
	{		
		ObjectMapper mapper = new ObjectMapper();
		this.population.setRecords(mapper.reader()
				.forType(new TypeReference<List<InfosRecord>>() {})
				.readValue(new File(this.path)));		
	}

	/**
	 * extruct the data from csv , json or zip file
	 * @throws Exception
	 */
	public void extructData() throws Exception
	{
		if(isZipFile())
		{
			this.path = Unzip.unzip(this.path);
			this.fileExt = this.getFileExtension(this.path);
		}

		switch (FileType.valueOf(this.fileExt))
		{
		case JSON: readJSON(); break;
		default : readCSV(); break;
		}
	}
}
