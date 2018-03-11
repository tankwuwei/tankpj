package engine.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;

import antlr.Parser;
import engine.log.LogUtil;

public class CSVReader {

	public static List<Map<String, String>> read(String path) {
		List<String> title = new ArrayList<>();
		List<Map<String, String>> allcontent = new ArrayList<>();

		
		try
		{

			 File file = new File(path);  
			 InputStream in = new FileInputStream(file);

			 //读取的时候去掉utf bom
			final Reader reader = new InputStreamReader(new BOMInputStream(in, false));
			final CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());

			for(String val:parser.getHeaderMap().keySet())
			{
				title.add(val);
			}

			
			  for (CSVRecord record : parser) {
				  int i=0;
				  Map<String, String> colummaps=new HashMap<String, String>();
				    for(String val:record)
				    {
				    	colummaps.put(title.get(i++), val);
				    }
				    allcontent.add(colummaps);
				  }

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allcontent;
	}

}
