package th.co.bookstore.config.security.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import th.co.bookstore.config.security.constant.SecurityConstants.ROLE;
import th.co.bookstore.config.security.model.CustomUserPrincipal;

@Component("wsAuthenticationProvider")
public class WebServiceAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
	@Override
	protected void additionalAuthenticationChecks(org.springframework.security.core.userdetails.UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
	}
	
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		System.out.println("WebServiceAuthenticationProvider");
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		SimpleGrantedAuthority role = new SimpleGrantedAuthority(ROLE.USER);
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
		roles.add(role);
		CustomUserPrincipal userDetails = new CustomUserPrincipal(
			name,
			password,
			roles 
		);
	
		
		return userDetails;
	}
	
}
