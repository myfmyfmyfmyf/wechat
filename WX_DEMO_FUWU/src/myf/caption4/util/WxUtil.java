/******************************************************************************
 * Copyright (C) 2012-2018 Yantai HaiYi Software Co., Ltd
 * All Rights Reserved.
 * 本软件为烟台海颐软件开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package myf.caption4.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import myf.caption4.vo.Article;
import myf.caption4.vo.ImageMessage;
import myf.caption4.vo.MusicMessage;
import myf.caption4.vo.NewsMessage;
import myf.caption4.vo.TextMessage;
import myf.caption4.vo.VideoMessage;
import myf.caption4.vo.VoiceMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 微信服务
 *
 * @author muyunfei
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>8 4, 2016			muyunfei			新建</p>
 */
public class WxUtil {
	// token 
	public static  String respMessageToken;
	// APPID 
	public static  String messageAppId;
	// 管理组凭证密钥  
	public static  String messageSecret;
	// AES密钥  密钥生成规则：32位的明文经过base64加密后，去掉末尾=号，形成43为的密钥
	public static  String respMessageEncodingAesKey;
	
	public static  String orderTemplateId;
	
	// 主动发送消息不需要消息体进行加密，格式为json格式
	// 请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	// 请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	// 请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	// 请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	// 请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	// 请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	// 请求消息类型：新闻
	public static final String REQ_MESSAGE_TYPE_NEWS = "news";

