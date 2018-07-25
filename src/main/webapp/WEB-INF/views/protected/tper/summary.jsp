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
	<jsp:param value="protected.dashboard.title" name="title" />
</jsp:include>

<script>
var myApp;
myApp = myApp || (function () {
    var pleaseWaitDiv = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header"><h1>Processing...</h1></div><div class="modal-body"><div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div></div></div>');
    return {
        showPleaseWait: function() {
            pleaseWaitDiv.modal();
        },
        hidePleaseWait: function () {
            pleaseWaitDiv.modal('hide');
        },

    };
})();
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.dashboard" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>Update TPer data</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/dashboardSidenav.jsp">
					<jsp:param value="protected.dashboard.tper" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<h1>Update Data</h1>			
				<form:form action="/protected/tper" method="POST">
					<fieldset>
						<p>Updating TPer date may take a while. Remember that data are
							automatically updated every 6 months.</p>
						<button class="btn btn-primary" type="submit" onclick="myApp.showPleaseWait()">Submit</button>
						<a class="btn btn-danger" type="submit"	href='<c:url value="/protected/dashboard"/>'>Cancel</a>
					</fieldset>
				</form:form>
			</div>
			<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static"
				data-keyboard="false">
				<div class="modal-header">
					<h1>Processing...</h1>
				</div>
				<div class="modal-body">
					<div class="progress progress-striped active">
						<div class="bar" style="width: 100%;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>