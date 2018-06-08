package service;

import javax.servlet.http.HttpSession;

import model.exceptions.BadCredentialsException;

public interface AuthService {
		
		public boolean isAuthorized(HttpSession session);
		
		public void authorizeSession(HttpSession session, String usernick, String password)
				throws BadCredentialsException;
		
		public void invalidateSession(HttpSession session);

}
