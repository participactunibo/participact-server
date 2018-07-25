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
	<jsp:param value="protected.task.add.title" name="title" />
</jsp:include>
<script>
	function isNumber(n) {
	  return !isNaN(parseInt(n)) && isFinite(n);
	}
	
	function updateDurationTime() {
		if (isNumber($('input[id=sensingDuration]').val())) {
			currdur = parseInt($('input[id=sensingDuration]').val())
			days = Math.floor(currdur / (24 * 60))
			hours = Math.floor((currdur - (days * 24 * 60)) / 60)
			minutes = currdur - (days * 24 * 60) - (hours * 60)
			res = days + "d " + hours + "h " + minutes + "m"
			$('#durtime').text(res);
		} else {
			$('#durtime').text("Insert a valid duration in minutes");
		}
	}
	
	function constToMinutes(type) {
		switch(type) {
		case "minute":
			return 1;
		case "hour":
			return 60;
		case "day":
			return 24 * 60;
		case "week":
			return 7 * 24 * 60;
		default:
			return 0;
		}
	}

	function inputAddTimeDuration(input, quantity, type) {
		curr = parseInt($(input).val()) + quantity * constToMinutes(type)
		$(input).val(curr);
		updateDurationTime();
	}

	$(document).ready(function() {
		$('#sensingDuration').on('keyup keydown keypress', function(e) {
			updateDurationTime();
		});
		
		$('#duration_add_week').click(function() {
			inputAddTimeDuration('#sensingDuration', 1, "week")
		});
		$('#duration_add_day').click(function() {
			inputAddTimeDuration('#sensingDuration', 1, "day")
		});
		$('#duration_add_hour').click(function() {
			inputAddTimeDuration('#sensingDuration', 1, "hour")
		});
		$('#duration_add_minute').click(function() {
			inputAddTimeDuration('#sensingDuration', 1, "minute")
		});
		$('#duration_sub_week').click(function() {
			inputAddTimeDuration('#sensingDuration', -1, "week")
		});
		$('#duration_sub_day').click(function() {
			inputAddTimeDuration('#sensingDuration', -1, "day")
		});
		$('#duration_sub_hour').click(function() {
			inputAddTimeDuration('#sensingDuration', -1, "hour")
		});
		$('#duration_sub_minute').click(function() {
			inputAddTimeDuration('#sensingDuration', -1, "minute")
		});
	})
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.task" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>New Task</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/taskSidenav.jsp">
					<jsp:param value="protected.task.add" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Select a sensing duration for task &quot;<c:out value="${task.name}" />&quot;</h1>
				</div>
				<form:form modelAttribute="task" action="${flowExecutionUrl}">
					<t:input path="sensingDuration" inputprepend="fa fa-clock-o" label="Duration of the sensing activity in minutes. This time is shared by all passive sensing actions." />
					<label id="durtime"></label>
					<p>
						<a class="btn" id="duration_add_week">+1w</a><a class="btn"
							id="duration_add_day">+1d</a><a class="btn" id="duration_add_hour">+1h</a><a class="btn" id="duration_add_minute">+1m</a>
					</p>
					<p>
						<a class="btn" id="duration_sub_week">-1w</a><a class="btn"
							id="duration_sub_day">-1d</a><a class="btn" id="duration_sub_hour">-1h</a><a class="btn" id="duration_sub_minute">-1m</a>
					</p>
					<button class="btn btn-danger" type="submit" name="_eventId_cancel">Cancel</button>
					<button class="btn btn-primary" type="submit" name="_eventId_next">Next</button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>