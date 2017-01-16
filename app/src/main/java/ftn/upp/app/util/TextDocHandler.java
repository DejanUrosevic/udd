package ftn.upp.app.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;


public class TextDocHandler extends DocumentHandler {

	@Override
	public Document getDocument(File file) {
		try{
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF8"));
			
			//u prvom redu teksta se nalazi naslov
			String title = reader.readLine();
			
			TextField titleField1 = new TextField("title", title.trim(), Store.YES);
			//StringField titleField2 = new StringField("title", title.trim(), Store.YES);
			TextField fileNameField = new TextField("fileName",	file.getName(), Store.YES);
			StringField locationField = new StringField("location", file.getCanonicalPath(), Store.YES);
			
			Document doc = new Document();
			doc.add(titleField1);
			//doc.add(titleField2);
			doc.add(fileNameField);
			doc.add(locationField);
			
			/*
			 *razmislite o tome sta bi jos moglo da se indeksira...
			 * Sta je jos bitno osim ove tri stvari? Da li su ove tri stvari uopste i bitne ???
			 * 
			 *Dodati jos metapodataka
			 */
			
			String secondLine = reader.readLine();
			String[] keywords = secondLine.split(" ");
			for(String kw : keywords){
				doc.add(new TextField("keyword", kw, Store.YES));
			}
			
			String text = "";
			String read = "";
			while(true){
				try{
					read = reader.readLine();
					if(read != null){
						text += "\n" + read;
					}
					else{
						break;
					}
				}catch(Exception e){
					break;
				}
			}
			doc.add(new TextField("text",text, Store.YES));
			
			return doc;
		}catch(FileNotFoundException fnfe){
			return null;
		}catch(IOException ioe){
			return null;
		}
	}

}
