package service.impl;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpSession;

import model.SessionData;
import model.User;
import model.dao.UserDao;
import model.exceptions.BadCredentialsException;
import service.AuthService;
import service.SessionComponent;
import utils.JPAUtils;
import utils.JPAUtils.QueryInfo;
import utils.SecurityUtils;

public class AuthServiceImpl implements AuthService {
	
	private AuthServiceImpl(){}
	
	private static AuthServiceImpl instance = new AuthServiceImpl();
	
	public static AuthServiceImpl getInstance(){return instance;}
	
	
	private SessionComponent sessionComponent = SessionComponentImpl.getInstance();

	private UserDao userDao = UserDao.getInstance();
	
	@Override
	public boolean isAuthorized(HttpSession session){
		return sessionComponent.getSessionById(session.getId()) != null;
	}
	
	@Override
	public void authorizeSession(HttpSession session, String usernick, String password)
			throws BadCredentialsException{
		
		EntityManager em = JPAUtils.getEm();
		EntityTransaction et = em.getTransaction();

		try{
			et.begin();
			
			JPAUtils.startProfiling(em);
				
			User user = userDao.getByUsernick(em, usernick);
			
			if(user == null){
				String hash = SecurityUtils.createMd5Hash(password);
				
				user = createNewUser(usernick, hash);
				userDao.persist(em, user);
			} else {
				String hash = SecurityUtils.createMd5Hash(password);
				
				if(!Objects.equals(hash, user.getPassword())){
					throw new BadCredentialsException();
				}
			}
			
			sessionComponent.addSessionToRegistry(session, user.getId());
			sessionComponent.storeDataToSession(new SessionData(
					user.getId(), null, false, null, null), session);
			
			QueryInfo queryInfo = JPAUtils.completeProfiling(em);
			
			et.commit();
			
			sessionComponent.storeQueryInfo(session, queryInfo);
		} catch(Exception e){
			et.rollback();
			throw e;
		} finally{
			em.close();
		}
	}
	
	@Override
	public void invalidateSession(HttpSession session){
		sessionComponent.removeSessionFromRegistry(session);
		session.invalidate();	
	}
	
	private User createNewUser(String usernick, String passwordHash){
		User user = new User();
		user.setDamage(10);
		user.setHealth(100);
		user.setRating(0);
		user.setUsernick(usernick);
		user.setPassword(passwordHash);
		user.setBattleResult(null);
		return user;
	}
	
}
