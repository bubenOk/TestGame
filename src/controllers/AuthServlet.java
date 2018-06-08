package controllers;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.exceptions.BadCredentialsException;
import service.AuthService;
import service.impl.AuthServiceImpl;

@WebServlet(AuthServlet.NAME)
public class AuthServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "/AuthServlet";

	private static final String COMMAND = "c";
	private static final String LOGIN = "login";
	public static final String LOGOUT = "logout";

	private static final String USERNICK = "usernick";
	private static final String PASSWORD = "password";
	private static final String ERROR_INFO = "errorInfo";

	private AuthService authService = AuthServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dispatch(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dispatch(request, response);
	}
	
	private void dispatch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String command = request.getParameter(COMMAND);
		if (Objects.equals(command, LOGIN)) {
			login(request, response);
		} else if (Objects.equals(command, LOGOUT)) {
			logout(request, response);
		}
	}

	private void login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (authService.isAuthorized(session)) {
			forwardTo(MENU_PAGE_URL, request, response);
			return;
		}

		String usernick = request.getParameter(USERNICK);
		String password = request.getParameter(PASSWORD);

		try {
			authService.authorizeSession(session, usernick, password);
		} catch (BadCredentialsException e) {
			request.setAttribute(ERROR_INFO, "Неправильный пароль");
			forwardTo(LOGIN_PAGE_URL, request, response);
			return;
		}
		
		response.sendRedirect(MainServlet.NAME);
	}

	private void logout(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		authService.invalidateSession(session);

		forwardTo(LOGIN_PAGE_URL, request, response);
	}

}