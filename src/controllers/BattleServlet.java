package controllers;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.BattleResult;
import model.dto.BattleDto;
import model.exceptions.NotInBattleException;
import service.BattleService;
import service.impl.BattleServiceImpl;

@WebServlet(BattleServlet.NAME)
public class BattleServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "/BattleServlet";
	
	private static final String COMMAND = "c";
	private static final String HIT_OPPONENT = "hitOpponent";
	private static final String TAKE_AWARD = "takeAward";
	
	private BattleService battleService = BattleServiceImpl.getInstance();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getParameter(COMMAND);
		if(Objects.equals(command, HIT_OPPONENT)){
			hitOpponent(request, response);
		} else if (Objects.equals(command, TAKE_AWARD)){
			takeAward(request, response);
		} 
	}
	
	private void hitOpponent(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		BattleResult result;
		
		try{
			result = battleService.hitOpponent(session);
		} catch (NotInBattleException e){
			forwardTo(MainServlet.NAME, request, response);
			return;
		}
		
		if(result != null){
			switch(result){
			case loose:
				forwardTo(LOOSE_PAGE_URL, request, response);
				return;
			case win:
				forwardTo(WIN_PAGE_URL, request, response);
				return;
			}
		}
		
		BattleDto dto =  battleService.getBattleDto(session);
			
		forwardToBattlePage(dto, request, response);
	}
	
	private void takeAward(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		battleService.takeAward(session);
		
		forwardTo(MainServlet.NAME, request, response);
	}
	
}