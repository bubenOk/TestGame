package service.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpSession;

import model.SessionData;
import model.User;
import model.Warrior;
import model.dao.UserDao;
import model.dto.RatingDto;
import model.exceptions.NotReadyForBattleException;
import service.MatchmakingService;
import utils.JPAUtils;
import utils.JPAUtils.QueryInfo;

public class MatchmakingServiceImpl implements MatchmakingService{
	
	private MatchmakingServiceImpl() { matchMaker.start();}
	
	private static MatchmakingService instance = new MatchmakingServiceImpl();
	
	public static MatchmakingService getInstance(){	return instance;}
	
	
	private SessionComponentImpl sessionComponent = SessionComponentImpl.getInstance();
	
	private UserDao userDao = UserDao.getInstance();
	
	
	private ConcurrentSkipListSet<SessionData> readyForBattle = new ConcurrentSkipListSet<SessionData>(
				new Comparator<SessionData>(){

					@Override
					public int compare(SessionData arg0, SessionData arg1) {
						return new StrictWarriorComparator().compare(arg0.getWarrior(), arg1.getWarrior());
					}
					
				});
	
	private Thread matchMaker = new Thread() {
		public void run() {
			try {
				while (true) {
					while (readyForBattle.size() >= 2) {
						
						SessionData firstData = readyForBattle.first();
						readyForBattle.remove(firstData);
						
						if(firstData.isExpired()){
							firstData.setReadyForBattle(false);
							continue;
						}
						
						SessionData secondData = readyForBattle.first();
						readyForBattle.remove(secondData);
						
						if(secondData.isExpired()){
							secondData.setReadyForBattle(false);
							readyForBattle.add(firstData);
							continue;
						}
						
						firstData.setOpponent(secondData.getWarrior());
						secondData.setOpponent(firstData.getWarrior());

						Date now = new Date();

						firstData.setOpponentFoundAt(now);
						secondData.setOpponentFoundAt(now);

					}

					Thread.sleep(1000);
				}
			} catch (InterruptedException v) {
				System.out.println(v);
			}
		}
	};
	
	
	@Override
	public void markMeReadyForBattle(HttpSession session) throws NotReadyForBattleException {
		
		EntityManager em = JPAUtils.getEm();
		EntityTransaction et = em.getTransaction();
		
		try{
			et.begin();
			
			JPAUtils.startProfiling(em);
			
			Long userId = sessionComponent.getLoggedInUserId(session);
			
			User user = userDao.getById(em, userId);
			
			if(user.getBattleResult() != null){
				throw new NotReadyForBattleException();
			}
			
			SessionData sessionData = sessionComponent.getSessionData(session);
			sessionData.setReadyForBattle(true);
			readyForBattle.add(sessionData);
			
			QueryInfo queryInfo = JPAUtils.completeProfiling(em);
			
			et.commit();
			
			sessionComponent.storeQueryInfo(session, queryInfo);
			
		} catch(Exception e){
			et.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void prepareWarrior(HttpSession session) {
		EntityManager em = JPAUtils.getEm();
		EntityTransaction et = em.getTransaction();
		
		try{
			et.begin();
			
			JPAUtils.startProfiling(em);
			
			Long userId = sessionComponent.getLoggedInUserId(session);
			
			User user = userDao.getById(em, userId);
			
			Warrior warrior = new Warrior(user.getId(), user.getUsernick(),
					user.getDamage(), user.getHealth(), user.getHealth(), user.getRating());
			
			SessionData sessionData = sessionComponent.getSessionData(session);
			
			sessionData.setWarrior(warrior);
			
			sessionData.setExpired(false);
			
			QueryInfo queryInfo = JPAUtils.completeProfiling(em);
			
			et.commit();
			
			sessionComponent.storeQueryInfo(session, queryInfo);
			
		} catch(Exception e){
			et.rollback();
			throw e;
		} finally {
			em.close();
		}
		
	}

	@Override
	public RatingDto getUserRatingDto(HttpSession session) {

		EntityManager em = JPAUtils.getEm();
		EntityTransaction et = em.getTransaction();
		
		try{
			et.begin();
			
			JPAUtils.startProfiling(em);
			
			Long userId = sessionComponent.getLoggedInUserId(session);
			
			User currentUser = userDao.getById(em, userId);
			
			QueryInfo queryInfo = JPAUtils.completeProfiling(em);
			
			et.commit();
			
			sessionComponent.storeQueryInfo(session, queryInfo);
			
			return new RatingDto(currentUser.getRating());
			
		} catch (Exception ex){
			et.rollback();
			throw ex;
		} finally {
			em.close();
		}
	}

	@Override
	public void tryCancelDuel(HttpSession session) {
		SessionData data = sessionComponent.getSessionData(session);
		
		data.setExpired(true);
		
	}
}
