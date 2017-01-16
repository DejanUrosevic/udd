package ftn.upp.app.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexableField;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ftn.upp.app.model.Book;
import ftn.upp.app.service.BookService;
import ftn.upp.app.util.UDDIndexer;

@Service
public class BookServiceImpl implements BookService{

	@Override
	public Book upload(MultipartFile file) throws IOException, URISyntaxException {
		
		Path path = Paths.get("C:\\Users\\Dejan\\git\\upp\\app\\src\\main\\resource\\pdf\\" + file.getOriginalFilename());
		Files.write(path, file.getBytes());
		
		File pdf = new File("C:\\Users\\Dejan\\git\\upp\\app\\src\\main\\resource\\pdf\\" + file.getOriginalFilename());
		UDDIndexer indexer = new UDDIndexer(true);
		indexer.index(pdf);
		
		Document[] docs = indexer.getAllDocuments();
		
		Book book = new Book();
		for(Document d : docs){
			for(IndexableField f : d.getFields()){
				if(f.name().equals("title")){
					book.setTitle(f.stringValue());
				} else if (f.name().equals("author")){
					book.setAuthor(f.stringValue());
				} else if (f.name().equals("keyword")){
					if(book.getKeywords() == null){
						book.setKeywords(f.stringValue());
					} else {
						book.setKeywords(book.getKeywords() + "," + f.stringValue());
					}
				} else if (f.name().equals("fileName")){
					book.setFilename(f.stringValue());
				}
			}
		}
		
		System.out.println("dsad");
		
		return book;
	}

}
