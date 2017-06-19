<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<html>
<head>
<title>朋友圈</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=0e5deb9a9d82f460c2e6590182e71239&plugin=AMap.Geocoder"></script>
<style type="text/css">
*{
    margin:0px;
    padding:0px;
}
body, button, input, select, textarea {
    font: 12px/16px Verdana, Helvetica, Arial, sans-serif;
}

.myTitle-left {
	width:35%;
	height:23px;
	border:1px solid;
	background: url(source/bg.png);
	float:left;
	color:#ffffff;
	font-size: 18px;
	padding-top:7px;
	border-top-left-radius:2em;
	border-bottom-left-radius:2em;
	-webkit-border-top-left-radius:2em; /* Safari */
	-webkit-border-bottom-left-radius:2em; /* Safari */
}
.myTitle-right {
	width:35%;
	height:23px;
	border:1px solid #cccccc;
	background: #ffffff;
	float:left;
	color:#000000;
	font-size: 18px;
	padding-top:7px;
	border-top-right-radius:2em;
	border-bottom-right-radius:2em;
	-webkit-border-top-right-radius:2em; /* Safari */
	-webkit-border-bottom-right-radius:2em; /* Safari */
}
.myHeader{
	border:2px solid #eee;
	width: 55px;
	border-radius: 50%;
}
#dialog{
	position: absolute;
	z-index: 2;
	width: 100%;
	height: 100%;
}
.dialog-bg{
	position: absolute;
	z-index: 3;
	width: 100%;
	height: 100%;
	background: url(source/bk.png);
	filter: blur(10px); 
	-webkit-filter: blur(15px);
	-moz-filter: blur(15px);
	-o-filter: blur(15px);
	-ms-filter: blur(15px);
}
.dialog-content{
	position: absolute;
	z-index: 4;
	width: 100%;
	height: 100%;
}
</style>
</head>
<body style="overflow: hidden;" >
<div id="dialog"  style="visibility: hidden">
	<div class="dialog-bg"></div>
	<div class="dialog-content">
		<div style="width:100%;height:35px;padding-top:10px;border-bottom: solid 1px #555555">
			<div onclick="publishTrack()" style="line-height: 50px;color:black;font-size: 13px;float: left;vertical-align: middle;">
		        取消
		    </div>
		    <div style="color:black;font-size: 18px;width: calc(100% - 80px);float: left" align="center" >I'M&nbsp;朋友圈</div>
		    <div onclick="publishTrack()" style="line-height: 50px;color:black;font-size: 13px;float: left;vertical-align: middle;">
		       	 发布
		    </div>
		</div>
		<textarea style="width:93%;height:80px;margin-left:10px" placeholder='说点什么吧...' >
		</textarea>
		<div style="width:93%;color:#555555;margin:10px" align="center">
			<img alt="" src="source/address.png" style="float:left" width="15px" height="15px" align="middle" />
			<div style="margin-top:1px;width:calc(100% - 15px);float:left; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;"><font size="1"  id="curPosit" >山东省烟台市芝罘区</font></div>
		</div>
		<div style="width:93%;color:#aaaaaa;margin-left:10px" id="photoList">
			<img src='source/add.png'  height='80px' width='80px' onclick="addPhoto()"/>
		</div>
		<div style="background: #cccccc;width: 100%;height: 2px;margin-top: 2px"></div>
		<div style="width:93%;color:#444444;margin:10px;font-size: 18px;">
			公开
		</div>
	</div>
</div>
<div style="width:100%;height:35px;padding-top:10px;border-bottom: solid 1px #555555;visibility: visible;" id="imtitle" >
    <div style="color:black;font-size: 18px;width: calc(100% - 40px);float: left" align="center" >I'M&nbsp;朋友圈</div>
    <div onclick="showDialog()" style="line-height: 50px;color:black;font-size: 26px;float: left;vertical-align: middle;">
        <img alt="" src="source/senddyn.png" width="30px" height="30px"/>
    </div>
</div>
<div style="width: 100%;height:10px"></div>
<div style="width:100%;height:23px">
	<div style="width: 15%;height:10px;float:left" ></div>
	<div class="myTitle-left" align="center">最新</div>
	<div class="myTitle-right" align="center">最热</div>
	<div style="width: 15%;height:10px;float:left"></div>
