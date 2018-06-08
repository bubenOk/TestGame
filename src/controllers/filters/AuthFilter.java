package controllers.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.AuthService;
import service.impl.AuthServiceImpl;
import controllers.AuthServlet;
import controllers.BaseServlet;

@WebFilter(filterName="authFilter", urlPatterns= "/*")
public class AuthFilter implements Filter {
	
	private static final String CSS_LOCATION = "/css";
	
	private AuthService authService = AuthServiceImpl.getInstance();

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
    throws IOException, ServletException {
		
		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		HttpServletResponse httpResponse = ((HttpServletResponse)response);
		HttpSession session = httpRequest.getSession();
		String url = httpRequest.getRequestURL().toString();

		if(!authService.isAuthorized(session) && !url.contains(BaseServlet.LOGIN_PAGE_URL) 
    			&& !url.contains(CSS_LOCATION) && !url.contains(AuthServlet.NAME)){
			httpResponse.sendRedirect(BaseServlet.LOGIN_PAGE_URL);
    	} else{
    		filterChain.doFilter(request, response);
    	}
    }

	@Override
    public void destroy() {}
}