package myf.caption6.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myf.caption6.WxPayUtil;

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
public class WxNotifyAction extends HttpServlet  {


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
		try{
			//获取微信POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			// post请求的密文数据
			// sReqData = HttpUtils.PostData();
			ServletInputStream in = req.getInputStream();
			BufferedReader reader =new BufferedReader(new InputStreamReader(in));
			String sReqData="";
			String itemStr="";//作为输出字符串的临时串，用于判断是否读取完毕
			while(null!=(itemStr=reader.readLine())){
				sReqData+=itemStr;
			}
			//----  待修改，可以在这里写一个log日志文件，记录相应信息
			System.out.println(sReqData);
			//----  待修改，结束
			//解析数据
			Map<String, Object> map = WxPayUtil.getMapFromXML(sReqData);
//			//打印接收信息
//			Iterator iterator = map.entrySet().iterator();  
//		    while (iterator.hasNext()) {  
//		        Map.Entry<String, String> entry = (Entry<String, String>) iterator.next();  
//		        System.out.println("key:" + entry.getKey() + " value："+ entry.getValue());  
//		    }
		    //判断支付结果，return_code通信标识，非交易标识，交易是否成功需要查看result_code来判断
		    String return_code=map.get("return_code")+"";
		    String result_code=map.get("result_code")+"";
		    if("SUCCESS".equals(return_code)&&"SUCCESS".equals(result_code)){
		    	//表示支付成功
		    	//sign进行验签，确保消息的真伪
		    	String sign = map.get("sign")+"";//sign不参与验签
		    	String reSign = WxPayUtil.getSign(map);
		    	if(sign.equals(reSign)){
		    		//验签成功，进行结算
		    		System.out.println("验签成功");
		    		
		    		//----待修改，结算时，加锁加事务，验证订单是否有效，判断金额是否正确
		    	}
		    }
		    //返回消息
		    String resultMsg="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
		    PrintWriter out = resp.getWriter();
		    out.write(resultMsg);
		    out.flush();
		    out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
