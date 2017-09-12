package ftn.upp.app.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ftn.upp.app.model.Book;
import ftn.upp.app.service.BookService;

@RestController
@RequestMapping(value = "/book")
public class BookController {
	
	@Autowired
	BookService bookService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Book> upload(@RequestParam(value = "file") MultipartFile file) throws IOException, URISyntaxException, ParseException{
		
		if(!file.getContentType().equals("application/pdf")){
			return new ResponseEntity<Book>(HttpStatus.FORBIDDEN);
		}
		
	 	Book book = bookService.upload(file);
		
		return new ResponseEntity<Book>(book, HttpStatus.OK);
		
	}
}
