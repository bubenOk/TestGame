package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dto.BattleDto;
import service.BattleService;
import service.impl.BattleServiceImpl;

@WebServlet(MainServlet.NAME)
public class MainServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "/MainServlet";
	
	private BattleService battleService = BattleServiceImpl.getInstance();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		switch (battleService.getBattleStatus(session)) {
		case battle:
			BattleDto dto = battleService.getBattleDto(session);
			forwardToBattlePage(dto, request, response);
			break;
		case countdown:
			dto = battleService.getBattleDto(session);
			forwardToCountdownPage(dto, request, response);
			break;
		case idle:
			forwardTo(MENU_PAGE_URL, request, response);
			break;
		case opponentSearch:
			forwardTo(OPPONENT_SEARCH_PAGE_URL, request, response);
			break;
		case takeReward:
			switch (battleService.getBattleResult(session)) {
			case loose:
				forwardTo(LOOSE_PAGE_URL, request, response);
				break;
			case win:
				forwardTo(WIN_PAGE_URL, request, response);
				break;
			}
		}

	}

}