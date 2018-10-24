<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>

<html>
<head>
<script type="text/javascript">
	$document.ready(function() {
		$("#hiddenReferesh").click();
	});
</script>
</head>
<body id="rptSmry">
	<%
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"dd/MM/yyyy");
	%>
	<form id="rptSmryFrm">
		<div style="float: right; position: fixed"></div>
		<div id="tbldiv" align="center">
			<label style="font-family: Arial;"><h2>
					HP PA/IO STATUS-
					<%=df.format(new java.util.Date())%></h2></label>
			<table id="smryTbl" border="1" bordercolor="black"
				style="font-family: Arial; font-size: 14px; text-align: center; width: 500px;">
				<tr style="font-weight: bold; padding: 10px">
					<td><span>File Name</span></td>
					<td><span>Folder Name</span></td>
					<td><span>Upload Status</span></td>
					<td><span>Records Count</span></td>
				</tr>
				<c:forEach items="${currentdayStatus}" var="currentdayStatus">
					<c:set var="status" value="${currentdayStatus.value.uploadStatus}" />
					<c:choose>
						<c:when test="${status == 'true'}">
							<tr style="padding: 10px; color: green">
								<td>${currentdayStatus.key}</td>
								<td>${currentdayStatus.value.sourceFolder}</td>
								<td>${currentdayStatus.value.uploadStatus}</td>
								<td>${currentdayStatus.value.noOfRecords}</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr style="padding: 10px; color: red">
								<td>${currentdayStatus.key}</td>
								<td>${currentdayStatus.value.sourceFolder}</td>
								<td>${currentdayStatus.value.uploadStatus}</td>
								<td>${currentdayStatus.value.noOfRecords}</td>
							</tr>
						</c:otherwise>
					</c:choose>

				</c:forEach>
			</table>
		</div>
		<br />
		<div align="center">
			<a href="rawdatauploadstatus">Click me to Refresh</a>
		</div>
	</form>
	<a style="display: block; text-align: center; padding-top: 20px;"
		href="PaIoDataprocess">Back</a>
</body>
</html>