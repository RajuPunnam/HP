/*

Swipe Functions

@Author: iHub Technologies Pvt. Ltd.


*/

//alert("Hello!");

var gnStartX = 0;
var gnStartY = 0;
var gnCurrentX = 0;
var gnCurrent = 0;
var gnEndX = 0;
var gnEndY = 0;

var globalMovement = 0;


$(document).on('vmousedown', function(event){
    gnStartX = event.pageX;
    gnStartY = event.pageY;
    /*event.preventDefault();*/
});

$(document).on('vmousemove', function(event){
    var newPosX = event.pageX;
    var newPosY = event.pageY; 
    var distance = Math.ceil(nthroot(Math.pow((newPosX - gnStartX),2) + Math.pow((newPosY - gnStartY),2), 2));    
    /*event.preventDefault();*/
});

$(document).on('vmouseup', function(event){
    gnEndX = event.pageX;
    gnEndY = event.pageY;  
    var distance = Math.ceil(nthroot(Math.pow((gnEndX - gnStartX),2) + Math.pow((gnEndY - gnStartY),2), 2));
    globalMovement+=distance;
    
    if(Math.abs(gnEndX - gnStartX) > Math.abs(gnEndY - gnStartY)) {
        if(gnEndX > gnStartX) {
            //alert("Swipe Right - Distance " + distance + 'px');
        } else {
            //alert("Swipe Left - Distance " + distance + 'px');
        }
    } else {
        if(gnEndY > gnStartY) {
            //alert("Swipe Bottom - Distance " + distance + 'px');
        	if(distance >= "2") {
    			closeBusinessUnits();
        	}
        } else {
            //alert("Swipe Top - Distance " + distance + 'px');
        	if(distance >= "2") {
        		openBusinessUnits();
        	}
        }        
    }  
    //alert('Global movement = '  + globalMovement);
    /*event.preventDefault();*/      
});

function nthroot(x, n) {
  try {
    var negate = n % 2 == 1 && x < 0;
    if(negate)
      x = -x;
    var possible = Math.pow(x, 1 / n);
    n = Math.pow(possible, n);
    if(Math.abs(x - n) < 1 && (x > 0 == n > 0))
      return negate ? -possible : possible;
  } catch(e){}
}