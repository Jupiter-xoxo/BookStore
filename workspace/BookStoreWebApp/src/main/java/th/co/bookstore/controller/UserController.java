package th.co.bookstore.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.bookstore.common.constant.ProjectConstants;
import th.co.bookstore.common.exception.BusinessException;
import th.co.bookstore.common.utils.ProjectUtils;
import th.co.bookstore.model.CreateUserRequest;
import th.co.bookstore.model.CreateUserResponse;
import th.co.bookstore.model.GetUserResponse;
import th.co.bookstore.model.OrderUserRequest;
import th.co.bookstore.model.OrderUserResponse;
import th.co.bookstore.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/security/users")
	public ResponseEntity<?> getUser(HttpServletRequest httpRequest) {
		String currentUsername = ProjectUtils.getCurrentUsername(httpRequest);
		
		GetUserResponse response = new GetUserResponse();
		try {
			response = userService.findByUsername(currentUsername);
		} catch (BusinessException e) {
			log.error("delete user business exception : {} ", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("delete user exception : {} ", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<GetUserResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request, HttpServletRequest httpRequest) {
		
		if (StringUtils.isBlank(request.getUsername())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (StringUtils.isBlank(request.getPassword())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (request.getDateOfBirth() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			Date date = userService.validateDateFormat(request.getDateOfBirth());
			if (date == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		
		CreateUserResponse response = userService.save(request, ProjectConstants.SYSTEM);
		
		return new ResponseEntity<CreateUserResponse>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/security/users")
	public ResponseEntity<?> deleteUser(HttpServletRequest httpRequest) {
		String currentUsername = ProjectUtils.getCurrentUsername(httpRequest);
		
		try {
			userService.delete(currentUsername);
		} catch (BusinessException e) {
			log.error("delete user business exception : {} ", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("delete user exception : {} ", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/security/users/orders")
	public ResponseEntity<?> ordersUser(@RequestBody OrderUserRequest request, HttpServletRequest httpRequest) {
		if (CollectionUtils.isEmpty(request.getOrders())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		String currentUsername = ProjectUtils.getCurrentUsername(httpRequest);
		
		OrderUserResponse response = new OrderUserResponse();
		try {
			response = userService.orderBookByUser(request, currentUsername);
		} catch (BusinessException e) {
			log.error("orders user business exception : {} ", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("orders user exception : {} ", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<OrderUserResponse>(response, HttpStatus.OK);
	}
}
