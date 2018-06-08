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

import model.SessionData;
import service.impl.SessionComponentImpl;

@WebFilter(filterName="responseTimeFilter", urlPatterns= "/*")
public class ResponseTimeFilter implements Filter {
	
	private static final String CSS_LOCATION = "/css";
	
	private static final String STRING_TO_REPLACE = "DEBUG_INFO";
	
	private SessionComponentImpl sessionComponent = SessionComponentImpl.getInstance();
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		
		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		String url = httpRequest.getRequestURL().toString();
		
		if(!url.contains(CSS_LOCATION)){
			long startTime = System.currentTimeMillis();
			ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse)response);
			chain.doFilter(request, wrapper);
			long responseTime = System.currentTimeMillis() - startTime;

			HttpSession session = httpRequest.getSession();
			SessionData data = sessionComponent.getSessionData(session);
	        String responseText = wrapper.getResponseContent();

	        String debugInfo;
	        if(data != null){
	        	debugInfo = String.format( "page: %d ms, db: %d req (%.3f ms)",
	        		responseTime, data.getQueriesCount(), data.getQueriesExecutionTime() * 1000);
	        	sessionComponent.resetQueryInfo(session);
	        } else {
	        	debugInfo = String.format( "page: %d ms, db: %d req (%d ms)",
		        		responseTime, 0, 0);
	        }
	        
	        responseText = responseText.replace(STRING_TO_REPLACE, debugInfo);
	        response.getWriter().print(responseText);
		} else {
			chain.doFilter(request, response);
		}
	}
	
	@Override
	public void destroy() {}

}