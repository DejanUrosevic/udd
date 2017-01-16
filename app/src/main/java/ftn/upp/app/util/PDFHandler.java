package ftn.upp.app.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

import ftn.upp.app.util.IncompleteIndexDocumentException;

public class PDFHandler extends DocumentHandler {

	@Override
	public Document getDocument(File file) throws IncompleteIndexDocumentException {
		Document doc = new Document();
		StringField id = new StringField("id", ""+System.currentTimeMillis(), Store.YES);
		TextField fileNameField = new TextField("fileName",	file.getName(), Store.YES);
		doc.add(id);
		doc.add(fileNameField);
		String error = "";
		try {
			StringField locationField = new StringField("location", file.getCanonicalPath(), Store.YES);
			doc.add(locationField);
			//napraviti pdf parser
			PDFParser parser = new PDFParser(new FileInputStream(file));
			//izvrsiti parsiranje
			parser.parse();
			
			//od parsera preuzeti parsirani pdf dokument (PDDocument)
			PDDocument pdf = parser.getPDDocument();
			
			//Upotrebiti text stripper klasu za ekstrahovanje teksta sa utf-8 kodnom stranom (PDFTextStripper)
			PDFTextStripper stripper = new PDFTextStripper("utf-8");
			String text = stripper.getText(pdf);
			if(text!=null && !text.trim().equals("")){
				doc.add(new TextField("text", text, Store.NO));
			}else{
				error += "Document without text\n";
			}
			
			//iz dokumenta izvuci objekat u kojem su svi metapodaci (PDDocumentInformation)
			PDDocumentInformation info = pdf.getDocumentInformation();
			
			//iz tog objekta preuzeti sto vise metapodataka i sve ih dodati na dokument
			
			//ovako se preuzimaju svi postojeci metapodaci - mana je sto se ne znaju njihova imena (u sustini se znaju, ali se ne zna redosled)
			
			/*
			String value;
			for(String key : info.getMetadataKeys()){
				value = info.getCustomMetadataValue(key);
				if(value!=null){
					//System.out.println(key + " : " + value);
					doc.add(new TextField(key.toLowerCase(), value, Store.YES));
				}
			}
			*/
			
			String author = info.getAuthor();
			if(author!=null && !author.trim().equals("")){
				doc.add(new TextField("author", author, Store.YES));
			}else{
				author = info.getCreator();
				if(author!=null && !author.trim().equals("")){
					doc.add(new TextField("author", author, Store.YES));
				}else{
					error += "Document without author\n";
				}
			}
			
			String title = info.getTitle();
			if(title!=null && !title.trim().equals("")){
				doc.add(new TextField("title", title, Store.YES));
			}else{
				error += "Document without title";
			}
			
			String keywords = info.getKeywords();
			if(keywords==null){
				keywords = title + " " + author;
			}
			
			String[] kws = keywords.trim().split(" ");
			for(String kw : kws){
				if(!kw.trim().equals("")){
					doc.add(new TextField("keyword", kw, Store.YES));
				}
			}
			
			//zatvoriti pdf dokument
			pdf.close();
		} catch (Exception e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
			error = "Document is incomplete. An exception occured";
		}
		
		if(!error.equals("")){
			throw new IncompleteIndexDocumentException(error.trim() + " -> " + file.getName());
		}
		
		return doc;
	}

}
