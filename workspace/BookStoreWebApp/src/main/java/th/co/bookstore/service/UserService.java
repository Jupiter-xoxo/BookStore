package th.co.bookstore.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.bookstore.common.constant.MessageConstants.ERROR_MESSAGE;
import th.co.bookstore.common.constant.ProjectConstants.FLAG;
import th.co.bookstore.common.constant.ProjectConstants.TransactionManagerRef;
import th.co.bookstore.common.exception.BusinessException;
import th.co.bookstore.common.utils.ProjectDateUtils;
import th.co.bookstore.common.utils.ProjectDateUtils.DATE_PATTERN;
import th.co.bookstore.common.utils.ProjectUtils;
import th.co.bookstore.dao.BookOrderRepository;
import th.co.bookstore.dao.BookRepository;
import th.co.bookstore.dao.OrderRepository;
import th.co.bookstore.dao.UserRepository;
import th.co.bookstore.dao.entity.Book;
import th.co.bookstore.dao.entity.BookOrder;
import th.co.bookstore.dao.entity.Order;
import th.co.bookstore.dao.entity.User;
import th.co.bookstore.model.CreateUserRequest;
import th.co.bookstore.model.CreateUserResponse;
import th.co.bookstore.model.GetUserResponse;
import th.co.bookstore.model.OrderUserRequest;
import th.co.bookstore.model.OrderUserResponse;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookOrderRepository bookOrderRepository;
	
	public GetUserResponse findByUsername(String username) throws Exception {
		log.info("get current username = {} ", username);
		
		User user = userRepository.findByUsernameAndIsDeleted(username, FLAG.N_FLAG);
		
		if (user == null) {
			throw new BusinessException(ERROR_MESSAGE.ERR0001_CODE, ERROR_MESSAGE.ERR0001_DESC);
		}
		
		GetUserResponse response = new GetUserResponse();
		response.setName(user.getName());
		response.setSurname(user.getSurname());
		response.setDateOfBirth(ProjectDateUtils.convertDate(user.getDateOfBirth(), DATE_PATTERN.DD_MM_YYYY));
		
		List<Integer> books = new ArrayList<>();
		List<Order> orders = orderRepository.findByUserIdAndIsDeleted(user.getId(), FLAG.N_FLAG);
		for (Order order : orders) {
			List<BookOrder> bookOrders = bookOrderRepository.findByOrderId(order.getId());
			for (BookOrder bookOrder : bookOrders) {
				Book book = bookRepository.findById(bookOrder.getBookId());
				if (book != null && !books.contains(book.getBookId())) {
					books.add(book.getBookId());
				}
			}
		}
		response.setBooks(books);
		return response;
	}
	
	@Transactional(value = TransactionManagerRef.MYSQl_DB, rollbackFor = Exception.class)
	public CreateUserResponse save(CreateUserRequest request, String currentUsername) {
		Date nowDate = new Date();
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		
		Date dateOfBirth = validateDateFormat(request.getDateOfBirth());
				
		user.setDateOfBirth(dateOfBirth);
		user.setCreatedBy(currentUsername);
		user.setCreatedDate(nowDate);
		user.setUpdatedBy(currentUsername);
		user.setUpdatedDate(nowDate);
		user.setIsDeleted(FLAG.N_FLAG);
		User saveUser = userRepository.save(user);
		
		log.info("save username = {} ", request.getUsername());

		CreateUserResponse response = new CreateUserResponse();
		response.setUsername(saveUser.getUsername());
		response.setPassword(saveUser.getPassword());
		response.setDateOfBirth(ProjectDateUtils.convertDate(saveUser.getDateOfBirth(), DATE_PATTERN.DD_MM_YYYY));
		
		return response;
	}
	
	public Date validateDateFormat(String dateStr) {
		Date date = null;
		try {
			if (StringUtils.isNotBlank(dateStr)) {
				date = DateUtils.parseDate(dateStr, DATE_PATTERN.DD_MM_YYYY);
			}
		} catch (Exception e) {
			log.error("date format in correct  date_of_birth : {} ", dateStr);
		}
		return date;
	}
	
	@Transactional(value = TransactionManagerRef.MYSQl_DB, rollbackFor = Exception.class)
	public void delete(String currentUsername) throws Exception {
		User user = userRepository.findByUsernameAndIsDeleted(currentUsername, FLAG.N_FLAG);
		
		if (user == null) {
			throw new BusinessException(ERROR_MESSAGE.ERR0001_CODE, ERROR_MESSAGE.ERR0001_DESC);
		}
		Date nowDate = new Date();
		user.setIsDeleted(FLAG.Y_FLAG);
		user.setUpdatedBy(currentUsername);
		user.setUpdatedDate(nowDate);
		
		userRepository.save(user);
		
		List<Order> orders = orderRepository.findByUserIdAndIsDeleted(user.getId(), FLAG.N_FLAG);
		
		for (Order order : orders) {
			order.setIsDeleted(FLAG.Y_FLAG);
			user.setUpdatedBy(currentUsername);
			user.setUpdatedDate(nowDate);
		}
		orderRepository.save(orders);
	}
	
	public OrderUserResponse orderBookByUser(OrderUserRequest request, String currentUsername) throws Exception {
		OrderUserResponse response = new OrderUserResponse();

		List<Integer> orders = request.getOrders();
		
		User user = userRepository.findByUsernameAndIsDeleted(currentUsername, FLAG.N_FLAG);
		
		if (user == null) {
			throw new BusinessException(ERROR_MESSAGE.ERR0001_CODE, ERROR_MESSAGE.ERR0001_DESC);
		}
		
		Date now = new Date();
		Order orderTrns = new Order();
		orderTrns.setTransactionId(ProjectUtils.generateTransactionId(orderRepository.findLastTransactionSeq() + 1));
		orderTrns.setUserId(user.getId());
		orderTrns.setCreatedBy(currentUsername);
		orderTrns.setCreatedDate(now);
		orderTrns.setUpdatedBy(currentUsername);
		orderTrns.setUpdatedDate(now);
		orderTrns.setIsDeleted(FLAG.N_FLAG);
		Order orderSave = orderRepository.save(orderTrns);
		
		Integer orderId = orderSave.getId();
		log.info("orderBookByUser user = {}, order transaction = {} ", currentUsername, orderSave.getTransactionId());
		
		List<BookOrder> bookOrders = new ArrayList<>();
		
		BigDecimal price = new BigDecimal(BigInteger.ZERO);
		for (Integer bookId : orders) {
			BookOrder bookOrder = new BookOrder();
			bookOrder.setOrderId(orderId);
			
			Book book = bookRepository.findByBookIdAndIsDeleted(bookId, FLAG.N_FLAG);
			if (book != null) {
				bookOrder.setBookId(book.getId());
				price = price.add(book.getPrice());
			}
			bookOrders.add(bookOrder);
		}
		
		bookOrderRepository.save(bookOrders);
		
		response.setPrice(price);
		return response;
	}
}
