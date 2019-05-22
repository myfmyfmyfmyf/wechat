package myf.caption3.demo3_6;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import myf.caption3.demo3_4.Contant;
import myf.caption3.demo3_4.WxUtil;
import net.sf.json.JSONObject;

/**
 * 上传永久图文消息
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class UploadNewsMain {
	public static void main(String[] args) {
		//通过3.6.2上传永久图片，获得mediaid
		JSONObject imageResult = WxUtil.sendMedia("image", "E://404.png");
		String media_id = imageResult.getString("media_id");
		//图文消息json格式
		StringBuffer newsString =new StringBuffer();
		newsString.append("{");
		newsString.append("\"articles\":[");
		newsString.append("     {");
		newsString.append("        		\"title\": \"CSDN博客\",");
		newsString.append("        		\"thumb_media_id\": \""+media_id+"\",");
		newsString.append("        		\"author\": \"牟云飞\",");
		newsString.append("        		\"content_source_url\": \"http://blog.csdn.net/myfmyfmyfmyf\",");
		newsString.append("        		\"content\": \"测试发送新闻信息。。<br/><font color='red'>支持html标签</font>。。。<br/><a href='http://blog.csdn.net/myfmyfmyfmyf'>欢迎查看博客http://blog.csdn.net/myfmyfmyfmyf</a><br/><img src='http://myfmyfmyfmyf.vicp.cc/WX_DEMO/source/touxiang.png' />\",");
		newsString.append("        		\"digest\": \"作者CSDN博客http://blog.csdn.net/myfmyfmyfmyf\",");
		newsString.append("        		\"show_cover_pic\": \"0\"");
		newsString.append("     }");
		newsString.append("]}");
		//上传图文消息
		JSONObject result = WxUtil.createPostMsg(Contant.URL_NEWS_UPLOAD, newsString.toString());
		System.out.println(result);
		//返回结果
		//{"media_id":"TVwrM9MUyS-0ZusnXK0Afe-jeGc1U092Oz436V24QSo"}
	}



}
