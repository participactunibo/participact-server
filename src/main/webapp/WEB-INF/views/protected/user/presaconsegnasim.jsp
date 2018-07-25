<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Presa in carico SIM</title>
    <style>
        * {
            margin: 0;
            padding 0
        }
        .logo {
            display: block;
   	        margin-left: auto;
            margin-right: auto;
            margin-top: 10px;
            margin-bottom: 20px;
        }
        h1 {
            text-align: center;
            font-size: 21px;
            margin-bottom: 24px;
        }
        h2 {
            text-align: left;
            font-size: 19px
        }
        h3 {
            text-align: left;
            font-size: 16px;
            margin-top: 6px;
            margin-bottom: 6px;
        }
        h3.ciri {
        	font-variant:small-caps;
        	text-align: center;
        }
        p {
            text-align: justify;
            font-size: 17px;
            margin-bottom: 6px;
        }
        .data p {
        	font-size: 17px
        }
        .box {
       	    width:20px;
       	    height:20px;
       	    border:1px solid #000;
       	    display: inline-block;
       	    margin-right: 4px;
        }
    </style>
    </head>
<!--     <body onload="window.print()"> -->
    <body>
        <img class="logo" width="150" height="150" src='<c:url value="/resources/img/bologna_universita.jpg"/>' />
        <h3 class="ciri">Alma Mater Studiorum &mdash; Universit&agrave; di Bologna</h3>
        <h3 class="ciri">C.I.R.I. ICT - Centro Interdipartimentale di Ricerca Industriale ICT</h3>
        <br/>
        <h1>Modulo di PRESA IN CARICO SIM<br/>per il progetto ParticipAct</h1>
        <p>La firma del presente verbale porta all'assunzione della qualifica di Utilizzatore e comporta l’assunzione di tutte le condizioni di cui al Regolamento di Servizio. Al termine dell'utilizzo l'attrezzatura deve essere restituita alla struttura preposta.</p>
        <br/>
        <p><c:choose>
        <c:when test="${user.gender == 'MALE'}">Il sottoscritto: </c:when>
        <c:otherwise>La sottoscritta: </c:otherwise>
        </c:choose><c:out value="${user.name}" />&nbsp;<c:out value="${user.surname}" />
        <p><c:choose>
        <c:when test="${user.gender == 'MALE'}">Nato a: </c:when>
        <c:otherwise>Nata a: </c:otherwise>
        </c:choose><c:out value="${citybornname}" />&nbsp;(<c:out value="${citybornprov}" />)</p>
        <p>Il: <joda:format value="${user.birthdate}" pattern="dd/MM/yyyy" /></p>	
        <p>Codice Fiscale:  <c:out value="${user.cf}" /></p>
        <p>Scuola di afferenza:	<c:choose>
									<c:when
										test="${user.uniSchool == 'AGRARIA_E_MEDICINA_VETERINARIA'}">Agraria e medicina veterinaria</c:when>
									<c:when
										test="${user.uniSchool == 'ECONOMIA_MANAGEMENT_E_STATISTICA'}">Economia, Management e Statistica</c:when>
									<c:when
										test="${user.uniSchool == 'FARMACIA_BIOTECNOLOGIE_E_SCIENZE_MOTORIE'}">Farmacia, Biotecnologie e Scienze Motorie</c:when>
									<c:when test="${user.uniSchool == 'GIURISPRUDENZA'}">Giurisprudenza</c:when>
									<c:when test="${user.uniSchool == 'INGEGNERIA_E_ARCHITETTURA'}">Ingegneria e Architettura</c:when>
									<c:when test="${user.uniSchool == 'LETTERE_E_BENI_CULTURALI'}">Lettere e Beni Culturali</c:when>
									<c:when
										test="${user.uniSchool == 'LINGUE_E_LETTERATURE_TRADUZIONE_E_INTERPRETAZIONE'}"> Lingue e Letterature, Traduzioni e Interpretazione</c:when>
									<c:when test="${user.uniSchool == 'MEDICINA_E_CHIRURGIA'}">Medicina e Chirurgia</c:when>
									<c:when
										test="${user.uniSchool == 'PSICOLOGIA_E_SCIENZE_DELLA_FORMAZIONE'}">Psicologia a Scienze della Formazione</c:when>
									<c:when test="${user.uniSchool == 'SCIENZE'}">Scienze</c:when>
									<c:when test="${user.uniSchool == 'SCIENZE_POLITICHE'}">Scienze Politiche</c:when>
								</c:choose></p>
        <p>Numero di telefono: <c:out value="${user.contactPhoneNumber}" /></p>
        <p>E-mail: <c:out value="${user.officialEmail}" /></p>
        <br/>
        <p><em>dichiara di <strong>prendere in consegna</strong> la seguente SIM, identificata da ICCID sotto riportato, di aver <strong>preso visione</strong> e di <strong>impegnarsi a rispettare</strong> quanto riportato nel Regolamento di Servizio per il progetto ParticipAct e nelle Condizioni Generali di attivazione del servizio <strong>ricarica TIM TUO</strong>, Modulo Utilizzatore.</em></p>
        <br/>
        <p>ICCID: <c:out value="${user.newIccid}" /></p>
        <p>Numero di telefono: <c:out value="${user.projectPhoneNumber}" /></p>
        <br />
        <p><span style="color: #aaa">(Citt&agrave;)</span>_______________, <joda:format value="${today}" pattern="dd/MM/yyyy" /><p>
        <p style="text-align: right;margin-right:40px">Firma del partecipante</p>
        </div>
    </body>
</html>
