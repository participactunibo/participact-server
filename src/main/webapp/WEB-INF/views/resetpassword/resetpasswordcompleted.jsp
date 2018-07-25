<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/templates/stdhead.jsp">
	<jsp:param value="reset.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="login" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>UniBo ParticipAct</h1>
		</div>
	</div>
	<div class="container">
		<section id="reset">
			<div class="span12">
				<p>La tua nuova password &egrave; stata reimpostata. Da questo
					momento puoi usarla per accedere al sito. Ricordati di inserirla
					anche nell'applicazione Android ParticipAct.</p>
			</div>
		</section>
	</div>
</body>
</html>