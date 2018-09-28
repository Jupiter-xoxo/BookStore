package th.co.bookstore.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import th.co.bookstore.config.security.constant.SecurityConstants;
import th.co.bookstore.config.security.constant.SecurityConstants.ROLE;
import th.co.bookstore.config.security.constant.SecurityConstants.URL;
import th.co.bookstore.config.security.entrypoint.RestAuthenticationEntryPoint;
import th.co.bookstore.config.security.handler.RestAuthenticationSuccessHandler;
import th.co.bookstore.config.security.handler.RestLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
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
	
	// Basic authorize
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.antMatcher("/api/**")
//			.authorizeRequests().anyRequest()
//			.hasAnyRole(ROLE.USER)
//		.and()
//		.authorizeRequests()
//			.anyRequest().authenticated()
//		.and()
//		.httpBasic()
//			.authenticationEntryPoint(restAuthenticationEntryPoint());
//		http.csrf().disable();
//	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("username").password("password").roles("USER");
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs","/swagger.json", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList(
				"OPTIONS", 
        		"GET", 
        		"POST", 
        		"PUT", 
        		"DELETE"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
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