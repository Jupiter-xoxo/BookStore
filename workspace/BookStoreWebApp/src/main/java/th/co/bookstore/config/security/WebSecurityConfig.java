package th.co.bookstore.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import th.co.bookstore.common.constant.SecurityConstants;
import th.co.bookstore.common.constant.SecurityConstants.ROLE;
import th.co.bookstore.common.constant.SecurityConstants.URL;
import th.co.bookstore.config.security.entrypoint.RestAuthenticationEntryPoint;
import th.co.bookstore.config.security.handler.RestAuthenticationSuccessHandler;
import th.co.bookstore.config.security.handler.RestLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	/*
	 * Rest Login API
	 */
	@Configuration
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/api/**")
					.authorizeRequests().anyRequest()
					.hasAnyRole(ROLE.USER)
				.and()
				.formLogin()
					.loginProcessingUrl(URL.LOGIN_REST).permitAll()
					.successHandler(restAuthenticationSuccessHandler())
					.failureHandler(restAuthenticationFailureHandler())
					.usernameParameter(SecurityConstants.USERNAME_PARAM)
					.passwordParameter(SecurityConstants.PASSWORD_PARAM)
				.and()
				.logout()
					.permitAll()
					.logoutRequestMatcher(new AntPathRequestMatcher(URL.LOGIN_REST, HttpMethod.DELETE.toString()))
					.logoutSuccessHandler(restLogoutSuccessHandler())
				.and()
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint())
				.and()
				.requestCache().requestCache(new NullRequestCache())
				.and()
				.sessionManagement()
					.maximumSessions(2)
					.sessionRegistry(sessionRegistry());
			
			http.csrf().disable();
		}
		
		@Bean
		public RestAuthenticationSuccessHandler restAuthenticationSuccessHandler() {
			return new RestAuthenticationSuccessHandler();
		}
		
		@Bean
		public SimpleUrlAuthenticationFailureHandler restAuthenticationFailureHandler() {
			return new SimpleUrlAuthenticationFailureHandler();
		}
		
		@Bean
		public RestLogoutSuccessHandler restLogoutSuccessHandler() {
			return new RestLogoutSuccessHandler();
		}
		
		@Bean
		public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
			return new RestAuthenticationEntryPoint();
		}
		
		@Bean
		public SessionRegistry sessionRegistry() {
			return new SessionRegistryImpl();
		}
		
	}
}