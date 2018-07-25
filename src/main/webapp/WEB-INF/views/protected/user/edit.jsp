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
	<jsp:param value="protected.user.edit.title" name="title" />
</jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp">
		<jsp:param value="protected.user" name="section" />
	</jsp:include>
	<div class="jumbotron subhead">
		<div class="container">
			<h1>User account</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/userSidenav.jsp">
					<jsp:param value="protected.user.edit" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Edit ParticipAct user</h1>
				</div>
				<c:if test="${not empty formerror}">
					<div class="alert alert-error fade in" id="loginerror">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Errore nella creazione dell'account. Ricontrollare il form.
					</div>
				</c:if>
				<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
				<spring:url value="/protected/user/edit/${userId}" var="editAdminUserForm"></spring:url>
				</sec:authorize>
				<form:form method="POST" modelAttribute="editAdminUserForm" action="${editAdminUserForm}" enctype="multipart/form-data">
					<fieldset>
						<div class="span9">
							<div class="span6">
								<h2>Contatti</h2>
								<t:input path="name" label="Nome*" placeholder="Mario" inputprepend="fa fa-user" />
								<t:input path="surname" label="Cognome*" placeholder="Rossi" inputprepend="fa fa-user" />
								<t:input path="birthdate" label="Data di nascita (AAAA-MM-GG)*" placeholder="AAAA-MM-GG"
									inputprepend="fa fa-calendar" />
								<t:radiobuttons label="Sesso*" items="${genders}" path="gender" />

								<h3>Informazioni di contatto</h3>
								<t:input path="officialEmail" label="E-mail ufficiale di ateneo*" placeholder="nome.cognome@studio.unibo.it"
									stringinputprepend="@" />
								<t:input path="contactPhoneNumber" label="Numero di telefono principale*" placeholder="+39 051 12345678"
									inputprepend="fa fa-phone" />
								<t:input path="homePhoneNumber" label="Numero di telefono fisso" placeholder="+39 051 12345678"
									inputprepend="fa fa-phone" />

								<t:select label="Documento d'Identit&agrave;*" path="documentIdType" items="${documentTypes}"></t:select>
								<t:input path="documentId" label="Numero di documento*" placeholder="AJ000000" inputprepend="fa fa-tag" />
								
								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
								<h2>Informazioni per il progetto</h2>
								<t:input path="projectEmail" label="E-mail per il progetto*" placeholder="nome.cognome@gmail.com"
									inputprepend="fa fa-google-plus" />
								<t:checkbox label="User is active" path="isActive"></t:checkbox>
								<t:input path="facebookEmail" label="E-mail account Facebook" placeholder="nome.cognome@gmail.com"
									inputprepend="fa fa-facebook" />
								<t:input path="projectPhoneNumber" label="Numero di telefono per il progetto" placeholder="+39 349 12345678"
									inputprepend="fa fa-phone" />
								<t:checkbox label="L'utente desidera ricevere il telefono" path="wantsPhone" />
								<t:checkbox label="L'utente ha il telefono" path="hasPhone" />
								<t:input path="imei" label="Phone IMEI" />
								<t:checkbox label="L'utente ha la SIM" path="hasSIM" />
								<t:checkbox label="L'utente &egrave; registrato su TIM MyCompany" path="isMyCompanyRegistered" />
								<t:input path="newIccid" label="ICCID of the NEW SIM" />
								<t:input path="receivedPhoneOn"
									label="Data di RITIRO del telefono. Se viene lasciata vuota e viene MESSO un segno di spunta a &quot;L'utente ha il telefono&quot;, questo campo verr&agrave; riempito automaticamente con la data odierna."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:input path="returnedPhoneOn"
									label="Data di RESTITUZIONE del telefono. Se viene lasciata vuota e viene TOLTO un segno di spunta a &quot;L'utente ha il telefono&quot;, questo campo verr&agrave; riempito automaticamente con la data odierna."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:input path="activatedSIMOn"
									label="Data di ATTIVAZIONE della SIM."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:input path="receivedSIMOn"
									label="Data di RITIRO della SIM. Se viene lasciata vuota e viene MESSO un segno di spunta a &quot;L'utente ha la SIM&quot;, questo campo verr&agrave; riempito automaticamente con la data odierna."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:input path="returnedSIMOn"
									label="Data di RESTITUZIONE della SIM. Se viene lasciata vuota e viene TOLTO un segno di spunta a &quot;L'utente ha la SIM&quot;, questo campo verr&agrave; riempito automaticamente con la data odierna."
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:password path="password" label="Password" inputprepend="fa fa-key" />
								<t:password path="confirmPassword" label="Conferma password" inputprepend="fa fa-key" />
								<t:select label="SIM" items="${simStatuses}" path="simStatus" />
								<t:input path="cf" label="Codice fiscale" placeholder="MRARSS92C04F824P" inputprepend="fa fa-credit-card" />
								<t:input path="originalPhoneOperator" label="Operatore telefonico di partenza" inputprepend="fa fa-building" />
								<t:input path="iccid" label="ICCID della VECCHIA SIM (riportato sul retro della SIM stessa)"
									inputprepend="fa fa-phone" />
								<p>Caricare una scansione del documento di identit&agrave;.</p>
								<form:input path="idScan" type="file" />
								<br /> <br />
								<p>Caricare una scansione dell'ultima ricevuta di pagamento (per utenti con abbonamento).</p>
								<form:input path="lastInvoiceScan" type="file" />
								<br /> <br />
								<p>Scansione liberatoria privacy</p>
								<form:input path="privacyScan" type="file" />
								<br /> <br />
								<p>Presa consegna telefono</p>
								<form:input path="presaConsegnaPhoneScan" type="file" />
								<br /> <br />
								<p>Presa consegna SIM</p>
								<form:input path="presaConsegnaSIMScan" type="file" />
								<br /> <br />
								</sec:authorize>
								<h3>Indirizzo di Residenza</h3>
								<t:input path="currentAddress" label="Indirizzo*" placeholder="v.le Risorgimento 2" inputprepend="fa fa-envelope" />
								<t:input path="currentZipCode" label="CAP*" placeholder="40135" inputprepend="fa fa-map-marker" />
								<t:input path="currentCity" label="Citt&agrave;*" placeholder="Bologna" inputprepend="fa fa-map-marker" />
								<t:input path="currentProvince" label="Provincia*" placeholder="BO" inputprepend="fa fa-map-marker" />

								<h3>Indirizzo di Domicilio</h3>
								<t:input path="domicileAddress" label="Indirizzo" placeholder="v.le Risorgimento 2" inputprepend="fa fa-envelope" />
								<t:input path="domicileZipCode" label="CAP" placeholder="40135" inputprepend="fa fa-map-marker" />
								<t:input path="domicileCity" label="Citt&agrave;" placeholder="Bologna" inputprepend="fa fa-map-marker" />
								<t:input path="domicileProvince" label="Provincia" placeholder="BO" inputprepend="fa fa-map-marker" />

								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
								<h2>Situazione Universitaria</h2>
								<t:select label="Sede*" path="uniCity" items="${uniCities}"></t:select>
								<t:select label="Scuola*" path="uniSchool" items="${uniSchools}"></t:select>
								<t:input path="uniDepartment" label="Dipartimento*" placeholder="DISI" inputprepend="fa fa-book" />
								<t:input path="uniDegree" label="Corso di studi" placeholder="Ingegneria Informatica" inputprepend="fa fa-book" />
								<t:select label="Corso di laurea" path="uniCourse" items="${uniCourses}"></t:select>
								<t:checkbox path="uniIsSupplementaryYear" label="Fuori corso" />
								<t:input path="uniYear" label="Anno di corso" placeholder="2" inputprepend="fa fa-book" />
								</sec:authorize>
								<h2>Note aggiuntive</h2>
								<t:textarea path="notes" cssClass="span6" label="Note aggiuntive su questo utente, testo libero."></t:textarea>
							</div>
						</div>
						<div class="span2">
							<button class="btn btn-primary" type="submit">Save</button>
							<a class="btn btn-danger" type="submit" href='<c:url value="/protected/user"/>'>Cancel</a>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>