<!doctype html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
<meta name="apple-mobile-web-app-status-bar-style" content="yes" />

<title>hp CLEVER CHECK</title>
<!-- theme fonts -->
<link href="resources/assets/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="resources/assets/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link href="resources/css/app.css" rel="stylesheet" />



<script type="text/javascript">
	function loginFormSubmit() {
		var status = false;
		//alert("form submit()");
		var mailid = $("#email").val();
		/*  alert("mail id  :"+mailid); */
		if (mailid != "") {
			var mailidpattern = /[a-zA-Z]+\.[a-zA-Z]+@(hp|gmail|ecoihub|techouts)\.(com)/;
			//alert("returned value of patern--------"+mailidpattern.test(mailid));				
			if (mailidpattern.test(mailid)) {
				$("#mailidErrorMessage").html("");
				var pword = $("#password").val();
				/*  alert("password  :"+pword); */
				if (pword != "") {
					status = true;
				} else { //alert("indide password else");

					$("#passwordErrorMessage").html("invalid password");
					$("#password").focus();

				}//password

			} else {
				//alert("indide mailid else");
				$("#passwordErrorMessage").html("");
				$("#mailidErrorMessage").html("invalid mail id");
				$("#username").focus();

			}

		} else {
			$("#passwordErrorMessage").html("");
			$("#mailidErrorMessage").html("invalid mail id");
			$("#username").focus();

		}//mailid

		if (status) {

			/* alert("====== inside j_spring_security========="); */
			var pname = location.pathname.split("/");
			document.getElementById("form").action = "/" + pname[1]
					+ "/j_spring_security_check"
			document.getElementById("form").submit();
		} else {
			return false;
		}

	}
</script>

</head>
<style>
.form-control::-webkit-input-placeholder {
	color: #ccc;
}

.form-control:-moz-placeholder {
	color: #ccc;
}

.form-control::-moz-placeholder {
	color: #ccc;
}

.form-control:-ms-input-placeholder {
	color: #ccc;
}

.float-label-control input, .float-label-control textarea {
	border-bottom: 1px solid #ccc;
	width: 111%;
}

.m-top {
	position: fixed;
	top: 50%;
	height: 12em;
	margin-top: -6em;
	width: 85%;
}

.glyphicon {
	color: #ccc;
}

.float-label-control {
	margin-bottom: 3em;
}

.btn-blue {
	width: 95%;
	margin-left: 12px;
}

.form-control:focus {
	border-color: #fff;
}

.btn-blue:focus {
	border: none;
	outline: none;
}

.btn-blue:active {
	outline: 0px;
	border: none;
}

.validate {
	margin: 0 0 0 20px;
}

.passwordErrorMessage {
	margin: 0 0 0 20px;
}

input:-webkit-autofill, input:-webkit-autofill:hover, input:-webkit-autofill:focus,
	input:-webkit-autofill:active {
	-webkit-transition:
		"background-color #000 ease-in-out 0s, color 5000s ease-in-out 0s";
	-webkit-transition-delay: 1000s;
	-webkit-text-fill-color: #fff !important;
	-webkit-box-shadow: 0 0 0 1000px white inset !important;
	background: transparent;
	background-color: transparent;
	transition: all 5000s ease-in-out 0s;
	transition-property: background-color, #000;
}

@media only screen and (max-width: 320px) {
	.m-top {
		position: fixed;
		top: 50%;
		height: 12em;
		margin-top: -6em;
		width: 85%;
	}
	.float-label-control input, .float-label-control textarea {
		width: 115%;
	}
}

@media only screen and (min-width: 560px) {
	.float-label-control input, .float-label-control textarea {
		width: 100%;
	}
}
</style>


