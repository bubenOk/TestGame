package service;

import javax.servlet.http.HttpSession;

import model.SessionData;
import utils.JPAUtils.QueryInfo;

public interface SessionComponent {
	
	public HttpSession getSessionById(String id);
	
	public void addSessionToRegistry(HttpSession session, long userId);
	
	public void removeSessionFromRegistry(HttpSession session);
	
	public void storeDataToSession(SessionData data, HttpSession session);
	
	public SessionData getSessionData(HttpSession session);
	
	
	public Long getLoggedInUserId(HttpSession session);
	
	public void storeQueryInfo(HttpSession session, QueryInfo queryInfo);
	
	public void resetQueryInfo(HttpSession session);
	
}
