package myf.caption6;

import java.io.IOException;

import myf.caption6.entity.PayOrder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 微信支付，订单服务类
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>8 4, 2016			muyunfei			新建</p>
 */
public class WxOrderService {
	
	/**
	 * 创建微信同一订单
	 * @return
	 */
	public  String createOrder(PayOrder order){
		try {
			//根据order生成订单签名
			if(null!=order&&(null==order.getSign()||"".equals(order.getSign()))){
				String sign = WxPayUtil.getSign(order);
				order.setSign(sign);
			}
			String orderXML = WxPayUtil.orderToXml(order);
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpPost httpPost= new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
			 //发送json格式的数据
			 StringEntity myEntity = new StringEntity(orderXML, 
					   ContentType.create("text/plain", "UTF-8"));
			 //设置需要传递的数据
			 httpPost.setEntity(myEntity);
			 // Create a custom response handler
			 ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				 //对访问结果进行处理
				 public String handleResponse(
                       final HttpResponse response) throws ClientProtocolException, IOException {
					 int status = response.getStatusLine().getStatusCode();
					 if (status >= 200 && status < 300) {
						 HttpEntity entity = response.getEntity();
						 if(null!=entity){
							String result= EntityUtils.toString(entity);
							System.out.println(result);
                  		 	return result;
						 }else{
                       		return null;
						 }
					 } else {
						 throw new ClientProtocolException("Unexpected response status: " + status);
					 }
               }
           };
           	//返回的json对象
           String responseBody = httpclient.execute(httpPost, responseHandler);
           	System.out.println(responseBody);
			httpclient.close();
			return responseBody;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
