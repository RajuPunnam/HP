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
.fa fa-database {
	-animation: spin .7s infinite linear;
	-webkit-animation: spin2 .7s infinite linear;
	font-size: 2.2em;
	color: red;
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
	<div align="center" style="margin-top: 50px; padding-bottom: 50px;">
		<img alt="Brand" src="new_hp_logo_1.jpg" width="150px;">
	</div>
	<center>
		<img alt="replica" src="m2mMigr.png" width="400px;">
	</center>
	<h2 align="center" style="padding-bottom: 50px;">
		<a
			onclick="document.getElementById('replicaloader').style.display = 'block';"
			href="movedata">Replication</a>
	</h2>
	<div id="replicaloader" align="center" style="display: none";>
		<img src="default.svg" width="100px"><label>Replicating...</label>
	</div>
	<h3 align="center">
		<a href="http://${machineip}:8080/HPPA/rest/cache/refresh">Cache
			Refresh(stag)</a>
	</h3>
	<h3 align="center">
		<a href="http://${machineip}:8080/HPDemo/rest/cache/refresh">Cache
			Refresh(prod)</a>
	</h3>

	<h4 align="center">
		<a href="http://${machineip}:8080/HPPAIORAWDP/PaIoDataprocess"
			style="padding-bottom: 50px;">GO BACK</a>
	</h4>
</body>
</html>