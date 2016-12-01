    
$(document).ready(function(){
	
	$('#showLowPassword').click(function(){
		$('showLowPassword').prop("disabled",true);
		$('#showLowPassword').fadeOut(1000);
		$('#lowPassword').delay(500).fadeIn(400);
		$('#passwordInfo1').delay(2000).fadeIn(400);
		$('#passwordInfo2').delay(3500).fadeIn(400);
		$('#passwordInfo3').delay(5500).fadeIn(400);
		
		$('#passwordInfo3').delay(1000).animate({"font-size":"3em"}, 1000);		
	});

});
