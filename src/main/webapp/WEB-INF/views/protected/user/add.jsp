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
	<jsp:param value="protected.user.add.title" name="title" />
</jsp:include>
<script src="<c:url value="/resources/js/bootstrap-datepicker.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/datepicker.css"/>">
<script>
	function updateInputs() {
		var disableDomicileInputs = $('#isDomicileEqualToCurrentAddr1').is(
				':checked');
		var disableUniYearInput = $('#uniIsSupplementaryYear1').is(':checked');
		var disableSIMdata = $('#simStatus').val() != "PORTABILITY";
		if (disableDomicileInputs) {
			$('#domicileAddress').prop('disabled', true).val("");
			$('#domicileCity').prop('disabled', true).val("");
			$('#domicileZipCode').prop('disabled', true).val("");
			$('#domicileProvince').prop('disabled', true).val("");
			$('#domicile_address').slideUp();
		}

		if (disableUniYearInput) {
			$('#uniYear').prop('disabled', true).val("");
		}

		if (disableSIMdata) {
			$('#cf').prop('disabled', true).val("");
			$('#projectPhoneNumber').prop('disabled', true).val("");
			$('#originalPhoneOperator').prop('disabled', true).val("");
			$('#iccid').prop('disabled', true).val("");
			$('#siminfo').slideUp();
		}
	}
	$(document).ready(function() {
// 		$('#birthdate').datepicker({
// 			format : 'yyyy-mm-dd',
// 			viewMode : 'years'
// 		});

		$('#isDomicileEqualToCurrentAddr1').change(function() {
			var disableInput = $(this).is(':checked');
			$('#domicileAddress').prop('disabled', disableInput).val("");
			$('#domicileCity').prop('disabled', disableInput).val("");
			$('#domicileZipCode').prop('disabled', disableInput).val("");
			$('#domicileProvince').prop('disabled', disableInput).val("");
			if (disableInput == true) {
				$('#domicile_address').slideUp();
			} else {
				$('#domicile_address').slideDown();
			}
		});

		$('#uniIsSupplementaryYear1').change(function() {
			var disableInput = $(this).is(':checked');
			$('#uniYear').prop('disabled', disableInput).val("");
		});

		$('#officialEmail').on('keyup keydown keypress', function(e) {
			$('#proj_email').text($('input[id=officialEmail]').val());
		});

		$('#simStatus').change(function() {
			var disableSIMdata = $('#simStatus').val() != "PORTABILITY";
			$('#cf').prop('disabled', disableSIMdata).val("");
			$('#projectPhoneNumber').prop('disabled', disableSIMdata).val("");
			$('#originalPhoneOperator').prop('disabled', disableSIMdata).val("");
			$('#iccid').prop('disabled', disableSIMdata).val("");
			if (disableSIMdata) {
				$('#siminfo').slideUp();
			} else {
				$('#siminfo').slideDown();
			}
		});
		updateInputs();
	});
