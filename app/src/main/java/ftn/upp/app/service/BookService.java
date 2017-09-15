package ftn.upp.app.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ftn.upp.app.model.Book;

public interface BookService {

	public Book upload(MultipartFile file) throws IOException, URISyntaxException, ParseException;
	
	public Book save(Book book);
	
	public List<Book> search();
}
