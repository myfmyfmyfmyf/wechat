<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<title>下订单</title>
<script>
	wx.config({
	    debug: false,
	    appId: '<%=request.getAttribute("appId")%>',
	    timestamp: '<%=request.getAttribute("jsapiTime")%>',
	    nonceStr: '<%=request.getAttribute("jsapiNonceStr")%>',
	    signature: '<%=request.getAttribute("jsapiStr1")%>',
	    jsApiList: ['hideOptionMenu','checkJsApi','networkType']
	});
	wx.ready(function(){
    	// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.hideOptionMenu();
	});
	wx.error(function(res){
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	});

	//调用微信JS api 支付
	function jsApiCall()
	{
	    WeixinJSBridge.invoke(
	    'getBrandWCPayRequest',
	    {	"appId": '<%=request.getAttribute("appId")%>', 
	    	"timeStamp": <%=request.getAttribute("payTimeStamp")%>,
	        "nonceStr": '<%=request.getAttribute("nonceStr")%>',
	        "package": '<%=request.getAttribute("packageVal")%>',
	        "signType": '<%=request.getAttribute("signType")%>',
	        "paySign": '<%=request.getAttribute("paySign")%>',
	    },//json串
	    function (res){
	        	alert("同步返回数据："+JSON.stringify(res));
	        	// 支付成功后的回调函数
	            if(res.err_msg == "get_brand_wcpay_request：ok" ) {
	            	// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	           		alert("支付成功，请稍后查询...");
	            } 
	        }
	    );
	}
	 
	function callpay(){
	    if (typeof WeixinJSBridge == "undefined"){
	        if (document.addEventListener){
	            document.addEventListener('WeixinJSBridgeReady', jsApiCall, false);
	        }
	        else if (document.attachEvent){
	            document.attachEvent('WeixinJSBridgeReady', jsApiCall);
	            document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
	        }                     
	    }else{
	        jsApiCall();
	    }
	}
	
</script>
</head>
<body>
	<form action="/payServlet" name="myform" method="post">
		<input type="button" name="orderBtn" value="支付"  onclick="javascript:callpay();return false;"/>
	</form>
</body>
</html>