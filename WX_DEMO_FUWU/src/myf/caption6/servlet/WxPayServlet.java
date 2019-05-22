package myf.caption6.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import myf.caption6.WxOrderService;
import myf.caption6.WxPayUtil;
import myf.caption6.WxUtil;
import myf.caption6.entity.PayOrder;
import net.sf.json.JSONObject;

/**
 * 微信支付，工具类
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class WxPayServlet extends HttpServlet  {


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//判断是否微信浏览器
		String userAgent=req.getHeader("User-Agent");
		if(-1==userAgent.indexOf("MicroMessenger")){
			//如果不是微信浏览器,跳转到安全页
			resp.sendRedirect("jsp/wxPay/safePage.jsp");
			return ;
		}
		//根据code获得openid
		String code = req.getParameter("code");
		if(null==code||"".equals(code)){
			//如果没有code返回失败
			resp.sendRedirect("jsp/wxPay/safePage.jsp");
			return ;
		}
		String openId=getOpenIdByCode(code);
		//判断openId
		if(null==openId||"".equals(openId)){
			resp.sendRedirect("jsp/wxPay/safePage.jsp");
			return ;
		}
		
//		String openId="oJwCTwZy5Ofrb11vwuxP_jxdZ7dY";//--------测试使用
		
		//创建支付订单
		PayOrder order = new PayOrder();
		order.setAppid(WxUtil.messageAppId);//-------------待修改，微信appid
		order.setMch_id(WxUtil.mchId);//-------------待修改，商户号
		order.setNonce_str(WxPayUtil.getRandomString2(32));
		order.setBody("XXX微信支付");//-------------待修改,支付描述
		order.setOut_trade_no("2012030000000032");//-------------待修改，开发者系统订单号(很重要，用于对账结算)
		order.setTotal_fee("1");//-------------待修改，支付金额,单位分，1表示0.01元
		System.out.println("请求地址："+req.getLocalAddr());
		order.setSpbill_create_ip(req.getLocalAddr());//-------------待修改，APP和网页支付提交用户端ip
		order.setNotify_url("http://www.haiyisoft.com/SELearning/wxNotifyAction.slt");//-------------待修改，异步消息地址
		order.setTrade_type("JSAPI");
		order.setOpenid(openId);
		//创建订单获得
		WxOrderService service=new WxOrderService();
		String orderResult = service.createOrder(order);
		try{
			if(null==orderResult||"".equals(orderResult)){
				//订单创建失败
				resp.sendRedirect("jsp/wxPay/safePage.jsp");
				return ;
			}
			//转换乱码问题
			orderResult=new String(orderResult.getBytes("ISO-8859-1"), "utf-8");
			System.out.println(orderResult+"   -----------------");
			//将xml字符串转换成map
			Map<String, Object> xmlMap = WxPayUtil.getMapFromXML(orderResult);
			//获取数据进行支付签名
			if(null!=xmlMap.get("return_code")&&"SUCCESS".equals(xmlMap.get("return_code"))
					&&null!=xmlMap.get("result_code")&&"SUCCESS".equals(xmlMap.get("result_code"))
					&&null!=xmlMap.get("prepay_id")&&!"".equals(xmlMap.get("prepay_id"))){
				//订单创建成功
				String prepay_id=(String) xmlMap.get("prepay_id");
				//利用订单号生成支付信息
				//appID
				String appID=WxUtil.messageAppId;//-------------待修改，微信appid
				//时间戳
				long timeStamp = new Date().getTime();
				//随机字符串
				String nonceStr = WxPayUtil.getRandomString2(32);
				//订单详情扩展字符串
				String packageVal="prepay_id="+prepay_id;
				//签名方式
				String signType="MD5";
				//生成支付签名
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("appId", appID);
				map.put("timeStamp", timeStamp);
				map.put("nonceStr", nonceStr);
				map.put("package", packageVal);
				map.put("signType", signType);
				String paySign = WxPayUtil.getSign(map);
				System.out.println("微信公众号支付签名："+paySign);
				//跳转订单详细页面，进行支付
				req.setAttribute("appId", appID);
				req.setAttribute("payTimeStamp", timeStamp+"");
				req.setAttribute("nonceStr", nonceStr);
				req.setAttribute("packageVal", packageVal);
				req.setAttribute("signType", signType);
				req.setAttribute("paySign", paySign);
				//
				//生成微信js授权
				WxUtil msgUtil=new WxUtil();
				String jsapi_ticket=msgUtil.getJsapiTicketFromWx();//签名
				String url = WxUtil.webUrl //域名地址，自己的外网请求的域名，如：http://www.baidu.com/Demo
				                + req.getServletPath() ;     //请求页面或其他地址  
				if(null!=req.getQueryString()&&!"".equals(req.getQueryString()))
					url=url+ "?" + (req.getQueryString()); //参数 
				System.out.println(url);
		        Map<String, String> ret = WxUtil.sign(jsapi_ticket, url);
				req.setAttribute("jsapiStr1", ret.get("signature"));
				req.setAttribute("jsapiTime", ret.get("timestamp"));
				req.setAttribute("jsapiNonceStr", ret.get("nonceStr"));
				//调转到支付页面
				req.getRequestDispatcher("jsp/wxPay/wxPay.jsp").forward(req, resp);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 根据code获得openid
	 * @return
	 */
	public  String getOpenIdByCode(String code){
		//微信企业号标识
		String corpid=WxUtil.messageAppId;//-------------待修改，微信appid
		//管理组凭证密钥
		String corpsecret=WxUtil.messageSecret;//-------------待修改，微信secret
		//获取的标识
        String token="";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
	    try {
	    	//利用get形式获得token
	        HttpGet httpget = new HttpGet("https://api.weixin.qq.com/sns/oauth2" +
	        		"/access_token?appid="+corpid+"&secret="+corpsecret
	        		+"&code="+code+"&grant_type=authorization_code");
	        // Create a custom response handler
	        ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
	            public JSONObject handleResponse(
	                    final HttpResponse response) throws ClientProtocolException, IOException {
	                int status = response.getStatusLine().getStatusCode();
	                if (status >= 200 && status < 300) {
	                    HttpEntity entity = response.getEntity();
	                    if(null!=entity){
	                    	String result= EntityUtils.toString(entity);
	                        //根据字符串生成JSON对象
	               		 	JSONObject resultObj = JSONObject.fromObject(result);
	               		 	return resultObj;
	                    }else{
	                    	return null;
	                    }
	                } else {
	                    throw new ClientProtocolException("Unexpected response status: " + status);
	                }
	            }
	        };
	        //返回的json对象
	        JSONObject responseBody = httpclient.execute(httpget, responseHandler);
	        System.out.println("获得openId：");
	        System.out.println(responseBody.toString());
	        if(null!=responseBody){
	        	token= (String) responseBody.get("openid");//返回token
	        }
	        httpclient.close();
	    }catch (Exception e) {
			e.printStackTrace();
		} 
		 return token;
	}

}
