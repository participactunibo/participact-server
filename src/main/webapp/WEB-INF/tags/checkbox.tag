<%@tag language="java"
	description="Extended input tag to allow for sophisticated errors"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="label" required="true" type="java.lang.String"%>
<%@attribute name="showerror" required="false" type="java.lang.Boolean"%>
<%@attribute name="checked" required="false" type="java.lang.Boolean"%>
<spring:bind path="${path}">
	<div class="control-group ${status.error ? 'error' : '' }">
		<label class="checkbox" for="${path}"><form:checkbox path="${path}" />${label}</label>
		<c:if test="${empty showerror or showerror == true}">
			<c:if test="${status.error}">
				<span class="help-inline"><spring:message
						code="${status.errorMessage}" /></span>
			</c:if>
		</c:if>
	</div>
</spring:bind>