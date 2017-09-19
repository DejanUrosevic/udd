package ftn.upp.app.elasticsearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import ftn.upp.app.dto.BookDto;
import ftn.upp.app.model.Book;

@Repository
public interface BookElasticSearchRepository extends ElasticsearchRepository<BookDto, Long>{

	public List<BookDto> findByCategory(String category);
}
