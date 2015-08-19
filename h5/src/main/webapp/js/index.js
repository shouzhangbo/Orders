var reqUrl='http://localhost:8080/CreateOrder/test.json';

$('#recharge').click(function(){
	var mobile = $('#mobile').val();
	var amount = $('#amount').val();
	var userId = $('#mobile').val();
	$.ajax({
        type: "POST",
        async: false,
        url: reqUrl,
        dataType: "jsonp",
        jsonp: "callbackparam", //服务端用于接收callback调用的function名的参数  
        jsonpCallback: "success_jsonpCallback",
        data: {
        	mobile: mobile,
        	userId: userId,
        	amount: amount
        },
        dataType: "json",
        timeOut: 10000,
        context: null,
        success: function (b) {
        	console.log(b);
            var respCode = b[0].name;
            if('0000'==respCode){
            	window.location.href='success.html';
            }else{
            	window.location.href='failed.html';
            }
        }
	});
});