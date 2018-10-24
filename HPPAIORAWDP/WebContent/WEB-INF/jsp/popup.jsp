<!doctype html>
<html>
<head>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.9/jquery-ui.js"
	type="text/javascript"></script>
<link
	href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.9/themes/blitzer/jquery-ui.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript">
	$(function() {
		$("#dialog").dialog({
			modal : true,
			autoOpen : false,
			title : "jQuery Dialog",
			width : 800,
			height : 550
		});
		$("#btnShow").click(function() {

			//POC-Report1.pdf
			var url = $('#inputbox').val();
			//$('#pdfIfrmae').src('POC-Report1.pdf');
			debugger;
			var url1 = 'excel?fromdate=' + fromdate + '&todate=' + todate;
			$('#pdfIfrmae').attr('src', url);
			$('#dialog').dialog('open');

		});
	});
</script>
</head>
<body>
	<input type="text" id="inputbox" width="100" />
	<input type="button" id="btnShow" value="Show Popup" />
	<a href="POC-Report1.pdf" target="_blank">POC-Report1.pdf</a>
	<div id="dialog" style="display: none" align="center">
		This is a jQuery Dialog.
		<iframe id="pdfIfrmae" style="width: 600px; height: 500px;"
			frameborder="0"></iframe>
	</div>
</body>
</html>
