<%@tag language="java"
	description="Extended input tag to allow for sophisticated errors"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="label" required="false" type="java.lang.String"%>
<%@attribute name="inputprepend" required="false"
	type="java.lang.String"%>
<%@attribute name="stringinputprepend" required="false"
	type="java.lang.String"%>
<%@attribute name="placeholder" required="false" type="java.lang.String"%>
<%@attribute name="required" required="false" type="java.lang.Boolean"%>
<%@attribute name="showerror" required="false" type="java.lang.Boolean"%>
<spring:bind path="${path}">
	<div class="control-group ${status.error ? 'error' : '' }">
		<c:if test="${not empty label}">
			<label class="control-label" for="${path}">${label}</label>
		</c:if>
		<c:choose>
			<c:when test="${not empty inputprepend}">
				<div class="input-prepend">
					<span class="add-on"><i class="${inputprepend}"></i></span>
					<c:choose>
						<c:when test="${empty cssClass}">
							<form:input path="${path}"
								placeholder="${empty placeholder ? ' ' : placeholder}" />
						</c:when>
						<c:otherwise>
							<form:input path="${path}"
								placeholder="${empty placeholder ? ' ' : placeholder}"
								cssClass="${cssClass}" />
						</c:otherwise>
					</c:choose>
				</div>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${not empty stringinputprepend}">
						<div class="input-prepend">
							<span class="add-on">${stringinputprepend}</span>
							<c:choose>
								<c:when test="${empty cssClass}">
									<form:input path="${path}"
										placeholder="${empty placeholder ? ' ' : placeholder}" />
								</c:when>
								<c:otherwise>
									<form:input path="${path}"
										placeholder="${empty placeholder ? ' ' : placeholder}"
										cssClass="${cssClass}" />
								</c:otherwise>
							</c:choose>
						</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${empty cssClass}">
								<form:input path="${path}"
									placeholder="${empty placeholder ? ' ' : placeholder}" />
							</c:when>
							<c:otherwise>
								<form:input path="${path}"
									placeholder="${empty placeholder ? ' ' : placeholder}"
									cssClass="${cssClass}" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<c:if test="${empty showerror or showerror == true}">
			<c:if test="${status.error}">
				<span class="help-inline"><spring:message
						code="${status.errorMessage}" /></span>
			</c:if>
		</c:if>
	</div>
</spring:bind>