	// 请求消息类型：事件推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	// 事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// 事件类型：scan(用户已关注时的扫描带参数二维码)
	public static final String EVENT_TYPE_SCAN = "scan";
	// 事件类型：LOCATION(上报地理位置)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// 事件类型：CLICK(自定义菜单)
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	
	// 被动响应消息类型：文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	// 被动响应消息类型：图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	// 被动响应消息类型：语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	// 被动响应消息类型：视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	// 被动响应消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	// 被动响应消息类型：图文
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	

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
			respMessageToken = properties.get("token")+"";
			respMessageEncodingAesKey = properties.get("encodeAesKey")+"";
			orderTemplateId = properties.get("templateId")+"";
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 从微信获得accessToken
	 * @return
	 */
	public synchronized static  String getTokenFromWx(){
		//微信企业号标识
		String corpid=messageAppId;
		//管理组凭证密钥
		String corpsecret=messageSecret;
		//获取的标识
        String token="";
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
	 * 创建菜单
	 * @param context
	 * @return
	 */
	public static JSONObject createMetuMsg(String context) {
		String jsonContext=context;

		//获得token
  		String token=getTokenFromWx();
		 boolean flag=false;
		 try {
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpPost httpPost= new HttpPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token);
			 //发送json格式的数据
			 StringEntity myEntity = new StringEntity(jsonContext, 
					   ContentType.create("text/plain", "UTF-8"));
			 //设置需要传递的数据
			 httpPost.setEntity(myEntity);
			 // Create a custom response handler
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
            	//对访问结果进行处理
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
            System.out.println(responseBody);
			httpclient.close();
			return responseBody;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	/**
	 * 发送客服消息
	 * @return
	 */
	public static JSONObject sendCustomerMsg(String context,String toUser){
		String jsonContext="{\"touser\":\""+toUser+"\",\"msgtype\":\"text\","
			    +"\"text\":{\"content\":\"" +context+"\"}}";
//		//消息json格式
//  		String jsonContext="{"
//		    +"\"touser\":\"oTrNHs6-2rLa290KStWwhqmwfB1U\","
//		    +"\"msgtype\":\"text\","
//		    +"\"text\":"
//		    +"{"
//		         +"\"content\":\"" +
//		         "充电成功通知\r\n" +
//		         "==================" +
//		         "\r\n" +
//		         "您的爱车充电已充满！\r\n" +
//		         "总计消费：$17.66\r\n" +
//		         "充电电量：20度\r\n" +
//		         "<a href='http://www.baidu.com'>欢迎进行充电</a>\""
//		    +"}"
//	  +"}";
  		//System.out.println(jsonContext);
  		//获得token
  		String token=getTokenFromWx();
		 boolean flag=false;
		 try {
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpPost httpPost= new HttpPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+token);
			 //发送json格式的数据
			 StringEntity myEntity = new StringEntity(jsonContext, 
					   ContentType.create("text/plain", "UTF-8"));
			 //设置需要传递的数据
			 httpPost.setEntity(myEntity);
			 // Create a custom response handler
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
            	//对访问结果进行处理
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
            System.out.println(responseBody);
			httpclient.close();
			return responseBody;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	/**
	 * 群发text文本消息,所有群发共4条
	 * @return
	 */
	public static JSONObject sendAllTextMsg(String context){
		String jsonContext="{"
			   +"\"filter\":{"
			      +"\"is_to_all\":true"
			   +"},"
			   +"\"text\":{"
			      +"\"content\":\""+context+"\""
			   +"},"
			   +"\"msgtype\":\"text\""
			+"}";
//		String jsonContext="{"
//				   +"\"filter\":{"
//				      +"\"is_to_all\":true"
//				   +"},"
//				   +"\"text\":"
//				    +"{"
//				         +"\"content\":\"" +
//				         "群发消息\r\n" +
//				         "==================" +
//				         "\r\n" +
//				         "您的爱车充电已充满！\r\n" +
//				         "总计消费：<a href='http://www.baidu.com' style='color:#FF0000;font-color:#FF0000'>$17.66</a>\r\n" +
//				         "充电电量：20度\r\n" +
//				         "<a href='http://www.baidu.com'>欢迎进行充电</a>\""
//				    +"},"
//				   +"\"msgtype\":\"text\""
//				+"}";
		//获得token
  		String token=getTokenFromWx();
  		System.out.println("token:"+token);
		 boolean flag=false;
		 try {
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpPost httpPost= new HttpPost("https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+token);
			 //发送json格式的数据
			 StringEntity myEntity = new StringEntity(jsonContext, 
					   ContentType.create("text/plain", "UTF-8"));
			 //设置需要传递的数据
			 httpPost.setEntity(myEntity);
			 // Create a custom response handler
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
            	//对访问结果进行处理
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
            System.out.println(responseBody);
			httpclient.close();
			return responseBody;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 群发消息，每月4条,所有群发共4条
	 * @param jsonContext
	 * @return
	 */
	public static JSONObject sendAllMpNewsMsg(String mpnewsMediaId){
		String jsonContext="{\"filter\":{\"is_to_all\":true}," +
				"\"mpnews\":{\"media_id\":\"" +mpnewsMediaId+
				"\"},\"msgtype\":\"mpnews\"}";
		//获得token
  		String token=getTokenFromWx();
  		System.out.println("token:"+token);
		 boolean flag=false;
		 try {
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpPost httpPost= new HttpPost("https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+token);
			 //发送json格式的数据
			 StringEntity myEntity = new StringEntity(jsonContext, 
					   ContentType.create("text/plain", "UTF-8"));
			 //设置需要传递的数据
			 httpPost.setEntity(myEntity);
			 // Create a custom response handler
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
            	//对访问结果进行处理
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
            System.out.println(responseBody);
			httpclient.close();
			return responseBody;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	/**
	 * 上传永久图文消息
	 * @return
	 */
	public static JSONObject sendMpnewsMedia(String jsonContext){
  		//获得token
  		String token=getTokenFromWx();
		 boolean flag=false;
		 try {
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpPost httpPost= new HttpPost("https://api.weixin.qq.com/cgi-bin/material/add_news?access_token="+token);
			 //发送json格式的数据
			 StringEntity myEntity = new StringEntity(jsonContext, 
					   ContentType.create("text/plain", "UTF-8"));
			 //设置需要传递的数据
			 httpPost.setEntity(myEntity);
			 // Create a custom response handler
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
            	//对访问结果进行处理
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
            System.out.println(responseBody);
			httpclient.close();
			return responseBody;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	/**
     * 文件上传到微信服务器
     * @param fileType 文件类型 媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)
     * @param filePath 文件路径
     * @return JSONObject
     * @throws Exception
     */
    public  static JSONObject sendMedia(String fileType, String filePath)  {  
    	try{
        String result = null;  
        File file = new File(filePath);  
        if (!file.exists() || !file.isFile()) {  
            throw new IOException("文件不存在");  
        }  
        String token=getTokenFromWx();
        /** 
        * 第一部分 
        */  
        URL urlObj = new URL("https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+ token 
        		+ "&type="+fileType+""); 
        					  
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();  
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式  
        con.setDoInput(true);  
        con.setDoOutput(true);  
        con.setUseCaches(false); // post方式不能使用缓存  
        // 设置请求头信息  
        con.setRequestProperty("Connection", "Keep-Alive");  
        con.setRequestProperty("Charset", "UTF-8");  
        // 设置边界  
        String boundary = "----------" + System.currentTimeMillis();  
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ boundary);  
        // 请求正文信息  
        // 第一部分：  
        StringBuilder sb = new StringBuilder();  
        sb.append("--"); // 必须多两道线  
        sb.append(boundary);  
        sb.append("\r\n");  
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\""+ file.getName() + "\"\r\n");  
        sb.append("Content-Type:application/octet-stream\r\n\r\n");  
        byte[] head = sb.toString().getBytes("utf-8");  
        // 获得输出流  
        OutputStream out = new DataOutputStream(con.getOutputStream());  
        // 输出表头  
        out.write(head);  
        // 文件正文部分  
        // 把文件已流文件的方式 推入到url中  
        DataInputStream in = new DataInputStream(new FileInputStream(file));  
        int bytes = 0;  
        byte[] bufferOut = new byte[1024];  
        while ((bytes = in.read(bufferOut)) != -1) {  
        out.write(bufferOut, 0, bytes);  
        }  
        in.close();  
        // 结尾部分  
        byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线  
        out.write(foot);  
        out.flush();  
        out.close();  
        StringBuffer buffer = new StringBuffer();  
        BufferedReader reader = null;  
        try {  
        // 定义BufferedReader输入流来读取URL的响应  
        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));  
        String line = null;  
        while ((line = reader.readLine()) != null) {  
        //System.out.println(line);  
        buffer.append(line);  
        }  
        if(result==null){  
        result = buffer.toString();  
        }  
        } catch (IOException e) {  
        System.out.println("发送POST请求出现异常！" + e);  
        e.printStackTrace();  
        throw new IOException("数据读取异常");  
        } finally {  
        if(reader!=null){  
        reader.close();  
        }  
        }  
        JSONObject jsonObj =JSONObject.fromObject(result);  
        System.out.println(jsonObj);
        return jsonObj;  
    	}catch (Exception e) {
    		return null; 
		}
    }
	
/*	*//**
	 * 发送模板消息
	 * @return
	 *//*
	public static String sendModelMsg(WxModelMsg msg){
		
		//消息json格式
  		String jsonContext="{"
           +"\"touser\":\""+msg.getOpenId()+"\","
           +"\"template_id\":\""+orderTemplateId+"\","
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
	                   +"\"value\":\""+msg.getOrderId()+"\","
	                   +"\"color\":\"#000000\""
                   +"},"
                   +"\"keyword3\": {"
                       +"\"value\":\""+msg.getOrderMoney()+"\","
                       +"\"color\":\"#FF0000\""
                       +"},"
                   +"\"keyword4\": {"
                       +"\"value\":\""+msg.getOrderTime()+"\","
                       +"\"color\":\"#000000\""
                       +"},"
                   +"\"remark\":{"
                       +"\"value\":\""+msg.getOrderRemark()+"\","
                       +"\"color\":\"#173177\""
                    +"}"
               +"}"
        +"}";
  		System.out.println(jsonContext);
  		//获得token
  		String token=getTokenFromWx();
		 boolean flag=false;
		 try {
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpPost httpPost= new HttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token);
			 //发送json格式的数据
			 StringEntity myEntity = new StringEntity(jsonContext, 
					   ContentType.create("text/plain", "UTF-8"));
			 //设置需要传递的数据
			 httpPost.setEntity(myEntity);
			 // Create a custom response handler
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {
            	//对访问结果进行处理
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
			httpclient.close();
			return responseBody.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	*/
	
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
	
	/**
	 * 扩展xstream使其支持CDATA
	 * 内部类XppDriver
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String messageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图片消息对象转换成xml
	 * 
	 * @param imageMessage 图片消息对象
	 * @return xml
	 */
	public static String messageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 语音消息对象转换成xml
	 * 
	 * @param voiceMessage 语音消息对象
	 * @return xml
	 */
	public static String messageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	/**
	 * 视频消息对象转换成xml
	 * 
	 * @param videoMessage 视频消息对象
	 * @return xml
	 */
	public static String messageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage 音乐消息对象
	 * @return xml
	 */
	public static String messageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String messageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
}
