function jsApiCall(data) {
		alert("call js api");
	    WeixinJSBridge.invoke(
	        'getBrandWCPayRequest',
	        data,
	        function(res){
	                WeixinJSBridge.log(res.err_msg);
	        }
	    );
    }

function callpay(data) {
	for(var x in data)
	{}
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
        } else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', jsApiCall); 
            document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
        }
    } else {
        jsApiCall(data);
    }
}