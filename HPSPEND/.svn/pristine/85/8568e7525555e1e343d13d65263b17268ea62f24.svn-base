<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page isELIgnored="false"%>


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
					HP SPEND STATUS-
					<%=df.format(new java.util.Date())%></h2></label>
			<table id="smryTbl" border="1" bordercolor="black"
				style="font-family: Arial; font-size: 14px; text-align: center; width: 500px;">
				<tr style="font-weight: bold; padding: 10px">
					<td><span>File Name</span></td>
					<td><span>Folder Name</span></td>
					<td><span>Upload Status</span></td>
					<td><span>Sheet Name</span></td>
					<td><span>No Of Records</span></td>
				</tr>
				<c:forEach items="${filesList}" var="list">
					<tr style="padding: 10px">
						<td>${list.value}</td>
						<td>${list.key.folderName}</td>
						<td>${list.key.fileUploadStatus}</td>
						<td>${list.key.sheetName}</td>
						<td>${list.key.noOfRecords}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<br />
		<div align="center">
			<a href="refresh">Click me to Refresh</a>
		</div>
	</form>
	<a style="display: block; text-align: center; padding-top: 20px;"
		href="http://${machineip}:8080/HPPAIORAWDP/loginsucess">Back</a>
</body>
</html>