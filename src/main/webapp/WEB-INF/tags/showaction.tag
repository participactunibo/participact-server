<%@tag language="java" description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@attribute name="action" required="true" type="it.unibo.paserver.domain.Action"%>
<%@attribute name="compact" required="false" type="java.lang.Boolean"%>
<c:choose>
	<c:when test="${pa:actionType(action) == 'ActionActivityDetection'}">
		Activity Detection - Duration threshold: <c:out value="${action.duration_threshold}"/>m
	</c:when>
	<c:when test="${pa:actionType(action) == 'ActionSensing'}">
		<c:out value="${pa:actionToString(action)}" />
	</c:when>
	<c:when test="${pa:actionType(action) == 'ActionPhoto'}">
		<c:out value="${pa:actionToString(action)}" />
	</c:when>
	
	<c:when test="${pa:actionType(action) == 'ActionGeofence'}">
		Geofence: 
			DESCRIPTION: <c:out value="${action.description}" />  ---  
		POINTS: <c:out value="${action.interestPoints}" />   
		
	</c:when>
	
	<c:when test="${pa:actionType(action) == 'ActionQuestionaire'}">
		Questionnaire &quot;<c:out value="${action.title}" />&quot; &mdash; Description: &quot;<c:out
			value="${action.description}" />&quot;
			<c:if test="${compact eq true}">
			<ol>
				<c:forEach items="${action.questions}" var="question">
					<c:choose>
						<c:when test="${not question.isClosedAnswers}">
							<li>Open question: <c:out value="${question.question}" /></li>
						</c:when>
						<c:otherwise>
							<li>Closed question (<c:choose>
									<c:when test="${question.isMultipleAnswers}">multiple answers</c:when>
									<c:otherwise>single answer</c:otherwise>
								</c:choose>): &quot;<c:out value="${question.question}" />&quot;
								<ol>
									<c:forEach items="${question.closed_answers}" var="answer">
										<li><c:out value="${answer.answerDescription}" /></li>
									</c:forEach>
								</ol></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ol>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:out value="${pa:actionToString(action)}" />
	</c:otherwise>
</c:choose>
