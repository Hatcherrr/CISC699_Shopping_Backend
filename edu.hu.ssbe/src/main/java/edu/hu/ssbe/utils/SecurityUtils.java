package edu.hu.ssbe.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hu.ssbe.bean.Account;
import edu.hu.ssbe.http.AuthenticationSuccessResponse;
import edu.hu.ssbe.http.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class SecurityUtils {

	private static final Logger LOGGER = LogManager.getLogger(SecurityUtils.class);
	
	private static final ObjectMapper mapper = new ObjectMapper();

	public static void sendResponse(HttpServletResponse httpServletResponse, int status, String message, Exception exception) throws IOException {
		Response response = new Response(exception == null ? true : false, status, message);
		flushResponse(httpServletResponse, response);
	}
	
    public static void flushResponse(HttpServletResponse httpServletResponse, Response response) throws IOException {
		httpServletResponse.setContentType("application/json;charset=UTF-8");
		httpServletResponse.setStatus(response.getCode());
		PrintWriter writer = httpServletResponse.getWriter();
		writer.write(mapper.writeValueAsString(response));
		writer.flush();
		writer.close();
	}
    
    public static void sendAuthenticationSuccessResponse(HttpServletResponse httpServletResponse, int status, String message, Exception exception, Account user)
			throws IOException {
		Response response = new AuthenticationSuccessResponse(exception == null ? true : false, status, message, user);
		System.out.println(response);
		flushResponse(httpServletResponse, response);
	}
    
    public static boolean isAdmin(Collection<? extends GrantedAuthority> profiles) {
		boolean isAdmin = false;
		for(GrantedAuthority profle: profiles) {
			if(profle.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
			}
		};
		return isAdmin;
	}
}
