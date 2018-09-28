package th.co.bookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.bookstore.common.utils.ProjectUtils;
import th.co.bookstore.dao.entity.User;
import th.co.bookstore.service.UserService;

@RestController
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/api/test")
	public ResponseEntity<?> getAppTest() {
		return new ResponseEntity<String>("Test Success", HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public ResponseEntity<?> test() {
		return new ResponseEntity<String>("Test Success1", HttpStatus.OK);
	}
	
	@GetMapping("/api/users")
	public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
		String currentUsername = ProjectUtils.getCurrentUsername(request);
		log.info("get current username = {} ", currentUsername);
		
		User user = userService.findByUsername(currentUsername);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/api/users")
	public ResponseEntity<?> pushUserInfo(HttpServletRequest request) {
		String currentUsername = ProjectUtils.getCurrentUsername(request);
		log.info("get current username = {} ", currentUsername);
		
		User user = userService.findByUsername(currentUsername);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
