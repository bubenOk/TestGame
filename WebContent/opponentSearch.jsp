<%@ page language="java" contentType="text/html; charset=UTF-8"  
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:wrapper>
	<h1>Поиск оппонента</h1>
	<div>Обновляйте страницу, пока оппонент не найдется</div>		
	<div><a class="btn" href="DuelsServlet?c=tryStartDuel">Обновить</a></div>		
	<div><a class="btn" href="DuelsServlet?c=cancelDuel">Отмена</a></div>		
</t:wrapper>