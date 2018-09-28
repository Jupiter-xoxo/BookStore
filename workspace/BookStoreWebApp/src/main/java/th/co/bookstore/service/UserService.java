package th.co.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.bookstore.dao.UserRepository;
import th.co.bookstore.dao.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
