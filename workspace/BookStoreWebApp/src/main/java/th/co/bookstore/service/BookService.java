package th.co.bookstore.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import th.co.bookstore.common.constant.MessageConstants.ERROR_MESSAGE;
import th.co.bookstore.common.constant.ProjectConstants;
import th.co.bookstore.common.constant.ProjectConstants.FLAG;
import th.co.bookstore.common.constant.ProjectConstants.TransactionManagerRef;
import th.co.bookstore.common.exception.BusinessException;
import th.co.bookstore.dao.BookRepository;
import th.co.bookstore.dao.entity.Book;
import th.co.bookstore.model.BookVo;
import th.co.bookstore.model.GetBookResponse;
import th.co.bookstore.model.RestBookResponse;

@Service
public class BookService {

	private static final Logger log = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private RestApiBookService restApiBookService;
	
	@Autowired
	private BookRepository bookRepository;
	
	public void provisioningBookData(GetBookResponse response) throws Exception {
		List<BookVo> books = new ArrayList<>();
		
		List<RestBookResponse> bookData = restApiBookService.callRestBookApi();
		List<String> tmpBookName = new ArrayList<>();
		
		if (CollectionUtils.isEmpty(bookData)) {
			throw new BusinessException(ERROR_MESSAGE.ERR0001_CODE, ERROR_MESSAGE.ERR0001_DESC);
		}
		
		for (RestBookResponse book : bookData) {
			if (!tmpBookName.contains(book.getBookName())) {
				BookVo vo = new BookVo();
				vo.setId(book.getId());
				vo.setBookName(book.getBookName());
				vo.setAuthorName(book.getAuthorName());
				vo.setPrice(book.getPrice());
				vo.setRecommended(Boolean.FALSE);
				
				tmpBookName.add(book.getBookName());
				books.add(vo);
			}
		}
		
		prepareRecommenedBook(books);
		
		saveBook(books);
		
		books.sort(Comparator.comparing(BookVo::getBookName));
		response.setBooks(books);
	}
	
	public void prepareRecommenedBook(List<BookVo> books) {
		try {
			List<RestBookResponse> recommenedBooks = restApiBookService.callRestBookRecommendationApi();
			bookLoop:
			for (BookVo book : books) {
				for (RestBookResponse recommenedBook : recommenedBooks) {
					if (prepareRecommenedBookCondition(book.getBookName(), recommenedBook.getBookName())) {
						book.setRecommended(Boolean.TRUE);
						continue bookLoop;
					}
				}
			}
		} catch (Exception e) {
			log.error("exception prepareRecommenedBook {}", e.getMessage());
		}
	}
	
	public boolean prepareRecommenedBookCondition(String bookName, String recommendBookName) {
		if (bookName.equalsIgnoreCase(recommendBookName)) return true;
		return false;
	}
	
	@Transactional(value = TransactionManagerRef.MYSQl_DB, rollbackFor = Exception.class)
	public void saveBook(List<BookVo> bookVos) {
		List<Book> books = new ArrayList<>();
		
		deleteBook();
		
		Date now = new Date();
		for (BookVo bookVo : bookVos) {
			Book book = new Book();
			book.setBookId(bookVo.getId());
			book.setName(bookVo.getBookName());
			book.setAuthor(bookVo.getAuthorName());
			book.setPrice(bookVo.getPrice());
			book.setIsRecommoned(bookVo.isRecommended() ? FLAG.Y_FLAG : FLAG.N_FLAG);
			book.setCreatedBy(ProjectConstants.SYSTEM);
			book.setCreatedDate(now);
			book.setUpdatedBy(ProjectConstants.SYSTEM);
			book.setUpdatedDate(now);
			book.setIsDeleted(FLAG.N_FLAG);
			
			books.add(book);
		}
		
		bookRepository.save(books);
	}
	
	@Transactional(value = TransactionManagerRef.MYSQl_DB, rollbackFor = Exception.class)
	public void deleteBook() {
		List<Book> books = bookRepository.findByIsDeleted(FLAG.N_FLAG);
		
		for (Book book : books) {
			book.setIsDeleted(FLAG.Y_FLAG);
			book.setUpdatedBy(ProjectConstants.SYSTEM);
			book.setUpdatedDate(new Date());
		}
		
		bookRepository.save(books);
	}
}
