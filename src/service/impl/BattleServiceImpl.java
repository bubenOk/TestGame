package service.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpSession;

import model.SessionData;
import model.User;
import model.User.BattleResult;
import model.Warrior;
import model.dao.UserDao;
import model.dto.BattleDto;
import model.exceptions.NotInBattleException;
import service.BattleService;
import service.MatchmakingService.StrictWarriorComparator;
import utils.JPAUtils;
import utils.JPAUtils.QueryInfo;

public class BattleServiceImpl implements BattleService  {
	
	private BattleServiceImpl(){}
	
	private static BattleService instance = new BattleServiceImpl();
	
	public static BattleService getInstance(){return instance;}
	
	
	private static long COUNTDOWN_TIME = 30000;
	
	private SessionComponentImpl sessionComponent = SessionComponentImpl.getInstance();
	
	private UserDao userDao = UserDao.getInstance();
	
	@Override
	public BattleResult getBattleResult(HttpSession session){
		
		EntityManager em = JPAUtils.getEm();
		EntityTransaction et = em.getTransaction();
		
		try{
			et.begin();
			
			JPAUtils.startProfiling(em);
			
			Long userId = sessionComponent.getLoggedInUserId(session);
			
			User user = userDao.getById(em, userId);
			
			QueryInfo queryInfo = JPAUtils.completeProfiling(em);
			
			et.commit();
			
			sessionComponent.storeQueryInfo(session, queryInfo);
			
			return user.getBattleResult();
			
		} catch (Exception ex){
			et.rollback();
			throw ex;
		} finally{
			em.close();
		}
		
	}
	
	@Override
	public BattleStatus getBattleStatus(HttpSession session){
		
		EntityManager em = JPAUtils.getEm();
		EntityTransaction et = em.getTransaction();
		
		SessionData data = sessionComponent.getSessionData(session);
		User user;
		try{
			et.begin();
			
			JPAUtils.startProfiling(em);
			
			Long userId = sessionComponent.getLoggedInUserId(session);
			
			user = userDao.getById(em, userId);
			
			QueryInfo queryInfo = JPAUtils.completeProfiling(em);
			
			et.commit();
			
			sessionComponent.storeQueryInfo(session, queryInfo);
			
		} catch (Exception ex){
			System.out.println(ex);
			et.rollback();
			throw ex;
		} finally {
			em.close();
		}
		
		return getBattleStatus(user, data);
		
	}
	
	@Override
	public BattleResult hitOpponent(HttpSession session) throws NotInBattleException{
	
		EntityManager em = JPAUtils.getEm();
		EntityTransaction et = em.getTransaction();
		
		try{
			et.begin();
			
			JPAUtils.startProfiling(em);
			
			SessionData data = sessionComponent.getSessionData(session);
			
			Long userId = sessionComponent.getLoggedInUserId(session);
			
			User user = userDao.getById(em, userId);
			
			if(getBattleStatus(user, data) != BattleStatus.battle){
				throw new NotInBattleException();
			}
			
			Warrior opponent = data.getOpponent();
			
			Warrior monitor = chooseSmaller(opponent, data.getWarrior());
			
			BattleResult result = null;
			
			synchronized(monitor){
				if(data.getWarrior().getHealth() > 0){
					opponent.setHealth(Math.max(opponent.getHealth() - data.getWarrior().getDamage(), 0));
				}
				
				if(data.getOpponent().getHealth() == 0){
					data.setReadyForBattle(false);
					data.setOpponentFoundAt(null);
					
					User opponentUser = userDao.getById(em, opponent.getUserId());
					opponentUser.setBattleResult(BattleResult.loose);
					user.setBattleResult(BattleResult.win);
					result = BattleResult.win;
					
						
				} else if(data.getWarrior().getHealth() == 0){
					data.setReadyForBattle(false);
					data.setOpponentFoundAt(null);
					
					result = BattleResult.loose;
				}
			}
			
			QueryInfo queryInfo = JPAUtils.completeProfiling(em);
			
			et.commit();
			
			sessionComponent.storeQueryInfo(session, queryInfo);
			
			return result;
			
		} catch (Exception ex){
			et.rollback();
			throw ex;
		} finally {
			em.close();
		}
		
	}
	
	@Override
	public void takeAward(HttpSession session){
		
		EntityManager em = JPAUtils.getEm();
		EntityTransaction et = em.getTransaction();
		
		try{
			et.begin();
			
			JPAUtils.startProfiling(em);
			
			SessionData data = sessionComponent.getSessionData(session);
			Long userId = data.getUserId();
			User user = userDao.getById(em, userId);
		
			if(user.getBattleResult() != null){
				switch(user.getBattleResult()){
				case loose:
					user.setRating(user.getRating() - 1);
					break;
				case win:
					user.setRating(user.getRating() + 1);
					break;
				}
				user.setDamage(user.getDamage() + 1);
				user.setHealth(user.getHealth() + 1);
				user.setBattleResult(null);
				data.setReadyForBattle(false);
				data.setOpponentFoundAt(null);
			}
			
			QueryInfo queryInfo = JPAUtils.completeProfiling(em);
			
			et.commit();
			
			sessionComponent.storeQueryInfo(session, queryInfo);
			
		} catch (Exception ex){
			et.rollback();
			throw ex;
		} finally {
			em.close();
		}
	}
	
	private Warrior chooseSmaller(Warrior first, Warrior second){
		boolean firstSmaller = new StrictWarriorComparator().compare(first, second) == 0;
		return firstSmaller? first : second;
	}
	
	private BattleStatus getBattleStatus(User user, SessionData data){
		Date opponentFoundAt = data.getOpponentFoundAt();
		
		if(user.getBattleResult() != null){
			return BattleStatus.takeReward;
	 	} else if (data.isReadyForBattle() && opponentFoundAt == null && !data.isExpired()){
	 		return BattleStatus.opponentSearch;
	 	} else if (data.isReadyForBattle() && opponentFoundAt != null && opponentFoundAt.getTime() 
	 			+ COUNTDOWN_TIME > System.currentTimeMillis()){
	 		return BattleStatus.countdown;
	 	} else if (data.isReadyForBattle() && opponentFoundAt != null && opponentFoundAt.getTime() 
	 			+ COUNTDOWN_TIME <= System.currentTimeMillis()){
	 		return BattleStatus.battle;
	 	} else {
	 		return BattleStatus.idle;
	 	}
	}
	
	public BattleDto getBattleDto(HttpSession session){
		SessionData data = sessionComponent.getSessionData(session);
		
		long remainMillis = (data.getOpponentFoundAt().getTime() + COUNTDOWN_TIME)
				- System.currentTimeMillis();
		
		int healthPercent = (int) ((float)data.getOpponent().getHealth() * 100.0 / data.getOpponent().getMaxHealth());
		
		return new BattleDto(data.getOpponent().getName(), data.getOpponent().getDamage(),
				data.getOpponent().getHealth(), data.getWarrior().getName(), 
				data.getWarrior().getDamage(), data.getWarrior().getHealth(),healthPercent,
				(int) remainMillis / 1000);
	}
}
