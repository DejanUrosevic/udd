package ftn.upp.app.util;

import org.springframework.beans.factory.annotation.Autowired;

import ftn.upp.app.dto.BookDto;
import ftn.upp.app.model.Book;
import ftn.upp.app.service.CategoryService;
import ftn.upp.app.service.LanguageService;

public class Mapper {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	LanguageService languageService;
	
	public Book fromBookDtoToBook(BookDto bookDto){
		Book book = new Book();
		

		book.setId(bookDto.getId());
		book.setAuthor(bookDto.getAuthor());
		book.setCategory(categoryService.findByName(bookDto.getCategory()));
		book.setFilename(bookDto.getFilename());
		book.setKeywords(bookDto.getKeywords());
		book.setLanguage(languageService.findByName(bookDto.getLanguage()));
		book.setMime(bookDto.getMime());
		book.setTitle(bookDto.getTitle());
		book.setPublicationYear(bookDto.getPublicationYear());
		book.setContent(bookDto.getContent());
		
		return book;
	}
	
	public BookDto fromBookToBookDto(Book book){
		BookDto bookDto = new BookDto();
		
		bookDto.setAuthor(book.getAuthor());
		bookDto.setCategory(book.getCategory().getName());
		bookDto.setFilename(book.getFilename());
		bookDto.setId(book.getId());
		bookDto.setKeywords(book.getKeywords());
		bookDto.setLanguage(book.getLanguage().getName());
		bookDto.setMime(book.getMime());
		bookDto.setPublicationYear(book.getPublicationYear());
		bookDto.setTitle(book.getTitle());
		bookDto.setContent(book.getContent());
		
		return bookDto;
	}
}
