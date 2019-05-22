package myf.caption3.demo3_4;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;


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

/**
 * 微信开发工具类
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class WxUtil {

	//主动调用：发送消息AccessTokentoken
	public static String access_token;
	//主动调用：请求token的时间
	private static Date access_token_date;
	//token有效时间,默认7200秒,每次请求更新，用于判断token是否超时
	private static long accessTokenInvalidTime=7200L;
	//微信标识appID
	public static  String messageAppId;
	// 管理组凭证密钥 
	public static  String messageSecret;
	//域名
	public static String webUrl;
	
	/**
	 * 静态块，初始化数据
	 */
	static{
		try{
			Properties properties = new Properties();
			//InputStream in = WxUtil.class.getClassLoader().getResourceAsStream("myf/wxConfig.properties");
			InputStream in  =new FileInputStream(new java.io.File("E:/微信/微信服务号/WX_DEMO_FUWU/src/myf/wxConfig.properties"));
			
			properties.load(in);
			messageAppId = properties.get("messageAppId")+"";
			messageSecret = properties.get("messageSecret")+"";
			webUrl = properties.get("webUrl")+"";
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 发起主动调用   Post方式
	 * @param context
	 * @return
	 */
	public static JSONObject createPostMsg(String url , String context) {
		String jsonContext=context;
		//获得token
  		String token=WxUtil.getTokenFromWx();
  		System.out.println(token);
		 try {
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpPost httpPost= new HttpPost(url+token);
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
            //System.out.println(responseBody);
			httpclient.close();
			return responseBody;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	/**
	 * 发起主动调用   Get方式
	 * @param context
	 * @return
	 */
	public static JSONObject createGetMsg(String url) {
		//获得token
  		String token=WxUtil.getTokenFromWx();
  		System.out.println(token);
		 try {
			 CloseableHttpClient httpclient = HttpClients.createDefault();
			 HttpGet httpGet= new HttpGet(url+token);
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
            JSONObject responseBody = httpclient.execute(httpGet, responseHandler);
            //System.out.println(responseBody);
			httpclient.close();
			return responseBody;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	/**
	 * 从微信获得access_token
	 * @return
	 */
	public static String getTokenFromWx(){
		//获取的标识
		String token="";
		//1、判断access_token是否存在，不存在的话直接申请
		//2、判断时间是否过期，过期(>=7200秒)申请，否则不用请求直接返回以后的token
		if(null==access_token||"".equals(access_token)||(new Date().getTime()-access_token_date.getTime())>=((accessTokenInvalidTime-200L)*1000L)){
			CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	        	System.out.println(messageAppId);
	        	System.out.println(messageSecret);
	        	//利用get形式获得token
	            HttpGet httpget = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?" +
	            		"grant_type=client_credential&appid="+messageAppId+"&secret="+messageSecret);
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
//	        //获得临时素材media_id
//	        URL urlObj = new URL("https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+ token + "&type="+fileType+""); 
			
//	        //获得临时media_id不可用，可以使用该接口
//	        URL urlObj = new URL("http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+ token 
//	        		+ "&type="+fileType+""); 
	        
	        //获得永久素材mediaId
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
	        sb.append("Content-Disposition: form-data;name=\"media\";id=\"media\";filename=\""+ file.getName() + "\"\r\n");  
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
    
    /**
     * 图文消息内的图片上传到微信服务器
     * @param filePath 文件路径
     * @return JSONObject
     * @throws Exception
     */
    public  static JSONObject sendMediaNewsImg(String filePath)  {  
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
	        //获得永久素材mediaId
	        URL urlObj = new URL("https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="+ token); 
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
	        sb.append("Content-Disposition: form-data;name=\"media\";id=\"media\";filename=\""+ file.getName() + "\"\r\n");  
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
    
    /**
     *下载临时文件
     * @param mediaId媒体文件Id 
     * @param resp数据响应
     * @return InputStream  获得文件输入流，注释部分为直接保存本地
     */
    public static  InputStream downloadFile(String mediaId,Object resp){
    	//获取token凭证
    	String token=getTokenFromWx();
    	String urlStr="https://api.weixin.qq.com/cgi-bin/media/get?access_token="+token+"&media_id="+mediaId;
    	try {
    		 URL urlObj = new URL(urlStr);
    		 HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
    		 con.setDoInput(true);
    		 //打印头部信息
//    		 Map<String, List<String>> aa = con.getHeaderFields();
//    		 for (int i = 0; i < aa.size(); i++) {
//    			 List<String> listTemp = aa.get(i);
//    			 String _temp="";
//    			 if(null!=listTemp&&0!=listTemp.size()){
//	    			 for (int j = 0; j < listTemp.size(); j++) {
//	    				 _temp+=listTemp.get(j);
//					 }
//	    			 System.out.println(_temp);
//    			 }
//			}
//    		 //设置servlet请求文件格式
    		 String contentType = con.getContentType();
//    		 resp.setContentType(contentType);
    		 //输出文件格式
    		 System.out.println("文件格式："+contentType);
    		 if(contentType.equals("text/plain")){
    			 //如果出现错误返回null,并且打印错误信息
    			 ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
    			 InputStream inTxt = con.getInputStream();
    			 byte[] b = new byte[512];
    			 int i ;
    			 while(( i =inTxt.read(b))>0){
    				 infoStream.write(b); 
    			 }
    			 System.out.println("错误信息:"+infoStream.toString());
    			 infoStream.close();
    			 return null;
    		 }
    		 //返回输出流
    		 return con.getInputStream();
    		 /**
    		 //保存文件
    		 InputStream in = util.downloadFile(accessId,resp);
    		 if(null!=in){
    			 OutputStream outputStream = new FileOutputStream(new File("G:\\app\\aaa.jpg"));
    			 byte[] bytes = new byte[1024];  
    			 int cnt=0;  
    			 while ((cnt=in.read(bytes,0,bytes.length)) != -1) {  
    				 outputStream.write(bytes, 0, cnt);  
    			 }  
    			 outputStream.flush();
    			 outputStream.close();  
    			 in.close();  
    		 }else{
    			//图片获取失败，显示默认图片
    			System.out.println("图片获取失败");
    		 }
    		 **/
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} 
    	return null;
    }
    
    

}
