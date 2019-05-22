package myf.caption3.demo3_8.vo;
/**
 * 案例：发送个人账单信息
 * 
 * @author muyunfei
 * 
 * <p>Modification History:</p> 
 * <p>Date       Author      Description</p>
 * <p>------------------------------------------------------------------</p>
 * <p>Aug 18, 2016           牟云飞       		 新建</p>
 */
public class WxModelMsg {

	private String toUrl;//跳转的URL
	private String templeteId;//微信模板消息  0支付消息   目前调用固定为0
	private String openId;//消息接收人  数据库中wechat_code字段
	private String orderTitle;//头部信息，如：尊敬的客户，本次充电订单已支付成功
	private String orderName;//订单商品，如：汽车充电费用
	private String orderMoney;//还款金额，如：39.8
	private String orderTime;//还款日期，如：2016年8月22日
	//订单备注,换行使用\\r\\n
	//如：\\r\\n感谢您的光临！\\r\\n若您交易异常，请拨打123456转人工\\r\\n★最新优惠★优惠福利，充100减10元
	private String orderRemark;
	public String getToUrl() {
		return toUrl;
	}
	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getOrderTitle() {
		return orderTitle;
	}
	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public String getTempleteId() {
		return templeteId;
	}
	public void setTempleteId(String templeteId) {
		this.templeteId = templeteId;
	}
	
	
}
