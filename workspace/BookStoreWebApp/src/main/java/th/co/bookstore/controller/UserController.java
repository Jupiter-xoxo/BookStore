package th.co.bookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/api/test")
	public ResponseEntity<?> getAppTest() {
		return new ResponseEntity<String>("Test Success", HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public ResponseEntity<?> test() {
		return new ResponseEntity<String>("Test Success1", HttpStatus.OK);
	}
}
