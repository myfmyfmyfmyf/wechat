<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<html>
<head>
<title>����Ȧ</title>
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
		        ȡ��
		    </div>
		    <div style="color:black;font-size: 18px;width: calc(100% - 80px);float: left" align="center" >I'M&nbsp;����Ȧ</div>
		    <div onclick="publishTrack()" style="line-height: 50px;color:black;font-size: 13px;float: left;vertical-align: middle;">
		       	 ����
		    </div>
		</div>
		<textarea style="width:93%;height:80px;margin-left:10px" placeholder='˵��ʲô��...' >
		</textarea>
		<div style="width:93%;color:#555555;margin:10px" align="center">
			<img alt="" src="source/address.png" style="float:left" width="15px" height="15px" align="middle" />
			<div style="margin-top:1px;width:calc(100% - 15px);float:left; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;"><font size="1"  id="curPosit" >ɽ��ʡ��̨��֥���</font></div>
		</div>
		<div style="width:93%;color:#aaaaaa;margin-left:10px" id="photoList">
			<img src='source/add.png'  height='80px' width='80px' onclick="addPhoto()"/>
		</div>
		<div style="background: #cccccc;width: 100%;height: 2px;margin-top: 2px"></div>
		<div style="width:93%;color:#444444;margin:10px;font-size: 18px;">
			����
		</div>
	</div>
</div>
<div style="width:100%;height:35px;padding-top:10px;border-bottom: solid 1px #555555;visibility: visible;" id="imtitle" >
    <div style="color:black;font-size: 18px;width: calc(100% - 40px);float: left" align="center" >I'M&nbsp;����Ȧ</div>
    <div onclick="showDialog()" style="line-height: 50px;color:black;font-size: 26px;float: left;vertical-align: middle;">
        <img alt="" src="source/senddyn.png" width="30px" height="30px"/>
    </div>
</div>
<div style="width: 100%;height:10px"></div>
<div style="width:100%;height:23px">
	<div style="width: 15%;height:10px;float:left" ></div>
	<div class="myTitle-left" align="center">����</div>
	<div class="myTitle-right" align="center">����</div>
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
             <div  style="width:60%;float:left" align="right">1Сʱǰ</div>
         </div> 
         <div style="width:90%;height:40px;padding-top:10px;font-size: 15px">
         	��ų����ڷܣ�������
         </div>
         <div style="width:90%">
         	<img alt="" src="source/resource.png" width="100%"  />
         </div>
         <div style="width:90%;color:#aaaaaa;">
         	<img alt="" src="source/address.png" style="float:left" width="15px" height="15px" align="middle" />
         	<div style="margin-top:1px;width:calc(100% - 15px);float:left" ><font size="1" >ɽ��ʡ��̨��֥���</font></div>
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
             <div  style="width:60%;float:left" align="right">5Сʱǰ</div>
         </div> 
         <div style="width:90%;height:40px;padding-top:10px;font-size: 15px">
         	���顶΢����ҵ�ſ�����ȫ��ѧ�ֲᡷ
         </div>
         <div style="width:90%">
         	<img alt="" src="source/book.png" width="100%" height="122px" />
         </div>
         <div style="width:90%;color:#aaaaaa;">
         	<img alt="" src="source/address.png" style="float:left" width="15px" height="15px" align="middle" />
         	<div style="margin-top:1px;width:calc(100% - 15px);float:left"><font size="1" >ɽ��ʡ��̨��֥���</font></div>
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
		debug : false, //��������ģʽ,���õ�����api�ķ���ֵ���ڿͻ���alert��������Ҫ�鿴����Ĳ�����������pc�˴򿪣�������Ϣ��ͨ��log���������pc��ʱ�Ż��ӡ��
		appId : '${messageAppId}', //�����ҵ�ŵ�Ψһ��ʶ���˴���д��ҵ��corpid
		timestamp : "${time}", //�������ǩ����ʱ���
		nonceStr : '${nonceStr}', // �������ǩ���������
		signature : '${str1}',// ���ǩ��
		jsApiList : ['hideOptionMenu','getLocation','checkJsApi','chooseImage']// �����Ҫʹ�õ�JS�ӿ��б�
	});
	
	wx.ready(function(){
		// config��Ϣ��֤���ִ��ready���������нӿڵ��ö�������config�ӿڻ�ý��֮��config��һ���ͻ��˵��첽���������������Ҫ��ҳ�����ʱ�͵�����ؽӿڣ��������ؽӿڷ���ready�����е�����ȷ����ȷִ�С������û�����ʱ�ŵ��õĽӿڣ������ֱ�ӵ��ã�����Ҫ����ready�����С�
		wx.hideOptionMenu();
		locationAgain();//��ȡλ��
	});
	//��ʾ��
	function showDialog(){
		$("#dialog").css("visibility","visible");
		$("#imtitle").css("visibility","hidden");
	}
	var curPosition="";
	//���¶�λλ��
	function locationAgain(){
		wx.getLocation({
			type: 'gcj02', // Ĭ��Ϊwgs84��gps���꣬�������꣬�ɴ���'gcj02'
		    success: function (res) {
		       	var latitude = res.latitude; // γ�ȣ�����������ΧΪ90 ~ -90
		       	var longitude = res.longitude ; // ���ȣ�����������ΧΪ180 ~ -180��
		        var speed = res.speed; // �ٶȣ�����/ÿ���
		        var accuracy = res.accuracy; // λ�þ���
		        /**
		        //GPS����ϵת���ɸߵ�����ϵ
		        var converGeo = AMap.convertFrom([longitude,latitude], 'gps',function(status,result){
		       		console.log(result);
			        //ʹ�øߵ����ַ��������
			        var geocoder = new AMap.Geocoder({
			            radius: 1000,
			            extensions: "all"
			        });        
			        geocoder.getAddress([result.locations[0].lng,result.locations[0].lat], function(status, result) {
			            if (status === 'complete' && result.info === 'OK') {
			             var address = result.regeocode.formattedAddress; //���ص�ַ����
			                alert(address);
			            }
			        });    
		        });
		        **/
		        //ʹ�øߵ����ַ��������
		        var geocoder = new AMap.Geocoder({
		            radius: 1000,
		            extensions: "all"
		        });        
		        geocoder.getAddress([longitude,latitude], function(status, result) {
		            if (status === 'complete' && result.info === 'OK') {
		             	curPosition = result.regeocode.formattedAddress; //���ص�ַ����
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
		    count: 1, // Ĭ��9
		    sizeType: ['original', 'compressed'], // ����ָ����ԭͼ����ѹ��ͼ��Ĭ�϶��߶���
		    sourceType: ['album', 'camera'], // ����ָ����Դ����ỹ�������Ĭ�϶��߶���
		    success: function (res) {
		        var localIds = res.localIds; // ����ѡ����Ƭ�ı���ID�б�localId������Ϊimg��ǩ��src������ʾͼƬ
		        alert(localIds);
				$("#photoList").prepend("<img src='"+localIds+"'  height='80px' width='80px' />")
		    }
		});
	}
</script>
</html>
