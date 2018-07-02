package com.devzy.share.elasticsearch.spring;

import java.io.Serializable;
import java.util.Optional;

import org.apache.lucene.search.Sort;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ElasticsearchSpringRepository<T, ID extends Serializable> extends ElasticsearchRepository<T, ID> {
	<S extends T> S save(S entity);

	Optional<T> findById(ID primaryKey);

	Iterable<T> findAll();

	Iterable<T> findAll(Sort sort);

	Page<T> findAll(Pageable pageable);
	Optional<T> findById(Predicate predicate);  

	  Iterable<T> findAll(Predicate predicate);   

	  long count(Predicate predicate);            

	  boolean exists(Predicate predicate);

	long count();

	void delete(T entity);

	boolean existsById(ID primaryKey);

	Iterable<T> search(QueryBuilder var1);

	Page<T> search(QueryBuilder var1, Pageable var2);

	Page<T> search(SearchQuery var1);

	Page<T> searchSimilar(T var1, String[] var2, Pageable var3);

	void refresh();

	Class<T> getEntityClass();
}
