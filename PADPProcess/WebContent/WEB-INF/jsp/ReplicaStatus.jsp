<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div align="center" style="margin-top: 50px">
		<img alt="HP Cleverplan" src="new_hp_logo_1.jpg" width="120px">
	</div>
	<br>
	<br>
	<h3 align="center" style="color: red;">REPLICA STATUS</h3>
	<div id="tbldiv" align="center">
		<table id="smryTbl" border="1" bordercolor="black"
			style="font-family: Arial; font-size: 14px; text-align: center; width: 500px;">
			<tr style="font-weight: bold; padding: 10px">
				<td><span>Collection NAME</span></td>
				<td><span>Replica Status</span></td>
			</tr>
			<c:forEach items="${replicaStatusMap}" var="replicaMap">
				<tr style="padding: 10px">
					<td>${replicaMap.key}</td>
					<td>${replicaMap.value}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<h4 align="center">
		<a href="replication" style="padding-bottom: 50px;">GO BACK</a>
</body>
</html>