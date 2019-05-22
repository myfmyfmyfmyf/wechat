package myf.caption6.entity;
/**
 * 微信支付，微信支付商户统一下单实体类
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class PayOrder {
//	<xml>
//	   <appid>wx2421b1c4370ec43b</appid>
//	   <attach>支付测试</attach>
//	   <body>JSAPI支付测试</body>
//	   <mch_id>10000100</mch_id>
//	   <detail><![CDATA[{ "goods_detail":[ { "goods_id":"iphone6s_16G", "wxpay_goods_id":"1001", "goods_name":"iPhone6s 16G", "quantity":1, "price":528800, "goods_category":"123456", "body":"苹果手机" }, { "goods_id":"iphone6s_32G", "wxpay_goods_id":"1002", "goods_name":"iPhone6s 32G", "quantity":1, "price":608800, "goods_category":"123789", "body":"苹果手机" } ] }]]></detail>
//	   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>
//	   <notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url>
//	   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>
//	   <out_trade_no>1415659990</out_trade_no>
//	   <spbill_create_ip>14.23.150.211</spbill_create_ip>
//	   <total_fee>1</total_fee>
//	   <trade_type>JSAPI</trade_type>
//	   <sign>0CB01533B8C1EF103065174F50BCA001</sign>
//	</xml> 
	//微信分配的公众账号ID
	private String appid; 
	//微信支付分配的商户号
	private String mch_id;
	//随机字符串，不长于32位
	private String nonce_str ;
	//签名
	private String sign;
	//商品简单描述 不长于128位
	private String body;
	//商品详细 不长于6000位,商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。可以不填
	private String detail;
	//商户订单号 32个字符内
	private String out_trade_no;
	//订单总金额，单位为分
	private String total_fee;
	 //APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
	private String spbill_create_ip;
	//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	private String notify_url;
	//交易类型 取值如下：JSAPI，NATIVE，APP
	private String trade_type;
	//指定支付方式  no_credit--指定不能使用信用卡支付，可以不填
	private String limit_pay;
	//用户标识，trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取
	private String openid;
	
	public PayOrder(){};
	
	public PayOrder(String appid, String mchId, String nonceStr, String sign,
			String body, String detail, String outTradeNo, String totalFee,
			String spbillCreateIp, String notifyUrl, String tradeType,
			String limitPay, String openid) {
		super();
		this.appid = appid;
		mch_id = mchId;
		nonce_str = nonceStr;
		this.sign = sign;
		this.body = body;
		this.detail = detail;
		out_trade_no = outTradeNo;
		total_fee = totalFee;
		spbill_create_ip = spbillCreateIp;
		notify_url = notifyUrl;
		trade_type = tradeType;
		limit_pay = limitPay;
		this.openid = openid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mchId) {
		mch_id = mchId;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonceStr) {
		nonce_str = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String outTradeNo) {
		out_trade_no = outTradeNo;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String totalFee) {
		total_fee = totalFee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbillCreateIp) {
		spbill_create_ip = spbillCreateIp;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notifyUrl) {
		notify_url = notifyUrl;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String tradeType) {
		trade_type = tradeType;
	}
	public String getLimit_pay() {
		return limit_pay;
	}
	public void setLimit_pay(String limitPay) {
		limit_pay = limitPay;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	
}
