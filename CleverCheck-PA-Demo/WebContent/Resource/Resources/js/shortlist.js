$(document).ready(function(){
	setInterval(function(){ 
		var winheight = $(window).height();
		$(".shortlistPanel").css("height",winheight+"px");
	}, 30);

});

function openShortList() {	
	var value = $(".shortlistPanel").attr("data-value");
	if(value == "open") {
		$(".shortlistedmenu").animate({
			left: "-76px"
		}, 300, function(){
			$(".shortlistPanel").attr("data-value","close");
		});
	} else {
		$(".shortlistedmenu").animate({
			left: "-243px"
		}, 100, function(){
			$(".shortlistPanel").attr("data-value","open");
		});
	}
		$(".shortlistPanel").toggle();
		
		$(".shortlistPanel").animate({
			right: "0px"
		}, 100, function(){

		});
	
}