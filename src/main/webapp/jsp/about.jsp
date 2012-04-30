<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css" media="screen">
@import
url(
<c:url
value=
"/stylesheets/bootstrap.min.css"
/>
);
@import
url(
<c:url
value=
"/stylesheets/bootstrap-responsive.css"
/>
);
</style>
<title>&raquo; About IServer</title>
</head>

<body>
	<br />
	<div class="container">
		<div class="row">
			<h1>&raquo; Information about the images update</h1>
			<br />
			<table class="table table-striped table-bordered">
				<c:forEach var="pool" items="${it.poolingInformations}">
					<tr>
						<c:if test="${pool.key=='Output Folder'}">
							<c:set var="access_folder_images" value="${pool.value}" scope="page" />
						</c:if>
						<td><b>${pool.key}</b></td>
						<td>${pool.value}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<hr />
		<div class="row">
			<h1>&raquo; Information about access to images</h1>
			<br /> <b>Storage Path :</b>
			<c:out value="${pageScope.access_folder_images}" />
		</div>
	</div>
</body>
</html>