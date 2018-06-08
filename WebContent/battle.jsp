<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:wrapper>
	<h1>Сражение</h1>
	<div class="green">Атака: ${dto.myDamage} Здоровье: ${dto.myHealth}</div>
	<div>
		<div class="iblock pnlBig">
			<div class="red">${dto.opponentName}</div>
			<div>Атака: ${dto.opponentDamage}</div>
			<div class="pb1">
				<div class="pb2" style="width:${dto.opponentHealthPercent}%" ></div>
				<div class="pb3">${dto.opponentHealth}</div>
			</div>
		</div>
	</div>		
	<div><a class="btn" href="BattleServlet?c=hitOpponent">Бить</a></div>		
</t:wrapper>