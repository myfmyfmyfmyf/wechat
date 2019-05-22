package myf.caption3.demo3_7;

import java.util.ArrayList;
import java.util.List;

import myf.caption3.demo3_4.Contant;
import myf.caption3.demo3_4.WxUtil;
import myf.caption3.demo3_7.vo.WxArticleList;
import myf.caption3.demo3_7.vo.WxMpArticle;
import net.sf.json.JSONObject;

/**
 * 案例：推送最新活动
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class DemoMsgMain {

	public static void main(String[] args) {
		//第一条图文消息
		String headImgPath = "E://randomImage11.jpg";//通知图片
		//通过3.6.2上传永久图片，标题图片获得mediaid
		JSONObject imageResult = WxUtil.sendMedia("image", headImgPath);
		String thumbMediaId = imageResult.getString("media_id");
		//上传图文消息，消息内图片,
		//不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下。
		JSONObject newsImageResult = WxUtil.sendMediaNewsImg("E://load.png");
		String newsImageUrl = newsImageResult.getString("url");
		System.out.println("newsImageUrl:"+newsImageUrl);
		String title = "\"千里行\"为爱而行";//标题
		String author = "牟云飞";//作者
		String contentSourceUrl = "http://blog.csdn.net/myfmyfmyfmyf";//原文链接
		String content = "<img width='100%'  src='"+newsImageUrl+"' />";
		String digest = "穿越沙漠千里行，为爱而行...";//描述
		String showCoverPic = "0";
		WxMpArticle artitle1 = new WxMpArticle(title, thumbMediaId, author,
				 contentSourceUrl,content,digest,showCoverPic);
		//第二条图文消息
		String headImgPath2 = "E://404.png";//通知图片
		//通过3.6.2上传永久图片，获得mediaid
		JSONObject imageResult2 = WxUtil.sendMedia("image", headImgPath2);
		String thumbMediaId2 = imageResult2.getString("media_id");
		String title2 = "CSDN博客地址";//标题
		String author2 = "牟云飞";//作者
		String contentSourceUrl2 = "http://blog.csdn.net/myfmyfmyfmyf";//原文链接
		String content2 = "<img width='100%'   src='"+newsImageUrl+"'  />";
		String digest2 = "作者CSDN博客http://blog.csdn.net/myfmyfmyfmyf";//描述
		String showCoverPic2 = "0";
		WxMpArticle artitle2 = new WxMpArticle(title2, thumbMediaId2, author2,
				 contentSourceUrl2,content2,digest2,showCoverPic2);
		List<WxMpArticle> list = new ArrayList<WxMpArticle>();
		list.add(artitle1);
		list.add(artitle2);
		WxArticleList newsList  = new WxArticleList(list);
		System.out.println(JSONObject.fromObject(newsList).toString());
		//发送消息
		long clientmsgid=68;
		//clientmsgid，防止重发
		//clientmsgid，与之前的clientmsgif不可重复，否则{"errcode":45065
		DemoMsgMain.SendMsg(JSONObject.fromObject(newsList).toString(),clientmsgid);
	}
	
	private static void SendMsg(String postJsonContext,long clientmsgid){
		//上传图文消息
		JSONObject result = WxUtil.createPostMsg(Contant.URL_NEWS_UPLOAD, postJsonContext);
		System.out.println(result);
		//发送图文消息
		String jsonContext_mpnews="{"
			   +"\"filter\":{"
			   		+"\"is_to_all\":false,"
			   		+"\"tag_id\":100"
			   +"},"
			   +"\"mpnews\":"
			    +"{"
			         +"\"media_id\":\"TVwrM9MUyS-0ZusnXK0AfbaHB_PQpxRMNpLsopdbU4s\"" 
			    +"},"
			   +"\"msgtype\":\"mpnews\","
			   +"\"send_ignore_reprint\":1,"
			   +"\"clientmsgid\":"+clientmsgid
			+"}";
		//修改图文消息，createPostMsg封装主动调用公用方法3.4节介绍
		String url_mapnew = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=";
		JSONObject result_mapnew = WxUtil.createPostMsg(url_mapnew, jsonContext_mpnews);
		System.out.println(result_mapnew);
	}
}
