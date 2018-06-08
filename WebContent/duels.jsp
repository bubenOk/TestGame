<%@ page language="java" contentType="text/html; charset=UTF-8"  
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:wrapper>
	<h1>Дуэли</h1>
	<div>Ваш рейтинг: ${dto.rating}</div>		
	<div><a class="btn" href="DuelsServlet?c=prepareForDuel">Начать дуэль</a></div>		
	<div><a class="btn" href="menu.jsp">К меню</a></div>		
</t:wrapper>