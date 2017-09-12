package ftn.upp.app.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import ftn.upp.app.model.Book;

@Repository
public interface BookElasticSearchRepository extends ElasticsearchRepository<Book, Long>{

}
