package myf.caption3.demo3_7;

import myf.caption3.demo3_4.WxUtil;
import net.sf.json.JSONObject;

/**
 * 根据标签id群发消息
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class SendMsgAllMsgByOpenID {
	public static void main(String[] args) {
//		//发送文本消息
//		String jsonContext_text="{"
//			   +"\"touser\":["
//			      +"\"oGzTi0tOakoHMX76VxKIB0GMcX8M\""
//			   +"],"
//			   +"\"text\":"
//			    +"{"
//			         +"\"content\":\"" +
//			         "群发Text消息，根据OpenID\r\n" +
//			         "==================" +
//			         "\r\n" +
//			         "根据用户OpenID群发Text消息！\r\n" +
//			         "总计消费：<a href='http://www.baidu.com' >$17.66</a>\r\n" +
//			         "消费用户：牟云飞\r\n" +
//			         "联系方式：15562579597\r\n" +
//			         "<a href='http://blog.csdn.net/myfmyfmyfmyf'>博客点击查看详情</a>\""
//			    +"},"
//			   +"\"msgtype\":\"text\""
//			+"}";
//		//调用接口群发消息，createPostMsg封装主动调用公用方法3.4节介绍
//		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=";
//		JSONObject result = WxUtil.createPostMsg(url, jsonContext_text);
//		System.out.println(result);
		
		//发送图文消息,touser
		String jsonContext_mpnews="{"
			 	+"\"touser\":["
			 		+"\"oGzTi0tOakoHMX76VxKIB0GMcX8M\","
			 		+"\"oGzTi0vDjCSXUC57LysuDVT4MJng\""
		      	+"],"
		      	+"\"mpnews\":"
			    +"{"
			         +"\"media_id\":\"TVwrM9MUyS-0ZusnXK0AfQCrIgA_Th65evCtZyclWrA\"" 
			    +"},"
			   +"\"msgtype\":\"mpnews\","
			   +"\"send_ignore_reprint\":1"
			+"}";
		//createPostMsg封装主动调用公用方法3.4节介绍
		String url_mapnew = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=";
		JSONObject result_mapnew = WxUtil.createPostMsg(url_mapnew, jsonContext_mpnews);
		System.out.println(result_mapnew);
	}
}
