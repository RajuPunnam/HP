$(document).ready(function(){
				function reduceCharsForID() {
					$(".prodID").each(function(){
						var char = $(this).text();
						//console.log(char);
						var charLength = char.length;
						//console.log(charLength);
						if(charLength > 16) {
							var newChar = char.substring(0,13);
							//console.log(newChar);
							$(this).html('<span class="livText">'+newChar+'..</span>');
							$(this).append('<span class="delText" style="display: none;">'+char+'</span>');
							//$(this).addClass("hasDelText");
							$(this).addClass("hasDelText");
						}
					});
				}

				reduceCharsForID();

				$(function() {
					$(".prodID").hover(function(){
						var checkClass = $(this).hasClass("hasDelText");
						//console.log(checkClass);
						if(checkClass="true") {
							var selector = $(this);
							selector.find(".livText").hide();
							selector.find(".delText").show();
							//$(this).find("forprodID").find("prodId").hide();
						}
					}, function(){
						var checkClass = $(this).hasClass("hasDelText");
						//console.log(checkClass);
						if(checkClass="true") {
							var selector = $(this);
							selector.find(".livText").show();
							selector.find(".delText").hide();
							//$(this).find("forprodID").find("prodId").hide();
						}
					});
				});
			});


$(document).ready(function(){
	function reduceCharsForID() {
		$(".desc").each(function(){
			var char = $(this).text();
			//console.log(char);
			var charLength = char.length;
			//console.log(charLength);
			if(charLength > 18) {
				var newChar = char.substring(0,17);
				//console.log(newChar);
				$(this).html('<span class="livText">'+newChar+'..</span>');
				$(this).append('<span class="delText" style="display: none;">'+char+'</span>');
				//$(this).addClass("hasDelText");
				$(this).addClass("hasDelText");
			}
		});
	}

	reduceCharsForID();

	$(function() {
		$(".desc").hover(function(){
			var checkClass = $(this).hasClass("hasDelText");
			//console.log(checkClass);
			if(checkClass="true") {
				var selector = $(this);
				selector.find(".livText").hide();
				selector.find(".delText").show();
				//$(this).find("forprodID").find("prodId").hide();
			}
		}, function(){
			var checkClass = $(this).hasClass("hasDelText");
			//console.log(checkClass);
			if(checkClass="true") {
				var selector = $(this);
				selector.find(".livText").show();
				selector.find(".delText").hide();
				//$(this).find("forprodID").find("prodId").hide();
			}
		});
	});
});