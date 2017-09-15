package ftn.upp.app.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import ftn.upp.app.elasticsearch.repository.BookElasticSearchRepository;
import ftn.upp.app.model.Book;
import ftn.upp.app.repository.BookRepository;
import ftn.upp.app.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookElasticSearchRepository bookESRepository;

	@Override
	public Book upload(MultipartFile file) throws IOException, URISyntaxException, ParseException {
		
		Path path = Paths.get("C:\\Users\\Dejan.UROSEVIC\\git\\uddd\\app\\src\\main\\resource\\pdf\\" + file.getOriginalFilename());
		Files.write(path, file.getBytes());
		
		File pdf = new File("C:\\Users\\Dejan.UROSEVIC\\git\\uddd\\app\\src\\main\\resource\\pdf\\" + file.getOriginalFilename());
		
		Book book = new Book();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream inputstream = new FileInputStream(pdf);
		ParseContext pcontext = new ParseContext();
	      
		//parsing the document using PDF parser
		PDFParser pdfparser = new PDFParser(); 
		try {
			pdfparser.parse(inputstream, handler, metadata,pcontext);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		//getting the content of the document
		System.out.println("Contents of the PDF :" + handler.toString());
	      
		//getting metadata of the document
		System.out.println("Metadata of the PDF:");
		String[] metadataNames = metadata.names();
		
		book.setAuthor(metadata.get("Author"));
		book.setKeywords(metadata.get("Keywords"));
		book.setTitle(metadata.get("title"));
		book.setContent(handler.toString());
		book.setMime(file.getContentType());
		book.setFilename(file.getOriginalFilename());
		
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(metadata.get("created")));;
		
		book.setPublicationYear(calendar.get(Calendar.YEAR));
		
		for(String name : metadataNames) {
			System.out.println(name+ " : " + metadata.get(name));
	    }
		
		return book;
	}

	@Override
	public Book save(Book book) {
		
		Book savedBook = bookRepository.save(book);
		bookESRepository.save(savedBook);
		return savedBook;
	}

	@Override
	public List<Book> search() {
		
		return null;
	}

}
