package myf.caption3.demo3_2;

import java.io.IOException; 
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
 * 申请token
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class ApplyAccessToken_main {


	public static void main(String[] args) {
		String token = ApplyAccessToken_main.getToken("输入appID", "输入管理组密钥");
		System.out.println("--------------------------");
		System.out.println("获得的Token为:"+token);
		System.out.println("--------------------------");
	}
	
	//获取微信token
	public static String getToken(String corpid,String corpsecret){
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
            String token="";
            if(null!=responseBody){
            	token= (String) responseBody.get("access_token");//返回token
            }
            //System.out.println("----------------------------------------");
            //System.out.println("access_token:"+responseBody.get("access_token"));
            //System.out.println("expires_in:"+responseBody.get("expires_in"));

            httpclient.close();
            return token;
        }catch (Exception e) {
			e.printStackTrace();
        	return "";
		} 
	}
	

}
