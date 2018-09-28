package th.co.bookstore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import th.co.bookstore.dao.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

	List<Book> findByIsDeleted(String isDeleted);
}
