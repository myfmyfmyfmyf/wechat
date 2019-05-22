package myf;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.sun.star.util.Time;
/**
 * 测试域名稳定性
 * @author 牟云飞
 * @QQ 1147417467
 *
 */
public class TestNetWorkRateMain {
	public static void main(String[] args) {
		String noteUrl="http://121.8.169.37:9000/evportal/ev_net/webpic/17.jpg";//------------------------------------
		//调用结果
		while(1==1) {
			//循环调用
			try {
				//调用间隔毫秒数
				Thread.sleep(100);
				final Date bgTime = new Date();
				//发起链接调用
				 CloseableHttpClient httpclient = HttpClients.createDefault();
				 
				 HttpGet httpGet= new HttpGet(noteUrl); 
	            
				 // Create a custom response handler
	            ResponseHandler<Object> responseHandler = new ResponseHandler<Object>() {
	            	//对访问结果进行处理
	                public Object handleResponse(
	                        final HttpResponse response) throws ClientProtocolException, IOException {
	                    int status = response.getStatusLine().getStatusCode();
	            		SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	                    if (status >= 200 && status < 300) {
	                        HttpEntity entity = response.getEntity();
	                        Date endTime = new Date();
	                        double time = endTime.getTime() - bgTime.getTime();
	                        System.out.println(sd.format(bgTime)+":SUCCESS   "+status+"   time："+time/Double.valueOf(1000)+"s");
	                        TestNetWorkRateMain.writeLog(sd.format( new Date())+":SUCCESS   "+status+"   time："+time/1000+"ms");
	                        if(null!=entity){
	                        	String result= "";
	                            //根据字符串生成JSON对象
	                   		 	return null;
	                        }else{
	                        	return null;
	                        }
	                    } else {
	                    	System.out.println(sd.format(new Date())+":Unexpected response status: " + status);
	                    	TestNetWorkRateMain.writeLog(sd.format(new Date())+":Unexpected response status: " + status);
	                    	throw new ClientProtocolException(sd.format(new Date())+":Unexpected response status: " + status);
	                    	
	                    }
	                }
	            };
	          //返回的json对象
	             httpclient.execute(httpGet, responseHandler);
				httpclient.close();
			} catch (Exception e) {
				e.printStackTrace();
				SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
				System.out.println(sd.format(new Date())+":"+e.getMessage());
				TestNetWorkRateMain.writeLog(sd.format(new Date())+":"+e.getMessage());
            	
			}
		}
	}

	public static FileWriter output = null;
	public static BufferedWriter bf = null;
	
	/**
	 * 记录日志到H://net.txt目录下
	 * @param log
	 */
	public static void writeLog(String log){
		try {
			if(null==output)
				output= new FileWriter("H://net.txt");
			
			if(null==bf)
				bf= new BufferedWriter(output);
			bf= new BufferedWriter(output);
			bf.write(log + "\r\n");
			bf.flush();// 清空通道
		
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
}
