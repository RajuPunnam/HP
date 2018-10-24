$(document).ready(function()
{
$("#addedquantity").click(function()
{
var skuinputquantity=$('#inputquantity').val();
var skutotalquantity=$('#totalskuquantity').val();
var skuname=$('#skuid').val();
if(skuinputquantity==0)
{
	skuinputquantity=skutotalquantity;
}
var data = 'inputquantity=' + skuinputquantity+'&skuId='+skuname;
$
.ajax({
	type : "POST",
	url : "cart",
	data : data,
	success : function(response) 
	{
		var result = response;
		document.getElementById('cartquantity').innerHTML = result;
	},
	error : function(error) {

	}
});

});
});