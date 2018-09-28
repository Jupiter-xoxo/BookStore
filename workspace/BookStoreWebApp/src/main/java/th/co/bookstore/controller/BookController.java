package th.co.bookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.bookstore.common.exception.BusinessException;
import th.co.bookstore.model.GetBookResponse;
import th.co.bookstore.service.BookService;

@RestController
@RequestMapping("/api")
public class BookController {

	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/book")
	public ResponseEntity<?> getBook(HttpServletRequest httpRequest) {

		GetBookResponse response = new GetBookResponse();
		try {
			bookService.provisioningBookData(response);
		} catch (BusinessException e) {
			log.error("business exception get book : ", e.getMessage());
			return new ResponseEntity<GetBookResponse>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("exception get book : ", e.getMessage());
			return new ResponseEntity<GetBookResponse>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<GetBookResponse>(response, HttpStatus.OK);
	}
}
