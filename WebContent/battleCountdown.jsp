<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:wrapper>
	<h1>Приготовьтесь к битве!</h1>
	<div>
		<div class="iblock pnl">
			<div class="blue bold">${dto.myName}</div>
			<div>Атака: ${dto.myDamage}</div>
			<div>Здоровье: ${dto.myHealth}</div>
		</div>
		<div class="iblock big pd5">VS</div>
		<div class="iblock pnl" >
			<div class="red bold">${dto.opponentName}</div>
			<div>Атака: ${dto.opponentDamage}</div>
			<div>Здоровье: ${dto.opponentHealth}</div>
		</div>
	</div>		
	<div class="pd5">
		До начала битвы осталось ${dto.remainSeconds} секунд
	</div>
	<div><a class="btn" href="DuelsServlet?c=tryStartDuel">Обновить</a></div>	
</t:wrapper>