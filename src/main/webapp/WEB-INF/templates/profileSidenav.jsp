<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<ul class="nav nav-list bs-docs-sidenav affix-top" data-spy="affix" data-offset-top="200">
	<sec:authorize access="hasAnyRole('ROLE_USER')">
	
				<li class="active"><a data-toggle="modal" href="#statisticsModal">Statistics</a></li>
		
	</sec:authorize>

</ul>