</script>
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
					<jsp:param value="protected.user.add" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h1>Aggiungi account utente ParticipAct</h1>
				</div>
				<c:if test="${not empty formerror}">
					<div class="alert alert-error fade in" id="loginerror">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						Errore nella creazione dell'account. Ricontrollare il form.
					</div>
				</c:if>
				<spring:url value="/protected/user/addAccount" var="addUser"></spring:url>
				<form:form method="POST" modelAttribute="addAdminUserForm"
					action="${addUser}" enctype="multipart/form-data">
					<fieldset>
						<div class="span9">
							<div class="span6">
								<h2>Contatti</h2>
								<t:input path="name" label="Nome*" placeholder="Mario"
									inputprepend="fa fa-user" />
								<t:input path="surname" label="Cognome*" placeholder="Rossi"
									inputprepend="fa fa-user" />
								<t:input path="birthdate" label="Data di nascita (AAAA-MM-GG)*"
									placeholder="AAAA-MM-GG" inputprepend="fa fa-calendar" />
								<t:radiobuttons label="Sesso*" items="${genders}" path="gender" />

								<h3>Indirizzo di Residenza</h3>
								<t:input path="currentAddress" label="Indirizzo*"
									placeholder="v.le Risorgimento 2" inputprepend="fa fa-envelope" />
								<t:input path="currentZipCode" label="CAP*" placeholder="40135"
									inputprepend="fa fa-map-marker" />
								<t:input path="currentCity" label="Citt&agrave;*"
									placeholder="Bologna" inputprepend="fa fa-map-marker" />
								<t:input path="currentProvince" label="Provincia*"
									placeholder="BO" inputprepend="fa fa-map-marker" />

								<h3>Indirizzo di Domicilio</h3>
								<t:checkbox label="Il domicilio &egrave; uguale alla residenza."
									path="isDomicileEqualToCurrentAddr"></t:checkbox>
								<div id="domicile_address">
									<t:input path="domicileAddress" label="Indirizzo"
										placeholder="v.le Risorgimento 2" inputprepend="fa fa-envelope" />
									<t:input path="domicileZipCode" label="CAP" placeholder="40135"
										inputprepend="fa fa-map-marker" />
									<t:input path="domicileCity" label="Citt&agrave;"
										placeholder="Bologna" inputprepend="fa fa-map-marker" />
									<t:input path="domicileProvince" label="Provincia"
										placeholder="BO" inputprepend="fa fa-map-marker" />
								</div>

								<h3>Informazioni di contatto</h3>
								<span class="label label-warning">Attenzione</span>
								<p>L'e-mail ufficiale di ateneo verr&agrave; utilizzata per
									tutte le comunicazioni ufficiali del progetto. Inoltre,
									sar&agrave; l'identificativo utente da inserire sull'app
									smartphone e sul sito web di ParticipAct.</p>
								<t:input path="officialEmail"
									label="E-mail ufficiale di ateneo*"
									placeholder="nome.cognome@studio.unibo.it"
									stringinputprepend="@" />
								<t:input path="contactPhoneNumber"
									label="Numero di telefono principale*"
									placeholder="+39 051 12345678" inputprepend="fa fa-phone" />
								<t:input path="homePhoneNumber" label="Numero di telefono fisso"
									placeholder="+39 051 12345678" inputprepend="fa fa-phone" />

								<t:select label="Documento d'Identit&agrave;*"
									path="documentIdType" items="${documentTypes}"></t:select>
								<t:input path="documentId" label="Numero di documento*"
									placeholder="AJ000000" inputprepend="fa fa-tag" />

								<h2>Situazione Universitaria</h2>
								<t:select label="Sede*" path="uniCity" items="${uniCities}"></t:select>
								<t:select label="Scuola*" path="uniSchool" items="${uniSchools}"></t:select>
								<t:input path="uniDepartment" label="Dipartimento*"
									placeholder="DISI" inputprepend="fa fa-book" />
								<t:input path="uniDegree" label="Corso di studi"
									placeholder="Ingegneria Informatica" inputprepend="fa fa-book" />
								<t:select label="Corso di laurea" path="uniCourse"
									items="${uniCourses}"></t:select>
								<t:checkbox path="uniIsSupplementaryYear" label="Fuori corso" />
								<t:input path="uniYear" label="Anno di corso" placeholder="2"
									inputprepend="fa fa-book" />

								<h2>Informazioni per il progetto</h2>
								<span class="label label-warning">Attenzione</span>
								<p>Inserire nel seguente campo l'indirizzo dell'account
									Google che verr&agrave; associato allo smartphone assegnato.</p>
								<p>
									<a href="https://accounts.google.com/SignUp" target="_blank">Cliccare
										qui per creare un nuovo account Google o per associare un
										indirizzo e-mail esistente a un account Google</a>.
								</p>
								<t:input path="projectEmail" label="E-mail per il progetto*"
									placeholder="nome.cognome@gmail.com"
									inputprepend="fa fa-google-plus" />
								<t:input path="facebookEmail" label="E-mail account Facebook"
									placeholder="nome.cognome@gmail.com"
									inputprepend="fa fa-facebook" />
								<t:checkbox label="L'utente desidera ricevere il telefono"
									path="wantsPhone" />
								<t:select label="SIM" items="${simStatuses}" path="simStatus" />
								<div id="siminfo">
									<t:input path="cf" label="Codice fiscale"
										placeholder="MRARSS92C04F824P" inputprepend="fa fa-credit-card" />
									<t:input path="projectPhoneNumber"
										label="Numero di telefono per il progetto"
										placeholder="+39 349 12345678" inputprepend="fa fa-phone" />
									<t:input path="originalPhoneOperator"
										label="Operatore telefonico di partenza"
										inputprepend="fa fa-building" />
									<t:input path="iccid"
										label="ICCID della SIM (riportato sul retro della SIM stessa)"
										inputprepend="fa fa-phone" />
									<p>Caricare una scansione del documento di identit&agrave;.</p>
									<form:input path="idScan" type="file"/>
									<p>Caricare una scansione dell'ultima ricevuta di pagamento (per utenti con abbonamento).</p>
									<form:input path="lastInvoiceScan" type="file"/>
								</div>
								<span class="label label-info">Informazione</span>
								<p>
									L'identificativo che dovr&agrave; essere usato sull'app Android
									e sul sito di ParticipAct &egrave; &quot;<strong><span
										id="proj_email"></span></strong>&quot;. Scegliere una password per
									questo account.
								</p>
								<t:password path="password" label="Password*"
									inputprepend="fa fa-key" />
								<t:password path="confirmPassword" label="Conferma password*"
									inputprepend="fa fa-key" />
								<h2>Note aggiuntive</h2>
								<t:textarea path="notes" cssClass="span6" label="Note aggiuntive su questo utente, testo libero."></t:textarea>
							</div>
						</div>
						<div class="span2">
							<button class="btn btn-primary" type="submit">Save</button>
							<a class="btn btn-danger" type="submit"
								href='<c:url value="/protected/user/add"/>'>Cancel</a>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>