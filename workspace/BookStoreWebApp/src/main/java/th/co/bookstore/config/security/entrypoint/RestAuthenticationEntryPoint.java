package th.co.bookstore.config.security.entrypoint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * The Entry Point will not redirect to any sort of Login - it will return the 401
 */
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

}

//public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
//
//	@Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
//      throws IOException, ServletException {
//        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        PrintWriter writer = response.getWriter();
//        writer.println("HTTP Status 401 - " + authEx.getMessage());
//    }
//
//	@Override
//    public void afterPropertiesSet() throws Exception {
//        setRealmName("DeveloperStack");
//        super.afterPropertiesSet();
//    }
//
//}