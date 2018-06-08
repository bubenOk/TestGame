package service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import service.SessionComponent;
import utils.JPAUtils.QueryInfo;
import model.SessionData;

public class SessionComponentImpl implements SessionComponent {
	
	private SessionComponentImpl(){}
	
	private static SessionComponentImpl instance = new SessionComponentImpl();
	
	public static SessionComponentImpl getInstance(){return instance;}

	
	private static final String SESSION_DATA = "sessionData";
	
	private Map<String, HttpSession> sessionById = new ConcurrentHashMap<String, HttpSession>();
	private Map<Long, String> sessionIdByUserId = new ConcurrentHashMap<Long, String>();
	
	@Override
	public HttpSession getSessionById(String id){
		return sessionById.get(id);
	}
	
	@Override
	public void addSessionToRegistry(HttpSession session, long userId){
		String oldSessionId = sessionIdByUserId.get(userId);
		HttpSession oldSession = oldSessionId != null? sessionById.get(oldSessionId) : null;
		
		if(oldSession != null){
			oldSession.invalidate();
			sessionById.remove(oldSessionId);
		}
		
		sessionIdByUserId.put(userId, session.getId());
		sessionById.put(session.getId(), session);
	}
	
	@Override
	public void removeSessionFromRegistry(HttpSession session){
		sessionById.remove(session.getId());
	}
	
	@Override
	public void storeDataToSession(SessionData data, HttpSession session){
		session.setAttribute(SESSION_DATA, data);
	}
	
	@Override
	public SessionData getSessionData(HttpSession session){
		return (SessionData)session.getAttribute(SESSION_DATA);
	}
	
	@Override
	public Long getLoggedInUserId(HttpSession session){
		SessionData data = getSessionData(session);
		if(data != null){
			return data.getUserId();
		}
		return null;
	}
	
	@Override
	public void storeQueryInfo(HttpSession session, QueryInfo queryInfo){
		SessionData data = getSessionData(session);
		if(data != null){
			data.setQueriesCount(data.getQueriesCount() + queryInfo.getQueryCount());
			data.setQueriesExecutionTime(data.getQueriesExecutionTime() + queryInfo.getDuration());
		}
	}
	
	@Override
	public void resetQueryInfo(HttpSession session){
		SessionData data = getSessionData(session);
		if(data != null){
			data.setQueriesCount(0);
			data.setQueriesExecutionTime(0);
		}
	}
	
}
