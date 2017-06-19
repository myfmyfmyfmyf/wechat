/**
 * jQuery popWindow plugin
 * Version 1.0
 * 2011-10-12
*/
;(function($) {
var topWin = null;
topWin = typeof(isCrossDomain) == "undefined" ? window.top : isCrossDomain.curTopWindowRef;
/**
 * 创建一个模态窗口
 * @public
 * @taglib popWindow
 * @method $.showModalDialog
 * @param  url或{url:url, param:param} 当参数为url时，窗口传递方式为get；当参数为对象时则url属性为url地址，param为参数对象
 * @param  title　窗口标题
 * @param  callback 回调函数
 * @param  arguments　回调参数
 * @param  width 窗口宽度
 * @param  height　窗口高度
 * @param  button 按钮类型(0:无按钮,1:确定,2:取消,3：确定取消)
 * @param  showx　窗口横坐标
 * @param  showy　窗口纵坐标
 * 
 */
$.showModalDialog = function(urlPara, titlePara, callbackNamePara, argumentsPara, widthPara, heightPara, buttonPara, showx, showy){
//	var url,param;
//	if(urlPara instanceof Object){
//		url = isCrossDomain.isCrossDomainFlag?isCrossDomainUrl(urlPara.url):urlPara.url;
//		param = urlPara.param;
//	}else{
//		url = isCrossDomain.isCrossDomainFlag?isCrossDomainUrl(urlPara):urlPara;
//		param = null;
//	}
	$.popWindow({
//		url : url,
		url : urlPara,
//		param:param,
		title :titlePara,
		modal:true,
		callback:callbackNamePara,
		arguments:argumentsPara,
		button:buttonPara,
		css : {
			width : widthPara,
			height : heightPara,
			top : showy,
			left : showx
		}
	});
}
/**
 * 创建一个模态窗口(自定义参数对象)
 * @public
 * @taglib popWindow
 * @method $.popWindow
 * @param  opts {url:访问地址, title:窗口标题, callback:回调函数, arguments:回调参数, button:0-无按钮;1-确定;2-取消;3-确定取消,operator:0-无按钮;1-关闭;2-全部按钮, resize:是否可改变大小, move:是否可以移位置, css:{width:宽度, height:高度, top:纵坐标, left:横坐标}} 
 * 
 */
$.popWindow = function(opts) { install(opts);};
$.unpopWindow = function(divId) {remove(divId);};
$.submitpopWindow = function(divId) { submit(divId);};
$.winClose = function(divId) { winClose(divId);};
/**
 * 创建一个提示窗口(alert)
 * @public
 * @taglib popWindow
 * @method $.alert
 * @param title 窗口标题
 * @param message 提示信息
 * @param callback 回调函数
 * @param width 窗口宽度
 * @param height 窗口高度
 * 
 */
$.alert = function(t,msg,func,w,h) { Alert(t,msg,func,w,h,arguments.length); };
/**
 * 创建一个提示窗口(指定时间自关闭)
 * @public
 * @taglib popWindow
 * @method $.tips
 * @param title 窗口标题
 * @param message 提示信息
 * @param time 持续时间
 * @param width 窗口宽度
 * @param height 窗口高度
 * 
 */
$.tips = function(t,msg,time,w,h){Tips(t,msg,time,w,h)};
/**
 * 创建一个选择窗口
 * @public
 * @taglib popWindow
 * @method $.confirm
 * @param title 窗口标题
 * @param message 提示信息
 * @param submitcallback 确认回调函数
 * @param cancelcallback 取消回调函数
 * @param width 窗口宽度
 * @param height 窗口高度
 * 
 */
$.confirm = function(t,msg,func1,func2,w,h) { Confirm(t,msg,func1,func2,w,h); };
/**
 * 关闭当前弹窗
 * @public
 * @taglib popWindow
 * @method $.close
 * @param returnValue 返回值
 * 
 */
$.close = function(str) {close(str)};
$.cancel = function() {cancel()};
$._Array = [];
$.popWindow.version = "1.0"; 
$.popWindow.defaults = {
		url : 'javascript:void(0);',			// 指向的url地址
		callback : null,					// 容器内回调函数名称,默认为空,(注:请写入外部窗口函数体中,并只需写入名称即可)
		button : '3', 						// 已选按钮，1,确定；2,取消；3,确定，取消； 0,无按钮	默认值为4(考虑不需回调的普通窗口)	
	   	arguments : null,						// 关于参数传递的相关设置
		css : {
			padding : 0,
			margin : 0,
			width : null,
			height : null,
			top : 0,
			left : 0,
			textAlign : 'center',
			color : '#000',
			border : '3px solid #aaa',
			backgroundColor : '#008aff',
			cursor : 'wait'
		},
		baseZ : 2001,
		date:false,
		resize:true,
		move:true
};
function isCrossDomainUrl(urlPara){
	if(urlPara.indexOf("?")!=-1){
		urlPara+="&isCrossDomain=true";
	}else{
		urlPara+="?isCrossDomain=true"
	}
	if(isCrossDomain.getUrlVar("parentIndex") != null){
		urlPara=isCrossDomain.getUrl("parentIndex", Number(isCrossDomain.getUrlVar("parentIndex")));
	}else{
		urlPara+="&parentIndex=1";
	}
	return urlPara;
}
function install(opts) {
	if(opts.url instanceof Object){
		opts.param = opts.url.param;
		opts.url = isCrossDomain.isCrossDomainFlag?isCrossDomainUrl(opts.url.url):opts.url.url;
	}else{
		opts.url = isCrossDomain.isCrossDomainFlag?isCrossDomainUrl(opts.url):opts.url;
		opts.param = null;
	}
    var url = opts && opts.url !== undefined ? opts.url : undefined;
    opts = $.extend({}, $.popWindow.defaults, opts || {});
    var css = $.extend({}, $.popWindow.defaults.css, opts.css || {});
    if(typeof (opts.css.top) != "undefined"){
    	opts.date = true;	
    }
    url = url === undefined ? opts.url : url;
    var f="";
    if(url.indexOf($$pageContextPath)==0){
    	f="AP";
    }else if(url.substring(0,1) == "/"){
    	f="addAP"
    }else if(url.substring(0,4).toLowerCase() == "http" ){
    	f="CUSTOM";
    }else{
    	f="RP";
    }
    switch (f) {
        case 'AP':
            break;
    	case 'CUSTOM':
    		break;
    	case 'RP':
    		url = location.href.substring(0, location.href.lastIndexOf('/') + 1) + url;
    		break;
    	case "addAP":
    		url=$$pageContextPath+url.substring(1,url.length);
    		break;
    }
    
    if(opts.param != null){
	    topWin.popWindowUrl = {url:url,param:opts.param};
		topWin.getPopWindowUrl = function(){
			return topWin.popWindowUrl;
		}
    }
	//z-index
    var z = opts.baseZ;
		opts.id = new Date().getTime();
		if(opts.callback){
			window.callback = opts.callback;
		}else{
			window.callback = null;
		}
		if(opts.arguments){
			window.arguments = opts.arguments;
		}		
	function getcustomBtn(opts, onclick, value){
		var btntype = {},btnimg = {}, okOnclick = opts.content ? "$.winClose("+opts.id+")" : "$.submitpopWindow("+opts.id+")";
		function onclickType(opts,onclick){
			btntype.OKbtnhtml	 = "<button class='popWindowBtnStyle' id='Ok_"+opts.id+"' optsid="+opts.id+" onclick=\""+okOnclick+"\" type='button'>";
			btntype.Cancelbtnhtml= "<button class='popWindowBtnStyle' id='Cancel_"+opts.id+"' optsid="+opts.id+" onclick=\"$.unpopWindow("+opts.id+");\" type='button'>";
			return btntype[onclick];
		}
		function getBtnImg(opts,onclick){
			btnimg.OKbtnhtml = 'ok.gif';
			btnimg.Cancelbtnhtml = 'cancel.gif';
			return btnimg[onclick];
		}
		var btn =   onclickType(opts,onclick)
					+   "<table id='button_1_buttonTable' width='100%' height='100%' class='toolBarButton'><tbody><tr>"
					+	"<td align='right'><img src='"+$$pageContextPath+"images/button/"+getBtnImg(opts,onclick)+"' style='width:14px;height:14px;'></td>"
					+	"<td style='vertical-align: bottom;' align='center'>"+value+"</td></tr></tbody></table>"
					+ "</button>";
		return btn;
	}
    var OKbtnhtml   =	getcustomBtn(opts,'OKbtnhtml','确 定');
	var Cancelbtnhtml = getcustomBtn(opts,'Cancelbtnhtml','取 消');
	var AllbtnhtmlCB  = OKbtnhtml + Cancelbtnhtml;
	function showChooseBtn(opts){
		var btnList =[null,OKbtnhtml,Cancelbtnhtml,AllbtnhtmlCB];
		opts.button = (opts.button > 3 || opts.button < 0)?3:opts.button;
		return btnList[opts.button];
	}
	function getOperator(opts){
		function _getOperator(operator){
			if(operator){
				operator = operator.split(" ");
				var operators = "";
					section =  '<a role="button" class="k-popwin-window-action k-popwin-link" style="display:{display}"><span onclick="{click}" role="{operator}" class="k-popwin-icon k-popwin-i-{operator}"></span></a>';
				for(var i = 0; i < operator.length; i++){
					var display = operator[i] == "restore" ? "none" : "inline-block";
					var click = operator[i] == "close" ? "$.unpopWindow("+opts.id+")" : "window.resize()";
					operators += section.replace(new RegExp("{operator}","g"), operator[i]);
					operators = operators.replace(new RegExp("{display}"), display);
					operators = operators.replace(new RegExp("{click}"), click);
				}
				return operators;
			}else{
				return "";
			}
		}
		if(opts.operator == 1){
			return _getOperator("close");
		}else if(opts.operator == 0){
			return _getOperator();
		}else{
			return _getOperator("minimize maximize restore close");
		}
	}
	var iframeUrl = opts.param == null ? url : $$pageContextPath+"framework/commonjsp/popWin.jsp";
	window.blockDiv = $("#_DialogBGDiv", topWin.document);
//	if (opts.modal) {
		blockDiv.show();
		blockDiv.css('z-index',parseInt(blockDiv.css('z-index')) + 2);
		z = parseInt(blockDiv.css('z-index'))+1;
//	}
	if(opts.date){
		opts.resize = false;
		opts.operator = 1;
//		if(opts.modal){
			if(blockDiv.width() == screen.width){
				if($.browser.webkit){
					css.left += 27;
					css.top += 21;
					css.width += 2;
				}
				if($.browser.mozilla){
					css.left += 4;
					css.top += 26;
					css.width += 2;
					if(topWin.$.winCount != undefined && topWin.$.winCount != 0){
						var _window = $(topWin.document).find(".k-popwin-window:last")[0];
						css.left += _window.offsetLeft;
						css.top += _window.offsetTop;
					}
				}
				if($.browser.msie){
					css.left += 28;
					css.top += 10;
					css.height += 5;
					css.width += 5;
				}
			}
			if($.browser.msie){
				css.height += 5;
				css.width += 5;
			}
			
			var lowerHeigthNotEnough = blockDiv.height() - css.top < css.height + 30;
			if(lowerHeigthNotEnough){
				css.top = css.top - css.height - 62;
			}
			var rightSideWidthNotEnough = css.left + css.width > blockDiv.width();
			if(rightSideWidthNotEnough){
				css.left = blockDiv.width() - css.width;
			}
			var upperHeightNotEnough = css.top > 0?false:true;
			if(lowerHeigthNotEnough && upperHeightNotEnough){
				css.top = 0;
			}
//		}
  	}else{
	    if(!css.height){
			css.height = css.width/2;
		}
	  	var size = getWindowSize();
  		if($.browser.msie){
			css.height = parseInt(css.height)+28;
		}
	  	css.width = css.width >= size.w ? size.w : css.width;
	  	css.height = css.height >= size.h ? size.h : css.height;
		css.top = (size.h - css.height - 30) / 2;
		css.top = css.top < 0 ? 0  : css.top;
		css.left = (size.w - css.width) / 2;
		
  	}
  	
	var popWin = popWinRender(opts, css, z);
	var iHeight = showChooseBtn(opts) == null ? "100%" : Number(css.height-36);
	if(!opts.content){
		var _iframe = iframeUrl == "http://" ? '<div class="popWindowAlert">'+opts.msg+'</div>' : '<iframe optsid="'+opts.id+'" height="'+iHeight+'" width="100%" scroll="no" src="'+iframeUrl+'" id="_DialogFrame_'+opts.id+'" name="_DialogFrame_'+opts.id+'" allowTransparency="true" frameborder="0" style="overflow: auto;background-color: #transparent; border:none;"></iframe>';
	}else{
		var _iframe = opts.content;
	}
	popWin = popWin.replace(new RegExp("{iframe}","g"),_iframe);
	popWin = popWin.replace(new RegExp("{operator}","g"),getOperator(opts));
	popWin = popWin.replace(new RegExp("{buttonGroup}","g"),showChooseBtn(opts) == null ? "" : '<tr><td class="k-popwin-buttongroup-td">'+showChooseBtn(opts))+'</td></tr>';
	window.optsId = opts.id;
	window.lyr3 = $(popWin);
	topWin.document.body.appendChild(lyr3[0]);
	topWin.$._Array.push(getPath());
	if(topWin.$.winCount == null){
		topWin.$.winCount = 1;
	}else{
		topWin.$.winCount++;
	}
  	setWinEvent(z, opts);
  	return opts.id;
};
function popWinRender(opts, css, z){
	var popWin = 
		'<div id="_DialogDiv_'+opts.id+'" path="'+getPath()+'" class="k-popwin-widget k-popwin-window" data-role="draggable" style="z-index:'+z+';position:absolute;top:'+css.top+'px;left:'+css.left+';width:'+css.width+'px;height:'+css.height+'px">'+
			'<div class="k-popwin-window-titlebar k-popwin-header" id="_DialogHead_'+opts.id+'">'+
				'<table class="k-popwin-window-header-table" cellspacing=0><tr>' +
					'<td class="k-popwin-window-l"></td>' +
					'<td class="k-popwin-window-m k-popwin-move"></td>' +
					'<td class="k-popwin-window-r"></td>' +
				'</tr></table>'+
				'<span class="k-popwin-window-title k-popwin-move">'+opts.title+'</span>'+
				'<div class="k-popwin-window-actions">'+
					'{operator}'+
				'</div>'+
			'</div>'+
			'<div class="k-popwin-resize-handle k-popwin-resize-e" action="e" style="display: block;"></div>'+
			'<div class="k-popwin-resize-handle k-popwin-resize-s" action="s" style="display: block;"></div>'+
			'<div class="k-popwin-resize-handle k-popwin-resize-se" action="se" style="display: block;"></div>'+
			'<div id="_DialogContent_'+opts.id+'" data-role="window" class="k-popwin-window-content k-popwin-content" tabindex="0" role="dialog" aria-labelledby="window_wnd_title" style="display: block; overflow: hidden;">'+
			'<table cellspacing=0 cellpadding=0 class="k-popwin-table-content"><tr><td>'+
			'{iframe}'+
			'</tr></td>'+
			'{buttonGroup}'+
			'</table>'+
			'</div>'+
	   '</div>';
   return popWin;
}
function isLtIe9(){
	return $msie && ($.browser.version == "7.0" || $.browser.version == "8.0");
}
function getFxTime(){
	if(isLtIe9()){return 0;}else{return 200;}
}
function moveLimit(l, t, rl, rt){
	var size = getWindowSize(), w = size.w, h = size.h;
//	if(rl > w){l = w - (rl - l);}
//	if(l <= 0){l = 0;}
//	if(rt > h){t = h - (rt - t);}
	if(rl < 30){l = 30 - (rl - l)};
	if(l > (w - 30)){l = w - 30};
	if(t > (h - 8)){t = h - 8};
	if(t < 0){t = 0};
	t += 6;
	if($msie){l += 9;}else{l += 1;}
	return {l:l, t:t};
}
function getWindowSize(){
	var win = isCrossDomain.curTopWindowRef;
	return {
		w : win.innerWidth || (win.document.documentElement && win.document.documentElement.scrollWidth),
		h : (win.innerHeight || (win.document.documentElement && win.document.documentElement.scrollHeight))-30
	}
}
function setWinEvent(z, opts){
	var test = $("#_DialogDiv_"+optsId, topWin.document),
	proxy = $("#moveProxy", topWin.document).css({zIndex:z+2}),
	move = $("#_DialogHead_"+optsId, topWin.document),
	content = $("#_DialogContent_"+optsId, topWin.document),
	mask = $("#moveMask", topWin.document).css({zIndex:z+1}),
	min = move.find(".k-popwin-i-minimize"),
	max = move.find(".k-popwin-i-maximize"),
	restore = move.find(".k-popwin-i-restore").parent(),
	body = $(topWin.document),
	operator = move.find(".k-popwin-window-actions"),
	titleH = 28;
	resizeGrips = test.find(".k-popwin-resize-handle");
	$.data(test, "minH", test.height() + titleH);
	$.data(test, "minW", test.width());
	function disableSelection(){
		test.disableSelection();proxy.disableSelection();move.disableSelection();content.disableSelection();body.disableSelection();mask.disableSelection();
	}
	function getDisplacement(triggerEvt, _evt, action, offset){
		var x = _evt.clientX - triggerEvt.clientX,
			y = _evt.clientY - triggerEvt.clientY,
			action = actionsMap(x, y)[action];
		return {
			h : Number(test.offset().top+$.data(test,"h")+action.y) > getWindowSize().h ? getWindowSize().h - test.offset().top : Number($.data(test,"h")+action.y),
			w : Number(test.offset().left+$.data(test,"w")+action.x) > getWindowSize().w ? getWindowSize().w - test.offset().left : Number($.data(test,"w")+action.x)
		}
	}
	function actionsMap(x, y){
		return {
			n : {x:0, y:-y},s : {x:0, y:y},w : {x:-x, y:0},e : {x:x, y:0},nw : {x:-x, y:-y},ne : {x:x, y:-y},sw : {x:-x, y:y},se : {x:x, y:y}
		}
	}
	/***btnEvt***/
	var btns = test.find(".k-popwin-window-actions .k-popwin-icon");
	btns.mousedown(function(evt){
		evt.stopPropagation();
		roleHandler[$(evt.target).attr("role")]();
	})
	
	var roleHandler = {
		state : null,
		minimize : function(){
			roleHandler.state = "min";
			min.hide();
			max.hide();
			restore.show();
			$.data(test,"h",test.height());
			resizeGrips.hide();
			content.slideUp(getFxTime());
			test.height(0);
		},
		maximize : function(){
			roleHandler.state = "max";
			min.hide();
			max.hide();
			restore.show();
			$.data(test,"h",test.height());
			$.data(test,"w",test.width());
			$.data(test,"l",test.offset().left);
			$.data(test,"t",test.offset().top);
			resizeGrips.hide();
			test.css({width:getWindowSize().w, height:getWindowSize().h, top:0, left:0});
		},
		restore : function(){
			if(roleHandler.state == "min"){
				min.show();
				max.show();
				restore.hide();
				resizeGrips.show();
				content.slideDown(0, function(){test.height($.data(test,"h"));});
			}
			if(roleHandler.state == "max"){
				min.show();
				max.show();
				restore.hide();
				resizeGrips.show();
				test.css({width:$.data(test,"w"),height:($msie ? ($.data(test,"h")+titleH) : $.data(test,"h")),top:$.data(test,"t"),left:$.data(test,"l")});
			}
		},
//		close : /*$.unpopWindow*/ function(){}
		close : function(){}
	}
	if(!opts.move){
		return;
	}
	/***move***/
	body.bind("moveTrigger",function(event, _evt, firefoxEvt, id){
		var offset = {x:firefoxEvt == undefined ? _evt.offsetX : firefoxEvt.layerX, y : firefoxEvt == undefined ? _evt.offsetY : firefoxEvt.layerY},
			w = test.width(),
			h = test.height();
		body.mousemove(function(evt){
			var l = evt.clientX-offset.x-16, t = evt.clientY-offset.y-6, rl = l + w, rt = t + h, ios = moveLimit(l,t,rl,rt);
			proxy.css({left:ios.l, top:ios.t});
		}).mouseup(function(){
			body.data("move_div").css({top:proxy.css("top"), left:proxy.css("left")})
			proxy.hide();
			mask.hide();
			body.unbind("mousemove mouseup");
		})
	})
	move.mousedown(function(evt){
		if(!$(evt.target).hasClass("k-popwin-move"))
			return;
		disableSelection();
		proxy.css({top:test.css("top"), left:test.css("left"), width:test.width(), height:test.height()+titleH});
		proxy.show();
		mask.show();
		body.data("move_div",test);
		body.trigger("moveTrigger", [evt, $firefox ? getEvent() : undefined]);	
	})
	/***resize***/
	if(!opts.resize){
		resizeGrips.hide();
		return;
	}
	body.bind("resizeTrigger",function(evt, triggerEvt, action, firefoxEvt){
		var offset = {x:firefoxEvt == undefined ? triggerEvt.offsetX : firefoxEvt.layerX, y : firefoxEvt == undefined ? triggerEvt.offsetY : firefoxEvt.layerY};
		body.mousemove(function(_evt){
			var d = getDisplacement(triggerEvt, _evt, action, offset);
			proxy.css({width:d.w < $.data(test, "minW") ? $.data(test, "minW") : d.w, height:d.h < $.data(test, "minH") ? $.data(test, "minH") : d.h});
		}).mouseup(function(){
			test.css({height:proxy.height() - ($msie ? 0 : titleH), width:proxy.width()})
			proxy.hide();
			mask.hide();
			body.unbind("mousemove mouseup");
		})
	})
	resizeGrips.mousedown(function(evt){
		disableSelection();
		proxy.css({top:test.css("top"), left:test.css("left"), width:test.width(), height:test.height()+titleH});
		proxy.show();
		mask.show();
		$.data(test,"w",test.width());
		$.data(test,"h",test.height()+titleH);
		body.trigger("resizeTrigger", [evt, $(this).attr("action"), $firefox ? getEvent() : undefined]);	
	})
}
function getPath(){
	var path=[], isTop=false, win=window;
	while(!isTop){
		var id = win.frameElement && win.frameElement.id;
		if(id == ""){
			id = 'frameElementID';
			win.frameElement.id = id;
		}
		path.push(id);
		win = win.parent;
		if(win == win.top)
			isTop=true;
	}
	return path.join(",");
}
function getFrame(arr){
	arr = arr.split(",");
	var winRef=topWin,win=null;
	if(arr[0] != null && arr[0] != "")
		while(arr.length!=0){
			win=arr.pop();
			var w = winRef.document.getElementById(win);
			if(w != null)
				winRef = w.contentWindow;
			else
				winRef = window;			
		}
	return winRef;
}

function getFrameId(winRef){
	return winRef.frameElement.id;
}
function submit(divId) {
    var win = getFrame($("#_DialogDiv_"+divId).attr("path"));
    var frame = window.frames["_DialogFrame_" +divId];
	var rv = frame.returnValue;
	if(win.callback != null){
    	if (typeof rv == 'function') {
			var value = rv();
			if(win.arguments != null){
				try{
					win.callback(value,win.arguments);
				}catch(ex){
				}
			}else{
				try{
					win.callback(value);
				}catch(ex){
				}
			}
		}
	}
	block(win);
	reset(divId);
};
function close(value){
	var winRef = window;
	while($(winRef.frameElement).attr("optsid") == null){
		winRef = winRef.parent;
	}
	var divId = $(winRef.frameElement).attr("optsid");
	var win = getFrame($("#_DialogDiv_"+divId, $(topWin.document)).attr("path"));
	if(value != undefined)
		if(win.callback){
			if(win.arguments != null){
				try{
					win.callback(value,win.arguments);
				}catch(ex){
				}
			}else{
				try{
					win.callback(value);
				}catch(ex){
				}
			}
		}
	block(win);
	reset(divId);
}
function remove(divId) {
    var win = getFrame($("#_DialogDiv_"+divId).attr("path"));
    var frame = window.frames["_DialogFrame_" +divId];
    if(win.callback != null)
		if(win.callback.prototype.constructor.length == 3){
	    	var rv = frame.returnValue;
	    	if(win.callback != null){
		    	if (typeof rv == 'function') {
					var value = rv();
					if(win.arguments != null){
						try{
							win.callback(value,win.arguments,0);
						}catch(ex){
						}
					}else{
						try{
							win.callback(value,'',0);
						}catch(ex){
						}
					}
				}else{
					if(win.arguments != null){
						try{
							win.callback('',win.arguments,0);
						}catch(ex){
						}
					}else{
						try{
							win.callback('','',0);
						}catch(ex){
						}
					}
				}
	    	}
		}
	block(win);
	reset(divId);
};
function winClose(divId){
	var win = getFrame($("#_DialogDiv_"+divId).attr("path"));
	block(win);
	reset(divId);
}
function tipsClose(divId){
	var win = getFrame($("#_DialogDiv_"+divId, $(topWin.document)).attr("path"));
	if(win.callback != null){
		win.callback();
	}
	block(win);
	reset(divId);
}
function cancel(){
	var divId = $(window.frameElement).attr("optsid");
	var win = getFrame($("#_DialogDiv_"+divId, $(topWin.document)).attr("path"));
	if(win.callback){
			try{
				win.callback();
			}catch(ex){
		    }
	}
	block(win);
	reset(divId);
}
function reset(win,close,time) {
	var div = null,frame=null,_win=window.document,winRef = window.parent;
	if(close){
		for(var i = 0; i < time; i++){
			winRef = winRef.parent;
		}
		div = winRef.document.getElementById("_DialogDiv_"+win);
		_win = winRef.document;
	}else{
		div = topWin.document.getElementById("_DialogDiv_"+win);
	}
	frame = $(div).find("iframe")[0];
	if(frame){
		frame.src="";
		frame.outerHTML="";
	}
	div.outerHTML = "";
};
function block(win){
	if(topWin.$.winCount > 1){
		win.blockDiv.css('z-index',parseInt(win.blockDiv.css('z-index')) - 2);
	}else{
		win.blockDiv.hide();
	}
	topWin.$.winCount--;
}
function Alert(t,msg,func,w,h,l){
	if(l == 1){
		msg = t;
		t ="提示信息";
	}
	if(w == undefined)w=310;
	if(h == undefined)h=120;
	var content = '<table style="height: 100%;width: 100%;"><tbody><tr><td style="width: 50px; text-align:center;"><img src="'+$$pageContextPath+'images/popWindow/icon_alert.gif" width="34" height="34"></td><td><div class="k-popwin-window-div">'+msg+'</div></td></tr></tbody></table>	';
	var opts = {resize:false,operator:0,button:1,title:t,type:'warning',css:{width:w,height:h},content:content,url:$$pageContextPath+"framework/commonjsp/alert.jsp?msg="+msg+"",modal:true};
	install(opts);
	lyr3.find("button[id^='Ok_']").bind("click", function() {if(func){func();}}) 
}
function Confirm(t,msg,func1,func2,w,h){
	var content = '<table style="height: 100%;width: 100%;"><tbody><tr><td style="width: 50px; text-align:center;"><img src="'+$$pageContextPath+'images/popWindow/icon_query.gif" width="34" height="34"></td><td><div class="k-popwin-window-div">'+msg+'</div></td></tr></tbody></table>	';
	var opts = {resize:false,operator:0,button:3,title:t,type:'confirm',css:{width:w?w:300,height:h?h:120},content:content,url:$$pageContextPath+"framework/commonjsp/popconfirm.jsp?msg="+msg+"",modal:true};
	install(opts);
	lyr3.find("button[id^='Ok_']").bind("click", function() {if(func1){if(func1.callback){func1.callback(func1.param);}else{func1()}}});
	lyr3.find("button[id^='Cancel_']").bind("click", function() {if(func2){if(func2.callback){func2.callback(func2.param);}else{func2()}}});
}
function Tips(t,msg,time,callback,w,h){
	var opts = {/*move:false,*/resize:false,id:new Date().getTime(),callback:(callback?callback:null),operator:0,button:0,title:t,msg:msg,type:'warning',url:"http://",css:{width:w?w:280,height:h?h:120},modal:true};
	var id = install(opts);
	setTimeout(function(){tipsClose(id)}, time);
}
})(jQuery);
