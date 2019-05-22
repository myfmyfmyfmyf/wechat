package myf.caption3.demo3_8;

import myf.caption3.demo3_4.WxUtil;
import net.sf.json.JSONObject;

/**
 * 发送模板消息
 * “购买成功通知”模板
 * @author muyunfei
 * 
 * <p>Modification History:</p> 
 * <p>Date       Author      Description</p>
 * <p>------------------------------------------------------------------</p>
 * <p>Jun 16, 2017           牟云飞       		 新建</p>
 */
public class SendModelMsg {
	public static void main(String[] args) {
		JSONObject result = SendModelMsg.sendModelMsg();
		System.out.println(result);
	}
	
	/**
	 * 发送“购买成功通知”模板消息
	 * @return
	 */
	public static JSONObject sendModelMsg(){
		//接收人的openid
		String openID = "oGzTi0tOakoHMX76VxKIB0GMcX8M";
		//模板消息ID，不是模板编号
		String tempId="_oZcXMgn-Bi0Xv41EfZs3QVhuDMt8L-toCRZEQ3vxug";
		//点击消息跳转链接
		String toUrl="http://blog.csdn.net/myfmyfmyfmyf?viewmode=contents";
		String first="恭喜你购买成功！";
		String product="《微信企业号开发完全自学手册》";
		String price="59.8元";
		String time="2017年06月16日";
		String remark="欢迎再次购买！";
		
		//消息json格式
  		String jsonContext="{"
           +"\"touser\":\""+openID+"\","
           +"\"template_id\":\""+tempId+"\","
           +"\"url\":\""+toUrl+"\","            
           +"\"data\":{"
                   +"\"first\": {"
                       +"\"value\":\""+first+" \","
                       +"\"color\":\"#000000\""
                       +"},"
                   +"\"product\":{"
                       +"\"value\":\""+product+"\","
                       +"\"color\":\"#000000\""
                       +"},"
	               +"\"price\":{"
	                   +"\"value\":\""+price+"\","
	                   +"\"color\":\"#FF0000\""
                   +"},"
                   +"\"time\": {"
                       +"\"value\":\""+time+"\","
                       +"\"color\":\"#000000\""
                       +"},"
                   +"\"remark\":{"
                       +"\"value\":\""+remark+"\","
                       +"\"color\":\"#173177\""
                    +"}"
               +"}"
        +"}";
  		System.out.println(jsonContext);
  		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
  		JSONObject result = WxUtil.createPostMsg(url, jsonContext);
  		return result;
	}
}
