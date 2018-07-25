<%@tag language="java" description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="pa" uri="/WEB-INF/tlds/paserver-funcs.tld"%>
<%@attribute name="data" required="true" type="java.lang.Object"%>
<%@attribute name="questionnaire" required="true" type="it.unibo.paserver.domain.ActionQuestionaire"%>
<%@attribute name="questionnairedescription" required="true" type="java.lang.String" %>
<h3>
	Questionnaire
	<c:out value="${questionnaire.title}"></c:out>
</h3>
<p>
	<em>Questionnaire description:</em>
</p>
<p>
<blockquote><c:out value="${questionnairedescription}" /></blockquote>
</p>
<p>Answers</p>
<ol>
	<c:forEach items="${data}" var="entry">
		<li>${entry.question}<c:choose>
				<c:when test="${entry.isClosed}">
					<ul>
						<c:forEach items="${entry.answers}" var="answer">
							<li>${answer.answer}<c:choose>
									<c:when test="${answer.result == null}">
										<i class="fa fa-check-empty"></i>
									</c:when>
									<c:when test="${answer.result eq true}">
										<i class="fa fa-check"></i>
									</c:when>
									<c:when test="${answer.result eq false}">
										<i class="fa fa-times"></i>
									</c:when>
									<c:otherwise>
										<i class="fa fa-check-empty"></i>
									</c:otherwise>
								</c:choose></li>
						</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
					<br />
					<blockquote>
						<c:out value="${entry.openanswer}" />
					</blockquote>
				</c:otherwise>
			</c:choose></li>
	</c:forEach>
</ol>