</div>
<div style="width:100%;height:10px"></div>
<div style="width:100%;height:calc(100% - 85px)">
	<div style="width:55px;float:left;height:200px;margin-left:-55px;" >
		<img alt="" src="source/touxiang.png" width="55px" height="55px" class="myHeader" align="middle"/>
	</div>
	<div style="width: calc(100% - 65px);float:left;padding-left:6px">
         <div style="width:90%">
             <div style="width:40%;float:left" align="left">155****9597</div>
             <div  style="width:60%;float:left" align="right">1小时前</div>
         </div> 
         <div style="width:90%;height:40px;padding-top:10px;font-size: 15px">
         	天才出于勤奋！！！！
         </div>
         <div style="width:90%">
         	<img alt="" src="source/resource.png" width="100%"  />
         </div>
         <div style="width:90%;color:#aaaaaa;">
         	<img alt="" src="source/address.png" style="float:left" width="15px" height="15px" align="middle" />
         	<div style="margin-top:1px;width:calc(100% - 15px);float:left" ><font size="1" >山东省烟台市芝罘区</font></div>
         </div>
         <div style="width:90%;color:#aaaaaa;">
         <div style="width:55%;height:25px;float:left"></div>
         <img alt="" src="source/my_Collection.png" style="float:left" width="25px" height="25px" align="middle" />
         <div style="width:10px;height:25px;float:left"></div>
         <img alt="" src="source/my_Evaluation.png" style="float:left" width="25px" height="25px" align="middle" />
         <div style="width:10px;height:25px;float:left"></div>
         <img alt="" src="source/del.png" style="float:left" width="23px" height="23px" align="middle" />
         <div style="width:10px;height:25px;float:left"></div>
         </div>
	</div>
	<div style="width:100%;height:10px"></div>
	<div style="width:55px;float:left;height:200px;" >
		<img alt="" src="source/touxiang.png" width="55px" height="55px" class="myHeader" align="middle"/>
	</div>
	<div style="width: calc(100% - 65px);float:left;padding-left:6px">
         <div style="width:90%">
             <div style="width:40%;float:left" align="left">155****1234</div>
             <div  style="width:60%;float:left" align="right">5小时前</div>
         </div> 
         <div style="width:90%;height:40px;padding-top:10px;font-size: 15px">
         	新书《微信企业号开发完全自学手册》
         </div>
         <div style="width:90%">
         	<img alt="" src="source/book.png" width="100%" height="122px" />
         </div>
         <div style="width:90%;color:#aaaaaa;">
         	<img alt="" src="source/address.png" style="float:left" width="15px" height="15px" align="middle" />
         	<div style="margin-top:1px;width:calc(100% - 15px);float:left"><font size="1" >山东省烟台市芝罘区</font></div>
         </div>
         <div style="width:90%;color:#aaaaaa;">
         <div style="width:55%;height:25px;float:left"></div>
         <img alt="" src="source/my_Collection.png" style="float:left" width="25px" height="25px" align="middle" />
         <div style="width:10px;height:25px;float:left"></div>
         <img alt="" src="source/my_Evaluation.png" style="float:left" width="25px" height="25px" align="middle" />
         <div style="width:10px;height:25px;float:left"></div>
         <img alt="" src="source/del.png" style="float:left" width="23px" height="23px" align="middle" />
         <div style="width:10px;height:25px;float:left"></div>
         </div>
	</div>
</div>
</body>
<script>
	wx.config({
		debug : false, //开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : '${messageAppId}', //必填，企业号的唯一标识，此处填写企业号corpid
		timestamp : "${time}", //必填，生成签名的时间戳
		nonceStr : '${nonceStr}', // 必填，生成签名的随机串
		signature : '${str1}',// 必填，签名
		jsApiList : ['hideOptionMenu','getLocation','checkJsApi','chooseImage']// 必填，需要使用的JS接口列表
	});
	
	wx.ready(function(){
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.hideOptionMenu();
		locationAgain();//获取位置
	});
	//显示框
	function showDialog(){
		$("#dialog").css("visibility","visible");
		$("#imtitle").css("visibility","hidden");
	}
	var curPosition="";
	//重新定位位置
	function locationAgain(){
		wx.getLocation({
			type: 'gcj02', // 默认为wgs84的gps坐标，火星坐标，可传入'gcj02'
		    success: function (res) {
		       	var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		       	var longitude = res.longitude ; // 经度，浮点数，范围为180 ~ -180。
		        var speed = res.speed; // 速度，以米/每秒计
		        var accuracy = res.accuracy; // 位置精度
		        /**
		        //GPS坐标系转换成高德坐标系
		        var converGeo = AMap.convertFrom([longitude,latitude], 'gps',function(status,result){
		       		console.log(result);
			        //使用高德逆地址解析服务
			        var geocoder = new AMap.Geocoder({
			            radius: 1000,
			            extensions: "all"
			        });        
			        geocoder.getAddress([result.locations[0].lng,result.locations[0].lat], function(status, result) {
			            if (status === 'complete' && result.info === 'OK') {
			             var address = result.regeocode.formattedAddress; //返回地址描述
			                alert(address);
			            }
			        });    
		        });
		        **/
		        //使用高德逆地址解析服务
		        var geocoder = new AMap.Geocoder({
		            radius: 1000,
		            extensions: "all"
		        });        
		        geocoder.getAddress([longitude,latitude], function(status, result) {
		            if (status === 'complete' && result.info === 'OK') {
		             	curPosition = result.regeocode.formattedAddress; //返回地址描述
		             	console.log(curPosition);
		             	document.getElementById("curPosit").innerHTML=curPosition;
		             	console.log("--------"+$("#curPosit").html());
		            }
		        });   
		    }
		});
	}
	
	function addPhoto(){
		wx.chooseImage({
		    count: 1, // 默认9
		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		        alert(localIds);
				$("#photoList").prepend("<img src='"+localIds+"'  height='80px' width='80px' />")
		    }
		});
	}
</script>
</html>
