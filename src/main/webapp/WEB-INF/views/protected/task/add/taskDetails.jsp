<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

	function formatDate(d) {
		dformat = [ d.getFullYear(), ("0" + (d.getMonth() + 1)).slice(-2),
				("0" + d.getDate()).slice(-2) ].join('-')
				+ ' '
				+ [ ("0" + d.getHours()).slice(-2),
						("0" + d.getMinutes()).slice(-2) ].join(':');
		return dformat;
	}

	function addTime(date, quantity, factor) {
		result = new Date(date);
		switch (factor) {
		case "minute":
			result.setMinutes(date.getMinutes() + quantity);
			break;
		case "hour":
			result.setHours(date.getHours() + quantity);
			break;
		case "day":
			result.setDate(date.getDate() + quantity);
			break;
		case "month":
			result.setMonth(date.getMonth() + quantity);
			break;
		case "year":
			result.setFullYear(date.getFullYear() + quantity);
			break;
		}
		return result;
	}

	function parseDate(input) {
		try {
			var dateTimeParts = input.split(' ');
			var dateParts = dateTimeParts[0].split('-')
			var timeParts = dateTimeParts[1].split(':')
			return new Date(dateParts[0], dateParts[1] - 1, dateParts[2],
					timeParts[0], timeParts[1]); // months are 0-based
		} catch (err) {
			return new Date;
		}
	}

	function inputAddTime(input, quantity, type) {
		curr = parseDate($(input).val())
		curr = addTime(curr, quantity, type);
		now = formatDate(curr);
		$(input).val(now);
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
	
	function updateDurationTime() {
		if (isNumber($('input[id=duration]').val())) {
			currdur = parseInt($('input[id=duration]').val())
			days = Math.floor(currdur / (24 * 60))
			hours = Math.floor((currdur - (days * 24 * 60)) / 60)
			minutes = currdur - (days * 24 * 60) - (hours * 60)
			res = days + "d " + hours + "h " + minutes + "m"
			$('#durtime').text(res);
		} else {
			$('#durtime').text("Insert a valid duration in minutes");
		}
	}

	$(document).ready(function() {
		$('#duration').on('keyup keydown keypress', function(e) {
			updateDurationTime();
		});

		$('#start_now').click(function() {
			now = formatDate(new Date);
			$('#start').val(now);
		});
		$('#start_add_year').click(function() {
			inputAddTime('#start', 1, "year")
		});
		$('#start_add_month').click(function() {
			inputAddTime('#start', 1, "month")
		});
		$('#start_add_day').click(function() {
			inputAddTime('#start', 1, "day")
		});
		$('#start_add_hour').click(function() {
			inputAddTime('#start', 1, "hour")
		});
		$('#start_add_minute').click(function() {
			inputAddTime('#start', 1, "minute")
		});
		$('#start_sub_year').click(function() {
			inputAddTime('#start', -1, "year")
		});
		$('#start_sub_month').click(function() {
			inputAddTime('#start', -1, "month")
		});
		$('#start_sub_day').click(function() {
			inputAddTime('#start', -1, "day")
		});
		$('#start_sub_hour').click(function() {
			inputAddTime('#start', -1, "hour")
		});
		$('#start_sub_minute').click(function() {
			inputAddTime('#start', -1, "minute")
		});
		
		$('#deadline_copy_start').click(function() {
			curr = parseDate($('#start').val())
			$('#deadline').val(formatDate(curr));
		});
		$('#deadline_add_year').click(function() {
			inputAddTime('#deadline', 1, "year")
		});
		$('#deadline_add_month').click(function() {
			inputAddTime('#deadline', 1, "month")
		});
		$('#deadline_add_day').click(function() {
			inputAddTime('#deadline', 1, "day")
		});
		$('#deadline_add_hour').click(function() {
			inputAddTime('#deadline', 1, "hour")
		});
		$('#deadline_add_minute').click(function() {
			inputAddTime('#deadline', 1, "minute")
		});
		$('#deadline_sub_year').click(function() {
			inputAddTime('#deadline', -1, "year")
		});
		$('#deadline_sub_month').click(function() {
			inputAddTime('#deadline', -1, "month")
		});
		$('#deadline_sub_day').click(function() {
			inputAddTime('#deadline', -1, "day")
		});
		$('#deadline_sub_hour').click(function() {
			inputAddTime('#deadline', -1, "hour")
		});
		$('#deadline_sub_minute').click(function() {
			inputAddTime('#deadline', -1, "minute")
		});
		
		$('#duration_add_week').click(function() {
			inputAddTimeDuration('#duration', 1, "week")
		});
		$('#duration_add_day').click(function() {
			inputAddTimeDuration('#duration', 1, "day")
		});
		$('#duration_add_hour').click(function() {
			inputAddTimeDuration('#duration', 1, "hour")
		});
		$('#duration_add_minute').click(function() {
			inputAddTimeDuration('#duration', 1, "minute")
		});
		$('#duration_sub_week').click(function() {
			inputAddTimeDuration('#duration', -1, "week")
		});
		$('#duration_sub_day').click(function() {
			inputAddTimeDuration('#duration', -1, "day")
		});
		$('#duration_sub_hour').click(function() {
			inputAddTimeDuration('#duration', -1, "hour")
		});
		$('#duration_sub_minute').click(function() {
			inputAddTimeDuration('#duration', -1, "minute")
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
					<h1>Create a new Sensing task</h1>
				</div>
				<form:form modelAttribute="task" action="${flowExecutionUrl}">
					<t:input path="name" label="Name" inputprepend="fa fa-tag"/>
					<t:textarea label="Task description" path="description" cssClass="input-block-level" />
					<t:checkbox label="This task can be refused by users" path="canBeRefused" />
					<t:input path="start" inputprepend="fa fa-calendar"
						label="Start of the task: date and time this task will be available to the public. Input task start in the <strong>yyyy-MM-dd&nbsp;HH:mm</strong> format. All dates and times are interpreted on the timezone of this server (Italy)" />
					<p>
						<a class="btn" id="start_now">Now</a>
					</p>
					<p>
						<a class="btn" id="start_add_year">+1y</a><a class="btn" id="start_add_month">+1M</a><a class="btn"
							id="start_add_day">+1d</a><a class="btn" id="start_add_hour">+1h</a><a class="btn" id="start_add_minute">+1m</a>
					</p>
					<p>
						<a class="btn" id="start_sub_year">-1y</a><a class="btn" id="start_sub_month">-1M</a><a class="btn"
							id="start_sub_day">-1d</a><a class="btn" id="start_sub_hour">-1h</a><a class="btn" id="start_sub_minute">-1m</a>
					</p>
					<t:input path="deadline" inputprepend="fa fa-calendar"
						label="Deadline of this task. After the deadline the task will not be available anymore. Input the task in the <strong>yyyy-MM-dd&nbsp;HH:mm</strong> format." />
					<p>
						<a class="btn" id="deadline_copy_start">Copy start time</a>
					</p>
					<p>
						<a class="btn" id="deadline_add_year">+1y</a><a class="btn" id="deadline_add_month">+1M</a><a class="btn"
							id="deadline_add_day">+1d</a><a class="btn" id="deadline_add_hour">+1h</a><a class="btn" id="deadline_add_minute">+1m</a>
					</p>
					<p>
						<a class="btn" id="deadline_sub_year">-1y</a><a class="btn" id="deadline_sub_month">-1M</a><a class="btn"
							id="deadline_sub_day">-1d</a><a class="btn" id="deadline_sub_hour">-1h</a><a class="btn" id="deadline_sub_minute">-1m</a>
					</p>
					<t:input path="duration" inputprepend="fa fa-clock-o"
						label="Duration of the task in minutes. This is the time that the user has to successfully complete the task" />
					<p>
					<p><span id="durtime"></span></p>
					<p>
						<a class="btn" id="duration_add_week">+1w</a><a class="btn"
							id="duration_add_day">+1d</a><a class="btn" id="duration_add_hour">+1h</a><a class="btn" id="duration_add_minute">+1m</a>
					</p>
					<p>
						<a class="btn" id="duration_sub_week">-1w</a><a class="btn"
							id="duration_sub_day">-1d</a><a class="btn" id="duration_sub_hour">-1h</a><a class="btn" id="duration_sub_minute">-1m</a>
					</p>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
					</sec:authorize>
					<button class="btn btn-danger" type="submit" name="_eventId_cancel">Cancel</button>
					<button class="btn btn-primary" type="submit" name="_eventId_next">Next</button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>