<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style>
.glyphicon-refresh-animate {
	-animation: spin .7s infinite linear;
	-webkit-animation: spin2 .7s infinite linear;
	font-size: 2.2em;
}

@
-webkit-keyframes spin2 {from { -webkit-transform:rotate(0deg);
	
}

to {
	-webkit-transform: rotate(360deg);
}

}
@
keyframes spin {from { transform:scale(1)rotate(0deg);
	
}

to {
	transform: scale(1) rotate(360deg);
}
}
</style>

</head>
<body>
	<div align="center" style="margin-top: 50px">
		<img alt="HP Cleverplan" src="new_hp_logo_1.jpg" width="120px">
	</div>
	<h2 align="center"
		style="padding-bottom: 50px; font-style: oblique; font-family: serif;">
		<a href="rawdatauploadstatus">RDP STATUS</a>
	</h2>
	<h2 align="center"
		style="padding-bottom: 50px; font-style: oblique; font-family: serif;">
		<a href="UpdateskuAvil">HPPADP</a>
	</h2>

	<h2 align="center"
		style="padding-bottom: 50px; font-style: oblique; font-family: serif;">
		<a
			onclick="document.getElementById('ioloader').style.display = 'block';"
			href="http://${machineip}:8080/HPIODP/">HPIODP</a>
	</h2>
	<div id="ioloader" align="center" style="display: none";>
		<img alt="" src="gears.svg" width="80px">HP IODP Loading...
	</div>

	<h2 align="center"
		style="padding-bottom: 50px; font-style: oblique; font-family: serif;">
		<a
			onclick="document.getElementById('replica').style.display = 'block';"
			href="http://${machineip}:8080/PADPProcess/replication">PA
			Replication</a>
	</h2>
	<a
		style="display: block; text-align: center; padding-top: 20px; font-size: 20px; color: red"
		href="loginsucess">Back</a>
</body>
</html>