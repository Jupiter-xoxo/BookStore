package th.co.bookstore.service;

import java.util.Date;

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
import th.co.bookstore.dao.OrderRepository;
import th.co.bookstore.dao.UserRepository;
import th.co.bookstore.dao.entity.Order;
import th.co.bookstore.dao.entity.User;
import th.co.bookstore.model.DeleteUserRequest;
import th.co.bookstore.model.GetUserResponse;
import th.co.bookstore.model.PostUserRequest;
import th.co.bookstore.model.PostUserResponse;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public GetUserResponse findByUsername(String username) {
		log.info("get current username = {} ", username);
		
		User user = userRepository.findByUsername(username);
		
		GetUserResponse response = new GetUserResponse();
		response.setName(user.getName());
		response.setSurname(user.getSurname());
		response.setDateOfBirth(ProjectDateUtils.convertDate(user.getDateOfBirth(), DATE_PATTERN.DD_MM_YYYY));
				
		return response;
	}
	
	@Transactional(value = TransactionManagerRef.MYSQl_DB, rollbackFor = Exception.class)
	public PostUserResponse save(PostUserRequest request, String currentUsername) {
		Date nowDate = new Date();
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setDateOfBirth(request.getDateOfBirth());
		user.setCreatedBy(currentUsername);
		user.setCreatedDate(nowDate);
		user.setUpdatedBy(currentUsername);
		user.setUpdatedDate(nowDate);
		user.setIsDeleted(FLAG.N_FLAG);
		User saveUser = userRepository.save(user);
		
		log.info("save username = {} ", request.getUsername());

		PostUserResponse response = new PostUserResponse();
		response.setUsername(saveUser.getUsername());
		response.setPassword(saveUser.getPassword());
		response.setDateOfBirth(ProjectDateUtils.convertDate(saveUser.getDateOfBirth(), DATE_PATTERN.DD_MM_YYYY));
		
		return response;
	}
	
	public void delete(DeleteUserRequest request, String username) throws Exception {
		User user = userRepository.findByUsername(request.getUsername());
		
		if (user == null) {
			throw new BusinessException(ERROR_MESSAGE.ERR0001_CODE, ERROR_MESSAGE.ERR0001_DESC);
		}
		Date nowDate = new Date();
		user.setIsDeleted(FLAG.Y_FLAG);
		user.setUpdatedBy(username);
		user.setUpdatedDate(nowDate);
		
		userRepository.save(user);
		
		Order order = orderRepository.findByUserIdAndIsDeleted(user.getId(), FLAG.N_FLAG);
		
		if (order != null) {
			order.setIsDeleted(FLAG.Y_FLAG);
			user.setUpdatedBy(username);
			user.setUpdatedDate(nowDate);
			
			orderRepository.save(order);
		}
	}
}
