package myf.caption3.demo3_8;

import myf.caption3.demo3_4.Contant;
import myf.caption3.demo3_4.WxUtil;
import myf.caption3.demo3_8.vo.WxModelMsg;
import net.sf.json.JSONObject;

/**
 * 案例：发送个人账单信息
 * @author muyunfei
 * 
 * <p>Modification History:</p> 
 * <p>Date       Author      Description</p>
 * <p>------------------------------------------------------------------</p>
 * <p>Jun 16, 2017           牟云飞       		 新建</p>
 */
public class DemoMsgMain {
	public static void main(String[] args) {
		WxModelMsg msg= new WxModelMsg();
		msg.setOpenId("oGzTi0tOakoHMX76VxKIB0GMcX8M");
		msg.setTempleteId("XyDgtJAbCKTMGwXsPo6h-uWqwNTn1GVKcVvFmmvA0ws");
		msg.setOrderTitle("尊敬的客户，本次充电订单已支付成功");
		msg.setOrderName("汽车充电费用");
		msg.setOrderMoney("39.8元");
		msg.setOrderTime("2016年8月22日");
		msg.setOrderRemark("\\r\\n感谢您的光临！\\r\\n若您交易异常，请拨打123456转人工\\r\\n★最新优惠★优惠福利，充100减10元");
		msg.setToUrl("http://blog.csdn.net/myfmyfmyfmyf?viewmode=contents");
		JSONObject result = DemoMsgMain.sendModelMsg(msg);
		System.out.println(result.toString());
	}
	
	public static JSONObject sendModelMsg(WxModelMsg msg){
		
		//消息json格式
  		String jsonContext="{"
           +"\"touser\":\""+msg.getOpenId()+"\","
           +"\"template_id\":\""+msg.getTempleteId()+"\","
           +"\"url\":\""+msg.getToUrl()+"\","            
           +"\"data\":{"
                   +"\"first\": {"
                       +"\"value\":\""+msg.getOrderTitle()+" \","
                       +"\"color\":\"#000000\""
                       +"},"
                   +"\"keyword1\":{"
                       +"\"value\":\""+msg.getOrderName()+"\","
                       +"\"color\":\"#000000\""
                       +"},"
	               +"\"keyword2\":{"
	                   +"\"value\":\""+msg.getOrderTime()+"\","
	                   +"\"color\":\"#000000\""
                   +"},"
                   +"\"keyword3\": {"
                       +"\"value\":\""+msg.getOrderMoney()+"\","
                       +"\"color\":\"#FF0000\""
                       +"},"
                   +"\"remark\":{"
                       +"\"value\":\""+msg.getOrderRemark()+"\","
                       +"\"color\":\"#173177\""
                    +"}"
               +"}"
        +"}";
  		JSONObject result = WxUtil.createPostMsg(Contant.URL_MSG_MODEL, jsonContext);
		return result;
	}
}
