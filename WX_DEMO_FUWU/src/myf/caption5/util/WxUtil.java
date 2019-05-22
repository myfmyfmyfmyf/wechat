/******************************************************************************
 * Copyright (C) 2012-2018 Yantai HaiYi Software Co., Ltd
 * All Rights Reserved.
 * 本软件为烟台海颐软件开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package myf.caption5.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 微信服务
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>8 4, 2016			muyunfei			新建</p>
 */
public class WxUtil {

	public static  String messageAppId;
	
	public static  String messageSecret;

	//发送消息获得token
	public static String accessToken;
	//请求token的时间
	public static Date accessTokenDate;
	//token有效时间,默认7200秒,每次请求更新
	public static long accessTokenInvalidTime=7200L;
	//主动调用：发送消息获得token
	public static String jsapiTicket;
	//主动调用：请求token的时间
	public static Date jsapiTicketDate;
	//域名
	public static String  webUrl="http://myfmyfmyfmyf.vicp.cc/WX_DEMO";
	
	/**
	 * 静态块，初始化数据
	 */
	static{
		try{
			Properties properties = new Properties();
			InputStream in = WxUtil.class.getClassLoader().getResourceAsStream("myf/wxConfig.properties");
			properties.load(in);
			messageAppId = properties.get("appId")+"";
			messageSecret = properties.get("secret")+"";
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 从微信获得accessToken
	 * @return
	 */
	public  static  String getTokenFromWx(){
		String token="";
		//微信企业号标识
		String corpid=messageAppId;
		//管理组凭证密钥
		String corpsecret=messageSecret;
		//获取的标识
		//1、判断accessToken是否存在，不存在的话直接申请
        //2、判断时间是否过期，过期(>=7200秒)申请，否则不用请求直接返回以后的token
		if(null==accessToken||"".equals(accessToken)||
				(new Date().getTime()-accessTokenDate.getTime())>=((accessTokenInvalidTime-200L)*1000L)){
			CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	        	//利用get形式获得token
	            HttpGet httpget = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?" +
	            		"grant_type=client_credential&appid="+corpid+"&secret="+corpsecret);
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
	            //正确返回结果，进行更新数据
	            if(null!=responseBody&&null!=responseBody.get("access_token")){
	            	token= (String) responseBody.get("access_token");//返回token
	            	//token有效时间
	            	accessTokenInvalidTime=Long.valueOf(responseBody.get("expires_in")+"");
		    		//设置全局变量
		    		accessToken=token;
		    		accessTokenDate=new Date();
	            }
	            httpclient.close();
	        }catch (Exception e) {
				e.printStackTrace();
			} 
        }else{
        	token=accessToken;
        }
		 return token;
	}
	
	/**
	 * 根据code获得openid
	 * @return
	 */
	public static String getOpenIdByCode(String code){
		//微信企业号标识
		String corpid=messageAppId;
		//管理组凭证密钥
		String corpsecret=messageSecret;
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
	        if(null!=responseBody){
	        	token= (String) responseBody.get("openid");//返回token
	        }
	        httpclient.close();
	    }catch (Exception e) {
			e.printStackTrace();
		} 
		 return token;
	}
	
	/**
	 * 从微信获得jsapi_ticket
	 * @return
	 */
	public  String getJsapiTicketFromWx(){
		String token=getTokenFromWx();//token
		//1、判断jsapiTicket是否存在，不存在的话直接申请
        //2、判断时间是否过期，过期(>=7200秒)申请，否则不用请求直接返回以后的token
		if(null==jsapiTicket||"".equals(jsapiTicket)||(new Date().getTime()-jsapiTicketDate.getTime())>=(7000*1000)){
		
			CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	        	//利用get形式获得token
	            HttpGet httpget = new HttpGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi");
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
	            if(null!=responseBody){
	            	jsapiTicket= (String) responseBody.get("ticket");//返回token
	            }
	            jsapiTicketDate=new Date();
	            httpclient.close();
	        }catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return jsapiTicket;
	}
	
	
	/****微信js签名***********************************/
	 
	public static Map<String, String> sign(String jsapiTicket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket +
                  "&noncestr=" + nonceStr +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    /****微信js签名***********************************/
    
    
	/**
  	 * 获得人员，根据useid
  	 */
	public JSONObject getPeopleByOpenId(String openId){
  		//消息json格式
  		//获得token
  		String token=getTokenFromWx();
		 try {
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpPost httpPost= new HttpPost("https://api.weixin.qq.com/cgi-bin" +
			 		"/user/info?access_token="+token+"&openid="+openId+"&lang=zh_CN");
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
            JSONObject responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println(responseBody.toString());
            return responseBody;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
  	}
	
	
}
