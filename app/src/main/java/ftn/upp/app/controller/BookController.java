package ftn.upp.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ftn.upp.app.dto.BookDto;
import ftn.upp.app.dto.SearchDto;
import ftn.upp.app.model.Book;
import ftn.upp.app.service.BookService;
import ftn.upp.app.service.CategoryService;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CategoryService categoryService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Book> upload(@RequestParam(value = "file") MultipartFile file) throws IOException, URISyntaxException, ParseException{
		
		if(!file.getContentType().equals("application/pdf")){
			return new ResponseEntity<Book>(HttpStatus.FORBIDDEN);
		}
		
	 	Book book = bookService.upload(file);
		
		return new ResponseEntity<Book>(book, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Book> save(@RequestBody Book book){
		System.out.println(book.toString());
		return new ResponseEntity<Book>(bookService.save(book), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<BookDto>> elasticSearch(@RequestBody List<SearchDto> searchModel){
		System.out.println("nesto");
		return new ResponseEntity<List<BookDto>>(bookService.search(searchModel), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<BookDto>> seacrhAll(){
		
		return new ResponseEntity<List<BookDto>>(bookService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> findOne(@PathVariable("id") Long id){
		return new ResponseEntity<Book>(bookService.findOne(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Book> update(@RequestBody Book book){
		BookDto bookDto = bookService.findOneES(book.getId());
		book.setContent(bookDto.getContent());
		bookService.update(book);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/removeAll", method = RequestMethod.DELETE)
	public void deleteAllES(){
		bookService.deleteAllES();
	}
	
	@RequestMapping(value = "/search/{categoryId}/books", method = RequestMethod.GET)
	public ResponseEntity<List<BookDto>> findByCategory(@PathVariable("categoryId") Long categoryId){
		
		return new ResponseEntity<List<BookDto>>(bookService.findByCategoryES(categoryService.findOne(categoryId).getName()),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadBool(@PathVariable("id") Long id, HttpServletResponse response) throws IOException{
		File pdf = bookService.downloadBook(id);
		
		if(pdf == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		/*
		InputStream is = new FileInputStream(pdf);
		byte[] book = IOUtils.toByteArray(is);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "book.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		
		
		return new ResponseEntity<byte[]>(book, headers, HttpStatus.OK);*/
		

		//File fileToDownload = new File(filePathToBeServed);
		InputStream inputStream = new FileInputStream(pdf);
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename=book.pdf"); 
		IOUtils.copy(inputStream, response.getOutputStream());
		response.flushBuffer();
		inputStream.close();
		return new ResponseEntity<byte[]>(HttpStatus.OK);
	}
}
