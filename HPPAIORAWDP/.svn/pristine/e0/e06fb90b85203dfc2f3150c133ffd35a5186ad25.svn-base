<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style>
.glyphicon-refresh-animate {
    -animation: spin .7s infinite linear;
    -webkit-animation: spin2 .7s infinite linear;
    font-size: 2.2em;
    color: red;
}


@-webkit-keyframes spin2 {
    from { -webkit-transform: rotate(0deg);}
    to { -webkit-transform: rotate(360deg);}
}

@keyframes spin {
    from { transform: scale(1) rotate(0deg);}
    to { transform: scale(1) rotate(360deg);}
}
</style>

</head>
<body>
	<div align="center" style="margin-top: 50px">
			<img alt="HP Cleverplan" src="new_hp_logo_1.jpg" width="120px">
		</div>
<h2 align="center" style="padding-top: 50px;padding-bottom: 20px;font-style: oblique;font-family: cursive;"><a  onclick="document.getElementById('bomrawloader').style.display = 'block';"  href="loadbomfiles">BOMDP</a></h2>
<div id="bomrawloader" align="center" style="display: none";>
<span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>BOM Data Processing...
</div> 

<h2 align="center" style="padding-top: 50px;padding-bottom: 20px;font-style: oblique;font-family: cursive;"><a  onclick="document.getElementById('rawloader').style.display = 'block';"  href="rawdataprocess">HPRAWDP</a></h2>
<div id="rawloader" align="center" style="display: none";>
<span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>HP RD Processing...
</div>
<h2 align="center" style="padding-top: 50px;font-style: oblique;font-family: cursive;"><a onclick="document.getElementById('spend').style.display = 'block';" href="http://${machineip}:8080/HPSPEND/spendprocess">HPSPDP</a></h2>
<div id="spend" align="center" style="display: none";>

<span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>HP SPEND Processing...
</div>
<a style="display:block;text-align:center;padding-top: 20px;font-size: 20px;color: red " href="PaIoDataprocess" >PA IO DP</a>
<body>

</body>
</html>