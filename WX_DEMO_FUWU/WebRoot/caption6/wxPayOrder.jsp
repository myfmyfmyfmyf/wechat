<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>微信支付Demo</title>
<script>
	function createOrder(){
	//----待修改---
		var getUrl="http://www.haiyisoft.com/SELearning/payServlet.slt";
		
	
		var appId="wx327a22670d59613f";
		var changeurl=getUrl.replace(/[:]/g,"%3a").replace(/[/]/g,"%2f").replace(/[\?]/g,"%3f").replace(/[=]/g,"%3d").replace(/[&]/g,"%26");
		var tourl="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+changeurl+"&response_type=code&scope=snsapi_base&state=location#wechat_redirect";
	 	location.href=tourl;
	 	
	 	//location.href=getUrl;
	}
</script>
</head>
<body>
	<form action="/payServlet" name="myform" method="post" style="width: 100%;height: 100%">
		<div style="height: 30px;width: 100%;background:#aa0000;padding-top: 8px;color: white;" align="center">正宗栖霞苹果</div>
		<div style="width:100%;height: 350px;"><img src="myApple.jpg"   height="100%" width="100%" /></div>
		<div style="width:100%;border-bottom: solid 2px #999999;border-top: solid 2px #999999">正宗栖霞苹果&nbsp;条纹红富士&nbsp;片红红富士&nbsp;冰糖心&nbsp;圣诞/节日礼品</div>
		<div style="width:100%;color:red">￥<font size="15">75</font>.00（<font color="#999999" style="text-decoration:line-through">￥90.00</font>）</div>
		<div style="width:100%;color:#999999;font-size: 0.5em">
			<div style="width: 34%;float: left">快递：0.00</div>
			<div style="width: 30%;float: left;" align="center">月销量168笔</div>
			<div style="width: 34%;float: left" align="right">北京</div>
		</div>
		<div style="width:100%;height:30px">
			<div style="width:calc(100% - 80px);height:30px;float:left"></div>
			<div onclick="createOrder()" style=" background: linear-gradient(yellow,orange);float:left;width: 80px;height:30px;padding-top:9px;color: white;font-weight:bold" align="center">下订单</div>
		</div>
	</form>
</body>
</html>