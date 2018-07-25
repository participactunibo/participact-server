<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	$(document).ready(function() {

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
			$('#end').val(formatDate(curr));
		});
		$('#deadline_add_year').click(function() {
			inputAddTime('#end', 1, "year")
		});
		$('#deadline_add_month').click(function() {
			inputAddTime('#end', 1, "month")
		});
		$('#deadline_add_day').click(function() {
			inputAddTime('#end', 1, "day")
		});
		$('#deadline_add_hour').click(function() {
			inputAddTime('#end', 1, "hour")
		});
		$('#deadline_add_minute').click(function() {
			inputAddTime('#end', 1, "minute")
		});
		$('#deadline_sub_year').click(function() {
			inputAddTime('#end', -1, "year")
		});
		$('#deadline_sub_month').click(function() {
			inputAddTime('#end', -1, "month")
		});
		$('#deadline_sub_day').click(function() {
			inputAddTime('#end', -1, "day")
		});
		$('#deadline_sub_hour').click(function() {
			inputAddTime('#end', -1, "hour")
		});
		$('#deadline_sub_minute').click(function() {
			inputAddTime('#end', -1, "minute")
		});
		
	})
</script>
<c:set var="subSectionKey">${param.subsection}</c:set>
<ul class="nav nav-list bs-docs-sidenav affix-top">
	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
	
		<spring:url value="/protected/points" var="datesForm"></spring:url>
		<form:form method="POST" modelAttribute="datesForm" action="${datesForm}" enctype="multipart/form-data">
					<fieldset>
						<t:input path="start" inputprepend="fa fa-calendar"
						label="Input start in the <strong>yyyy-MM-dd&nbsp;HH:mm</strong> format." />
					<p>
						<a class="btn" id="start_now">Now</a>
					</p>
					<p>
						<a class="btn" id="start_add_year">+1y</a>
						<a class="btn" id="start_add_month">+1M</a>
						<a class="btn" id="start_add_day">+1d</a>
						<a class="btn" id="start_add_hour">+1h</a>
						<a class="btn" id="start_add_minute">+1m</a>
					</p>
					<p>
						<a class="btn" id="start_sub_year">-1y</a>
						<a class="btn" id="start_sub_month">-1M</a>
						<a class="btn" id="start_sub_day">-1d</a>
						<a class="btn" id="start_sub_hour">-1h</a>
						<a class="btn" id="start_sub_minute">-1m</a>
					</p>
					<t:input path="end" inputprepend="fa fa-calendar"
						label="Input the end in the <strong>yyyy-MM-dd&nbsp;HH:mm</strong> format." />
					<p>
						<a class="btn" id="deadline_copy_start">Copy start time</a>
					</p>
					<p>
						<a class="btn" id="deadline_add_year">+1y</a>
						<a class="btn" id="deadline_add_month">+1M</a>
						<a class="btn" id="deadline_add_day">+1d</a>
						<a class="btn" id="deadline_add_hour">+1h</a>
						<a class="btn" id="deadline_add_minute">+1m</a>
					</p>
					<p>
						<a class="btn" id="deadline_sub_year">-1y</a>
						<a class="btn" id="deadline_sub_month">-1M</a>
						<a class="btn" id="deadline_sub_day">-1d</a>
						<a class="btn" id="deadline_sub_hour">-1h</a>
						<a class="btn" id="deadline_sub_minute">-1m</a>
					</p>
					
					<button class="btn btn-primary" type="submit">Search</button>
					</fieldset>
				</form:form>
	</sec:authorize>
</ul>