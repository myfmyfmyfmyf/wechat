package myf.caption3.demo3_7;

import myf.caption3.demo3_4.WxUtil;
import net.sf.json.JSONObject;

/**
 * 删除群发消息
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class DeleteMsgAll {
	public static void main(String[] args) {
		String deleteUrlStr = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=";
		String jsonStr = "{\"msg_id\":3147483654,\"article_idx\":0}";
		JSONObject result = WxUtil.createPostMsg(deleteUrlStr, jsonStr);
		System.out.println(result.toString());
	}
}
