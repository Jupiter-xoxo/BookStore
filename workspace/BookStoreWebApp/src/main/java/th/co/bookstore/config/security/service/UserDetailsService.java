package th.co.bookstore.config.security.service;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import th.co.bookstore.common.constant.ProjectConstants.FLAG;
import th.co.bookstore.config.security.constant.SecurityConstants.ROLE;
import th.co.bookstore.config.security.model.CustomUserPrincipal;
import th.co.bookstore.dao.UserRepository;
import th.co.bookstore.dao.entity.User;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername username={}", username);
		CustomUserPrincipal customUserPrincipal = new CustomUserPrincipal();
		
		User user = userRepository.findByUsernameAndIsDeleted(username, FLAG.N_FLAG);
		
		if (user == null) {
			throw new UsernameNotFoundException(MessageFormat.format("Username {0} not found", username));
		}
		
		log.info("find username={}", user.getUsername());

		customUserPrincipal.setUserName(user.getUsername());
		customUserPrincipal.setPassword(encoder.encode(user.getPassword()));
		
		SimpleGrantedAuthority role = new SimpleGrantedAuthority(ROLE.USER);
		customUserPrincipal.addRole(role);
		customUserPrincipal.setFullName(String.format(user.getName(), user.getSurname()));
		
		return customUserPrincipal;
	}
	
}