<body style="overflow-x: hidden;">
	<div
		style="background-color: #1d243d; width: 100%; padding-left: 30px; padding-right: 30px; padding-bottom: 3px;">
		<div class="container">
			<div
				style="height: 50px; line-height: 50px; margin: margin: 4px 2px 3px -7px;">
				<span
					style="color: #fff; font-size: 20px; font-weight: 600; margin: -18px -2px -14px -9px;">CLEVERCHECK</span>
			</div>
		</div>

		<div style="padding-top:; margin: -20px 0 0 -115px;">
			<img src="Resources/app/images/Allgebra_Logo.png"
				style="margin: 7px 0 0 123px; width: 75px; height: 25px;">
		</div>
	</div>

	<div class="content-body" style="background-color: #282F44">
		<div class="container">
			<div class="row">
				<div class="col-xs-12"
					style="padding-left: 20px; padding-right: 30px;">
					<div class="m-top">

						<div class="validate"></div>
						<form action="" id="form" method="post" autocomplete="off">

							<span id="mailidErrorMessage" style="color: red"> <span
								id="mailidErrorMessage"><c:if
										test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
										<font color="red"> Your login attempt was not
											successful due to <br /> <br /> <c:out
												value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
										</font>
										<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session" />
									</c:if> </span>

							</span> <span id="passwordErrorMessage" style="color: red"> </span>

							<div class="form-group float-label-control" style="height: 25px;">
								<div class="col-xs-2">
									<img src="resources/app/images/userLogin.svg"
										style="margin-top: 6px; font-size: 15px; color: #ccc;">
								</div>
								<div class="col-xs-10" style="margin-left: -15px;">
									<input id="email" type="email" name='username'
										class="form-control"  placeholder="Email id / Mobile Number"
										style="color: #ccc; font-size: 16px;">
								</div>
							</div>
							<div class="form-group float-label-control">

								<div class="col-xs-2">
									<img src="resources/app/images/lock.svg"
										style="margin-top: 2px; font-size: 15px;">
								</div>
								<div class="col-xs-10" style="margin-left: -15px;">
									<input type="password" id="password" name='password'
										class="form-control" placeholder="Password"
										style="color: #ccc; font-size: 16px;" autocomplete="off" maxlength="20">
								</div>

							</div>

							<div class="form-group float-label-control">
								<a href="retrievePassword.xhtml" class="pull-right"
									style="position: absolute; top: 7px; right: 0px; color: #fff; margin-right: 10px; font-size: 14px;">Forgot?</a>
							</div>
							<div>
								<input type="submit" onclick="loginFormSubmit(); return false;"
									value="LOG IN" class="btn-blue"
									style="margin-top: 20px; height: 50px;">

							</div>
							<div align="center" style="margin-top: 30px;">
								<a href="NewSignUp.xhtml"
									style="font-weight: 300; color: #fff; font-size: 14px;">New
									to Clevercheck ? Signup !</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="resources/assets/jquery/jquery-2.1.1.min.js"></script>
	<script type="text/javascript"
		src="resources/assets/bootstrap/js/bootstrap.min.js"></script>
	<script>
		(function($) {
			$.fn.floatLabels = function(options) {

				// Settings
				var self = this;
				var settings = $.extend({}, options);

				// Event Handlers
				function registerEventHandlers() {
					self.on('input keyup change', 'input, textarea',
							function() {
								actions.swapLabels(this);
							});
				}

				// Actions
				var actions = {
					initialize : function() {
						self
								.each(function() {
									var $this = $(this);
									var $label = $this.children('label');
									var $field = $this.find('input,textarea')
											.first();

									if ($this.children().first().is('label')) {
										$this.children().first().remove();
										$this.append($label);
									}

									var placeholderText = ($field
											.attr('placeholder') && $field
											.attr('placeholder') != $label
											.text()) ? $field
											.attr('placeholder') : $label
											.text();

									$label.data('placeholder-text',
											placeholderText);
									$label.data('original-text', $label.text());

									if ($field.val() == '') {
										$field.addClass('empty')
									}
								});
					},
					swapLabels : function(field) {
						var $field = $(field);
						var $label = $(field).siblings('label').first();
						var isEmpty = Boolean($field.val());

						if (isEmpty) {
							$field.removeClass('empty');
							$label.text($label.data('original-text'));
						} else {
							$field.addClass('empty');
							$label.text($label.data('placeholder-text'));
						}
					}
				}

				// Initialization
				function init() {
					registerEventHandlers();

					actions.initialize();
					self.each(function() {
						actions.swapLabels($(this).find('input,textarea')
								.first());
					});
				}
				init();

				return this;
			};

			$(function() {
				$('.float-label-control').floatLabels();
			});
		})(jQuery);
	</script>
</body>
</html>



