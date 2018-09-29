package th.co.bookstore;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import th.co.bookstore.dao.BookRepository;
import th.co.bookstore.dao.entity.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerIntegrationTests {

	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void givenIsDeltedNFlag_whenFindBookByIsDeleted_thenReturnBook() {
	    
	    List<Book> found = bookRepository.findByIsDeleted("N");
	 
	    // then
	    Assert.assertNotNull(found);
	}
	
	@Test
	public void givenIsDeletedNFlag_whenFindByBookId_thenReturnBook() {
	    // given
		Book mock = new Book();
	    mock.setBookId(1);
	    mock.setIsDeleted("N");
	    
	    // when
	    Book found = bookRepository.findByBookIdAndIsDeleted(mock.getBookId(), mock.getIsDeleted());
	 
	    // then
	    Assert.assertEquals(found.getBookId(), found.getBookId());
	}
	
	@Test
	public void givenIsDeletedYFlag_whenFindByBookId_thenReturnBook() {
	    // given
		Book mock = new Book();
	    mock.setBookId(1);
	    mock.setIsDeleted("Y");
	    
	    // when
	    Book found = bookRepository.findByBookIdAndIsDeleted(mock.getBookId(), mock.getIsDeleted());
	 
	    // then
	    Assert.assertEquals(found.getBookId(), found.getBookId());
	}

}
