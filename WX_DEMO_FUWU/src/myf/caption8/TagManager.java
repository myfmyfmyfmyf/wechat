package myf.caption8;

import net.sf.json.JSONObject;
import myf.caption3.demo3_4.WxUtil;

/**
 * 微信支付，微信支付商户统一下单实体类
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class TagManager {
	public static void main(String[] args) {
//		//查看某一个用户下所有标签
//		String userTagStr = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=";
//		String userTagPostData ="{\"openid\" : \"oTrNHsypJNt-FKM5gC_Ma7txsSBM\"}";
//		JSONObject userResult = WxUtil.createPostMsg(userTagStr,userTagPostData);
//		System.out.println(userResult);
//		//获得所有标签
//		JSONObject tagList = WxUtil.createGetMsg("https://api.weixin.qq.com/cgi-bin/tags/get?access_token=");
//		System.out.println(tagList);
//		//打标签
//		String tagUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=";
//		String tagPostData ="{\"openid_list\" : [\"oTrNHsypJNt-FKM5gC_Ma7txsSBM\"],\"tagid\" : 2}";
//		JSONObject tagResult = WxUtil.createPostMsg(tagUrl, tagPostData);
//		System.out.println(tagResult);
		//获得标签下所有用户
		String urlString = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=";
		String postData="{\"tagid\" : 100,\"next_openid\":\"\"}";
		JSONObject resultList = WxUtil.createPostMsg(urlString,postData);
		System.out.println(resultList);
	}

}
