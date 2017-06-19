package myf.caption3.demo3_3;

import java.io.IOException;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Token缓存处理
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class TokenSession {
	public static void main(String[] args) {
		System.out.println(TokenSession.getTokenFromWx());
	}
	
	
	//主动调用：发送消息AccessTokentoken
	public static String access_token;
	//主动调用：请求token的时间
	public static Date access_token_date;
	//token有效时间,默认7200秒,每次请求更新，用于判断token是否超时
	public static long accessTokenInvalidTime=7200L;

	
	/**
	 * 从微信获得access_token
	 * @return
	 */
	public static String getTokenFromWx(){
		//微信公众号唯一标识
		String appID="****";//请读者改成自己的AppID
		//管理组凭证密钥
		String secret="****"; //请读者改成自己的Secret
		//获取的标识
		String token="";
		//1、判断access_token是否存在，不存在的话直接申请
		//2、判断时间是否过期，过期(>=7200秒)申请，否则不用请求直接返回以后的token
		if(null==access_token||"".equals(access_token)||(new Date().getTime()-access_token_date.getTime())>=((accessTokenInvalidTime-200L)*1000L)){
			CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	        	//利用get形式获得token
	            HttpGet httpget = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?" +
	            		"grant_type=client_credential&appid="+appID+"&secret="+secret);
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
	                        throw new ClientProtocolException("Unexpected status: " + status);
	                    }
	                }
	            };
	            //返回的json对象
	            JSONObject responseBody = httpclient.execute(httpget, responseHandler);
	            //正确返回结果，进行更新数据
	            if(null!=responseBody&&null!=responseBody.get("access_token")){
	                  //设置全局变量
	                  token= (String) responseBody.get("access_token");//返回token
	                  //更新token有效时间
	                  accessTokenInvalidTime=Long.valueOf(responseBody.get("expires_in")+"");
	                  //设置全局变量
	                  access_token=token;
	                  access_token_date=new Date();
	            }
	            httpclient.close();
	        }catch (Exception e) {
				e.printStackTrace();
			} 
	    }else{
	    	token=access_token;
	    }
		 return token;
	}

}
