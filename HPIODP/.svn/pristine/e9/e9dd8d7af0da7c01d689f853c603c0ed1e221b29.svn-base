<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center><h1><i>IO Data Process Status</i></h1>
<img src="iodp.jpg"/ height="252" width="536" ></center>
<table align="center" cellpadding="1" border="1" width="600" >   
             <thead> <tr>   
                        <th> Table  </th>   
                        <th> Status OldCount processCount TotalCount </th>   
                </tr>   
              </thead>   
                 
               <tbody>   
      <c:set var="mapofMaps" value ='<%=request.getSession().getAttribute("iODPStatus")%>'/>
              <c:forEach var="map" items="${mapofMaps}">   

                     <tr>   
                        <td>${map.key} </td>   
                              
                        <td>${map.value}</td>   
                            
                     </tr>   
                    </c:forEach>   
                    </tbody>   
 </table>  

</body>
</html>