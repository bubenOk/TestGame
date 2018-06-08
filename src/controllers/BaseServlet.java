package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dto.BattleDto;
import model.dto.RatingDto;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static final String LOGIN_PAGE_URL = "/login.jsp";
	
	protected static final String MENU_PAGE_URL = "/menu.jsp";
	
	protected static final String OPPONENT_SEARCH_PAGE_URL = "/opponentSearch.jsp";
	
	private static final String DUELS_PAGE_URL = "/duels.jsp";
	
	private static final String COUNTDOWN_PAGE_URL = "/battleCountdown.jsp";
	
	private static final String BATTLE_PAGE_URL = "/battle.jsp";

	protected static final String LOOSE_PAGE_URL = "/loose.jsp";
	
	protected static final String WIN_PAGE_URL = "/win.jsp";
	
	protected static final String DTO = "dto";
	
	protected void forwardTo(String location, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		request.getRequestDispatcher(location).forward(request, response);
	}
	
	protected void forwardToBattlePage(BattleDto dto, HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute(DTO, dto);
		
		forwardTo(BATTLE_PAGE_URL, request, response);
	}
	
	protected void forwardToCountdownPage(BattleDto dto, HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute(DTO, dto);
		
		forwardTo(COUNTDOWN_PAGE_URL, request, response);
	}
	
	protected void forwardToDuelsPage(RatingDto ratingDto, HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute(DTO, ratingDto);
		
		forwardTo(DUELS_PAGE_URL, request, response);
	}
	
}
