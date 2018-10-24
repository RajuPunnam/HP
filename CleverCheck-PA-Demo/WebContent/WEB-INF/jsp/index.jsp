<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <title>hp CLEVER CHECK</title>
	<!-- theme fonts -->
     <link href="Resources/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="Resources/assets/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="Resources/app/css/app.css" rel="stylesheet"/>

<script type="text/javascript">
		
		function loginFormSubmit(){			
			var status=false;
			 //alert("form submit()");
			 var mailid=$("#username").val();
			/*  alert("mail id  :"+mailid); */
			 if(mailid!=""){				
				var mailidpattern=/[a-zA-Z]+\.[a-zA-Z]+@(hp|gmail|ecoihub|techouts)\.(com)/;
				//alert("returned value of patern--------"+mailidpattern.test(mailid));				
					if(mailidpattern.test(mailid)){
					$("#mailidErrorMessage").html("");
					 var pword=$("#password").val();
					/*  alert("password  :"+pword); */
					if(pword!=""){
						status=true;
						}
					else{	//alert("indide password else");
						
						$("#passwordErrorMessage").html("invalid password");
						$("#password").focus();
					
					}//password
					 
						
					}
					else{
						//alert("indide mailid else");
						$("#passwordErrorMessage").html("");
						$("#mailidErrorMessage").html("invalid mailid");
						$("#username").focus();	
					
					}
					
								
			 }
			 else{	
				 $("#passwordErrorMessage").html("");
				 $("#mailidErrorMessage").html("invalid mailid");
				 $("#username").focus();	
				
			 }//mailid
			 
			
			 
			 
			
		if(status){
				
			/* alert("====== inside j_spring_security========="); */
			var pname = location.pathname.split("/");
			document.getElementById("form").action = "/"+pname[1]+"/j_spring_security_check"
			document.getElementById("form").submit();
		}
		else{
			return false;
		}
			
			 
			 
		}
		</script>
		
	
		</head>
<style>
/* 	img {
    max-width: 100%;
    height: auto;
    width: auto\9; /* ie8 */
} */

#wrapper {
    margin-left:auto;
    margin-right:auto;
    width:100%;
}
</style>
<body style="width: 100%">
	
	<div id="wrapper">
	<div style="height: 55px;background-color: #037CD5;width: 100%;">
		<div class="container">
			<div style="padding-top:;margin: 22px 0 0 0" class="col-xs-11">
			<span style="color: #fff;font-size: 31px;font-weight: 600;">CLEVERCHECK</span>
			</div>
			
			<div class="col-xs-1" style="padding-top:;margin: ">
				<img src="Resources/app/images/Allgebra_Logo.png" style="margin:20px -315px 1px 1px;width: 153px;float: right">
			</div>
		</div>
	</div>
	<div style="height: 40px;background-color: #006cb4;width: 100%;"></div>
	
    <div id="content-body">
		<div class="container">
			
					<div class="row"  style="margin-top: 50px;">
						<div class="col-xs-7" style="margin-top: 90px;">
							<!-- added by ravi  -->

					
					<form id="form" action="" method="POST">
							 <span id="mailidErrorMessage"><c:if
								test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
								<font color="red"> Your login attempt was not successful
									due to <br /> <br /> <c:out
										value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
								</font>
                            <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
							</c:if> </span> 
						 
				             <span id="passwordErrorMessage">  </span>
								<div class="form-group" style="width: 80%">
									<input name="username" id="username" type="text" class="form-control input-lg text-md" placeholder="Enter email / mobile">
								</div>
								<div class="form-group mt-25" style="width: 80%">
									<input name="password" id="password"  type="password"  class="form-control input-lg text-md" placeholder="Enter password">
								</div>
								<div class="form-group" style="margin-top: 30px;">
								<button onclick="loginFormSubmit(); return false;" class="btn btn-custom1" >LOGIN</button>									
									<!-- <a href="home.html" class="btn btn-custom1">LOGIN</a> -->
									
									<a class="loin-fp" href="ForgotPasswordRecovery.xhtml">
		                       Forgot Password ?
		                    </a>
									<!-- <a href="home.html" class="loin-fp">forgot password ?</a> -->
								</div>
								<div class="form-group" style="color: #8A8A8A;margin-top: 20px;">
									New to ALLgebra ? <a href="Signup.xhtml" class="text-custom1" style="font-weight: 600;"> Signup</a>
								</div>
							</form>
						</div>
						
					
					
					<div class="col-xs-5">
							<div class="clearfix col-xs-1">
								<img alt="" src="Resources/app/images/line-divider.png"
									style="height: 430px;" />
							</div>
							<div class="col-xs-3"></div>
								<div class="col-xs-4" style="top:50px;">
									<img src="Resources/app/images/systems.png"
										style="width: 190px;text-align: center;" />
								</div>
							<div class="col-xs-1"></div>
					</div>
					
				</div>
			
		</div>
   	</div>
    <script type="text/javascript" src="Resources/assets/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="Resources/assets/bootstrap/js/bootstrap.min.js"></script>
</div>
</body>
</html>



