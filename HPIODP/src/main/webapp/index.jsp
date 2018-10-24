<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Page</title>
</head>
<body>
<center>
<img src="pic.jpg" ></img>
<h1><i>IO Automation Process </i></h1>

<!-- <img src="iodp.jpg" height="143" width="250"></img><br><br> -->
<a href="rest/inventoryOptimization/iodp"><font color="balck">IO Daily Raw to Process(Mongo)</font></a><br>
<br><br>

<!-- <img src="iodp.jpg" height="143" width="250"></img><br><br> -->
<a href="rest/inventoryOptimization/forecastiodp"><font color="balck">IO Forecast Weekly Raw to Process(Mongo)</font></a><br>
<br><br>

<!-- <img src="m2mMigr.png" height="143" width="250"></img><br><br> -->
<a href="rest/inventoryOptimization/mongoTomysql"><font color="balck">IO Daily Collections Mongo Staging To MySql Staging</font></a><br>
<br><br>

<!-- <img src="m2mRepli.png" height="143" width="250"></img><br><br> -->
<a href="rest/inventoryOptimization/mysqlReplication"><font color="balck">IO Daily Collections MySql Staging to MySql Production</font></a>
<br><br><br>

<!-- <img src="bomM2M.png" height="143" width="250"></img><br><br> -->
<a href="rest/inventoryOptimization/weekly_BomToMySql"><font color="balck">Weekly (master_bom_flex_lt_tbl , masterskuavbom_pa) BOM MySQL Migration Process</font></a>
<br><br><br>

<!-- <img src="bomM2M.png" height="143" width="250"></img><br><br> -->
<a href="rest/inventoryOptimization/monthly_BomToMySql"><font color="balck">Monthly (master_sku_av_bom_tbl , masterav_pn_bom_v11_tbl) BOM MySQL Migration Process</font></a>
<br><br><br>

<!-- <img src="bomM2M.png" height="143" width="250"></img><br><br> -->
<a href="rest/inventoryOptimization/bhuvanesh_BomToMySql"><font color="balck">BOM( pri_alt_list_tbl , pa_flex_super_bom_v1 ) MySQL Migration Process</font></a>
<br><br><br>

<!-- <img src="bomM2M.png" height="143" width="250"></img><br><br> -->
<a href="rest/inventoryOptimization/runExcelCode"><font color="balck">Weekly Sosa & Shortages Data Process</font></a>
<br><br><br>

<a href="rest/forecast/runForecast"><font color="balck">Weekly ForeCast Printers to MySQL</font></a>
<br><br><br>

<a href="rest/forecast/printers"><font color="balck">Printers && EO Commodity</font></a>
<br><br><br>


</center>
</body>
</html>
