package service;

import javax.servlet.http.HttpSession;

import model.User.BattleResult;
import model.dto.BattleDto;
import model.exceptions.NotInBattleException;

public interface BattleService {
	
	public enum BattleStatus{
		idle, opponentSearch, countdown, battle, takeReward 
	}
	
	public BattleStatus getBattleStatus(HttpSession session);
	
	public BattleDto getBattleDto(HttpSession session);
	
	public BattleResult hitOpponent(HttpSession session) throws NotInBattleException;
	
	public BattleResult getBattleResult(HttpSession session);
	
	public void takeAward(HttpSession session);
		
}
