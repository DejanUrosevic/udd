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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import ftn.upp.app.dto.BookDto;
import ftn.upp.app.dto.SearchDto;
import ftn.upp.app.elasticsearch.repository.BookElasticSearchRepository;
import ftn.upp.app.model.Book;
import ftn.upp.app.repository.BookRepository;
import ftn.upp.app.service.BookService;
import ftn.upp.app.util.Mapper;
import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookElasticSearchRepository bookESRepository;
	
	@Autowired
	ElasticsearchOperations operations;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;

	Mapper mapper = new Mapper();
	
	@Override
	public Book upload(MultipartFile file) throws IOException, URISyntaxException, ParseException {
		
		Path path = Paths.get("C:\\Users\\Dejan\\git\\udd\\app\\src\\main\\resource\\pdf\\" + file.getOriginalFilename());
		Files.write(path, file.getBytes());
		
		File pdf = new File("C:\\Users\\Dejan\\git\\udd\\app\\src\\main\\resource\\pdf\\" + file.getOriginalFilename());
		
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
		operations.putMapping(BookDto.class);
		BookDto bookDto = mapper.fromBookToBookDto(savedBook);
		bookESRepository.save(bookDto);
		return savedBook;
	}

	@Override
	public List<BookDto> search(List<SearchDto> searchModel) {
		
		List<QueryBuilder> qbList = new ArrayList<QueryBuilder>();
		
		for (SearchDto sdto : searchModel) {
			qbList.add(QueryBuilders.matchQuery(sdto.getField(), sdto.getValue()).operator(AND)
					.fuzziness(Fuzziness.AUTO).prefixLength(3));
		}
		
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();

		for (int i = 0; i < searchModel.size(); i++) {
			if (searchModel.get(i).getOperator().equals("AND")) {
				bqb.must(qbList.get(i));
			} else {
				bqb.should(qbList.get(i));
			}
		}

		SearchQuery sq = new NativeSearchQueryBuilder()
				.withQuery(bqb)
				.withHighlightFields(new HighlightBuilder.Field("content"), 
									 new HighlightBuilder.Field("title"), 
									 new HighlightBuilder.Field("keywords"), 
									 new HighlightBuilder.Field("author"),
									 new HighlightBuilder.Field("language"))
				.build();
		
		Page<BookDto> booksEntities = elasticsearchTemplate.queryForPage(sq, BookDto.class, new SearchResultMapper() {
			
			@Override
			public <T> AggregatedPage<T> mapResults(SearchResponse arg0, Class<T> arg1, Pageable arg2) {
				List<BookDto> books = new ArrayList<BookDto>();
				for(SearchHit searchHit : arg0.getHits()){
					if(arg0.getHits().getHits().length <= 0){
						return null;
					}
					
					BookDto bookDto = new BookDto();
					bookDto.setId(Long.parseLong(searchHit.getId()));
					bookDto.setAuthor((String) searchHit.getSource().get("author"));
					bookDto.setCategory((String) searchHit.getSource().get("category"));
					bookDto.setContent((String)searchHit.getSource().get("content"));
					bookDto.setFilename((String)searchHit.getSource().get("filename"));
					bookDto.setKeywords((String)searchHit.getSource().get("keywords"));
					bookDto.setLanguage((String)searchHit.getSource().get("language"));
					bookDto.setMime((String)searchHit.getSource().get("mime"));
					bookDto.setPublicationYear((int) searchHit.getSource().get("publicationYear"));
					bookDto.setTitle((String)searchHit.getSource().get("title"));
					if(searchHit.getHighlightFields() != null){
						StringBuilder highlights = new StringBuilder("...");
						if(searchHit.getHighlightFields().get("content") != null){
							highlights.append(searchHit.getHighlightFields().get("content").fragments()[0].toString());
							highlights.append("...");
						}
						
						if(searchHit.getHighlightFields().get("title") != null){
							highlights.append(searchHit.getHighlightFields().get("title").fragments()[0].toString());
							highlights.append("...");
						}
						
						if(searchHit.getHighlightFields().get("keywords") != null){
							highlights.append(searchHit.getHighlightFields().get("keywords").fragments()[0].toString());
							highlights.append("...");
						}
						
						if(searchHit.getHighlightFields().get("author") != null){
							highlights.append(searchHit.getHighlightFields().get("author").fragments()[0].toString());
							highlights.append("...");
						}
						
						if(searchHit.getHighlightFields().get("language") != null){
							highlights.append(searchHit.getHighlightFields().get("language").fragments()[0].toString());
							highlights.append("...");
						}
						
						bookDto.setHighlight(highlights.toString());
					}
					books.add(bookDto);
					
				}
				
				if(books.size() > 0){
					return new AggregatedPageImpl<T>((List<T>) books);
				}
				
				return null;
			}
		});

		List<BookDto> returnBooks = new ArrayList<BookDto>();
		
		if(booksEntities != null){
			for(BookDto b : booksEntities){
				returnBooks.add(b);
			}
		}

		/*List<Book> books = new ArrayList<Book>();
		
		for(BookDto booksDto : returnBooks){
			Book book = bookRepository.findOne(booksDto.getId());
			books.add(book);
		}*/
		
		return returnBooks;

	}

	@Override
	public List<BookDto> findAll() {
		Iterable<BookDto> returnBooks = bookESRepository.findAll();
		List<BookDto> books = new ArrayList<BookDto>();
		returnBooks.forEach(books::add);
		/*for(BookDto booksDto : returnBooks){
			books.add(mapper.fromBookDtoToBook(booksDto));
		}*/
 		return books;
	}

	@Override
	public Book findOne(Long id) {
		return bookRepository.findOne(id);
	}

	@Override
	public BookDto findOneES(Long id) {
		return bookESRepository.findOne(id);
	}

	@Override
	public void deleteAllES() {
		bookESRepository.deleteAll();
	}

	@Override
	public Book update(Book book) {
		Book savedBook = bookRepository.save(book);
		operations.putMapping(BookDto.class);
		BookDto bookDto = mapper.fromBookToBookDto(savedBook);
		bookDto.setContent(book.getContent());
		bookESRepository.save(bookDto);
		return savedBook;
	}

	@Override
	public List<BookDto> findByCategoryES(String category) {
		return bookESRepository.findByCategory(category);
	}

	@Override
	public File downloadBook(Long id) {
		
		Book book = findOne(id);
		File pdf = new File("C:\\Users\\Dejan\\git\\udd\\app\\src\\main\\resource\\pdf\\" + book.getFilename());
		if(!pdf.exists()){
			return null;	
		}
		
		return pdf;
	}

	
}
