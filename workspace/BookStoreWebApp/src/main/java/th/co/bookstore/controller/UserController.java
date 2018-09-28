package th.co.bookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.bookstore.common.constant.ProjectConstants;
import th.co.bookstore.common.exception.BusinessException;
import th.co.bookstore.common.utils.ProjectUtils;
import th.co.bookstore.model.DeleteUserRequest;
import th.co.bookstore.model.GetUserResponse;
import th.co.bookstore.model.PostUserRequest;
import th.co.bookstore.model.PostUserResponse;
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
		GetUserResponse response = userService.findByUsername(currentUsername);
		return new ResponseEntity<GetUserResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> addUser(@RequestBody PostUserRequest request, HttpServletRequest httpRequest) {
		PostUserResponse response = userService.save(request, ProjectConstants.SYSTEM);
		return new ResponseEntity<PostUserResponse>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/security/users")
	public ResponseEntity<?> deleteUser(@RequestBody DeleteUserRequest request, HttpServletRequest httpRequest) {
		String currentUsername = ProjectUtils.getCurrentUsername(httpRequest);
		
		try {
			userService.delete(request, currentUsername);
		} catch (BusinessException e) {
			log.error("delete user business exception : {} ", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("delete user exception : {} ", e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
