package myf.caption2;
/**
 * 微信公众号入门
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
import java.io.IOException;
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

public class WXHelloWord {
	//主程序
	public static void main(String[] args) {
		WXHelloWord helloWord= new WXHelloWord();
		String messageAppId = "wx9612a30422ff3c59";
		String messageSecret = "486f87848dfedf389a7940d87c139443";
		helloWord.sendHelloWordMsg(messageAppId,messageSecret);
	}
	
	/**
	 * 发送消息
	 * @param jsonContext  json字符串
	 * @param messageAppId  微信公众号标识
	 * @param messageSecret  管理组凭证密钥
	 * @return
	 */
	public  JSONObject sendHelloWordMsg(String messageAppId,String messageSecret){
		String jsonContext="{"
				   +"\"filter\":{"
				      +"\"is_to_all\":true"
				   +"},"
				   +"\"text\":"
				    +"{"
				         +"\"content\":\"" +
				         "微信公众号入门HelloWord\r\n" +
				         "==================" +
				         "\r\n" +
				         "(内容支持超链接、换行)\r\n" +
				         "感谢您对本书的支持！\r\n" +
				         "作者：牟云飞\r\n" +
				         "博客：<a href='http://blog.csdn.net/myfmyfmyfmyf?viewmode=contents' >牟云飞博客地址</a>\r\n" +
				         "微信：15562579597\r\n" +
				         "\""
				    +"},"
				   +"\"msgtype\":\"text\""
				+"}";
		//获得token
		String token=getTokenFromWx(messageAppId,messageSecret);
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
	     //输出信息
	     //{"errcode":0,"errmsg":"send job submission success","msg_id":1000000011}
		httpclient.close();
		return responseBody;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取token
	 * @param messageAppId
	 * @param messageSecret
	 * @return
	 */
	public synchronized static  String getTokenFromWx(String messageAppId,String messageSecret){
		//获取的标识
        String accessToken="";
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
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
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            //返回的json对象
            JSONObject responseBody = httpclient.execute(httpget, responseHandler);
            
        	String token= (String) responseBody.get("access_token");//返回token
        	//token有效时间
        	Long accessTokenInvalidTime=Long.valueOf(responseBody.get("expires_in")+"");
        	System.out.println("获得的accesstoken值："+token);
        	System.out.println("accesstoken有效期："+accessTokenInvalidTime+"秒");
    		accessToken=token;
            httpclient.close();
        }catch (Exception e) {
			e.printStackTrace();
		} 
       
		 return accessToken;
	}
}

