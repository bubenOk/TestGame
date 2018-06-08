<%@ page language="java" contentType="text/html; charset=UTF-8"  
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:wrapper>
	<h1>Меню</h1>
	<div><a class="btn" href="DuelsServlet?c=showRating">Дуэли</a></div>		
	<div><a class="btn" href="AuthServlet?c=logout">Выход</a></div>		
</t:wrapper>