<%@ page language="java" contentType="text/html; charset=UTF-8"  
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:wrapper>
	<h1>Авторизация</h1>
	<span class="tC red">${errorInfo}</span>
	<form action="AuthServlet?" method="post">	
		<input type="hidden" name="c" value="login">
		<div>Логин</div>		
		<input type="text" name="usernick"size="20px" >
		<div>Пароль</div>
		<input type="password" name="password"size="20px">
		<br/>
		<input class="btn" type="submit" value="Войти">						
	</form>		
</t:wrapper>