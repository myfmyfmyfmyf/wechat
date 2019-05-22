package myf.caption3.demo3_6;

import myf.caption3.demo3_4.Contant;
import myf.caption3.demo3_4.WxUtil;
import net.sf.json.JSONObject;

/**
 * 删除永久素材
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class DeleteFileMain {
	public static void main(String[] args) {
		//通过3.6.2上传永久图片，获得mediaid
		//JSONObject imageResult = WxUtil.sendMedia("image", "E://404.png");
		String media_id = "TVwrM9MUyS-0ZusnXK0AfXh7r4iUv59JdFsD8hlHM_M";
		String jsonContextString ="{\"media_id\":\""+media_id+"\"}";
		//删除永久素材
		JSONObject result = WxUtil.createPostMsg(Contant.URL_NEWS_DEL, jsonContextString.toString());
		System.out.println(result);
		//返回结果
		//{"errcode":0,"errmsg":"ok"}
	}

}
