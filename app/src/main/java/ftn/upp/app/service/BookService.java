package ftn.upp.app.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.springframework.web.multipart.MultipartFile;

import ftn.upp.app.model.Book;

public interface BookService {

	public Book upload(MultipartFile file) throws IOException, URISyntaxException, ParseException;
}
