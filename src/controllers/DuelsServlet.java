package controllers;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dto.RatingDto;
import model.exceptions.NotReadyForBattleException;
import service.MatchmakingService;
import service.impl.MatchmakingServiceImpl;

@WebServlet(DuelsServlet.NAME)
public class DuelsServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "/DuelsServlet";
	
	private static final String COMMAND = "c";
	private static final String SHOW_RATING = "showRating";
	private static final String PREPARE_FOR_DUEL = "prepareForDuel";
	private static final String TRY_START_DUEL = "tryStartDuel";
	private static final String CANCEL_DUEL = "cancelDuel";
	
	private MatchmakingService matchmakingService = MatchmakingServiceImpl.getInstance();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getParameter(COMMAND);
		if(Objects.equals(command, SHOW_RATING)){
			showRating(request, response);
		} else if (Objects.equals(command, PREPARE_FOR_DUEL)){
			prepareForDuel(request, response);
		} else if(Objects.equals(command,  TRY_START_DUEL)){
			tryStartDuel(request, response);
		} else if (Objects.equals(command, CANCEL_DUEL)){
			cancelDuel(request, response);
		}
	}
	
	private void showRating(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		RatingDto dto = matchmakingService.getUserRatingDto(session);
		
		forwardToDuelsPage(dto, request, response);
	}
	
	private void prepareForDuel(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		matchmakingService.prepareWarrior(session);
		
		try{
			matchmakingService.markMeReadyForBattle(session);
		} catch (NotReadyForBattleException e){
			forwardTo(MainServlet.NAME, request, response);
			return;
		}
		
		forwardTo(OPPONENT_SEARCH_PAGE_URL, request, response);
	}
	
	private void tryStartDuel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		forwardTo(MainServlet.NAME, request, response);
	}
	
	private void cancelDuel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		matchmakingService.tryCancelDuel(session);
		
		forwardTo(MainServlet.NAME, request, response);
	}
	
}