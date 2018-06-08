package service;

import java.util.Comparator;

import javax.servlet.http.HttpSession;

import model.Warrior;
import model.dto.RatingDto;
import model.exceptions.NotReadyForBattleException;

public interface MatchmakingService {
	
	public static class StrictWarriorComparator implements Comparator<Warrior>{

		@Override
		public int compare(Warrior o1, Warrior o2) {
			if(o1.getRating() != o2.getRating()){
				return Integer.compare(o1.getRating(), o2.getRating());
			}
			
			return o1.getName().compareTo(o2.getName());
		}
		
	}
	
	public RatingDto getUserRatingDto(HttpSession session);
	
	public void prepareWarrior(HttpSession session);
	
	public void markMeReadyForBattle(HttpSession session) throws NotReadyForBattleException;
	
	public void tryCancelDuel(HttpSession session);
	
}
