package ftn.upp.app.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ftn.upp.app.dto.BookDto;
import ftn.upp.app.dto.SearchDto;
import ftn.upp.app.model.Book;

public interface BookService {

	public Book upload(MultipartFile file) throws IOException, URISyntaxException, ParseException;
	
	public Book save(Book book);
	
	public Book update(Book book);
	
	public List<BookDto> search(List<SearchDto> searchModel);
	
	public List<BookDto> findAll();
	
	public Book findOne(Long id);
	
	public BookDto findOneES(Long id);
	
	public void deleteAllES();
}
