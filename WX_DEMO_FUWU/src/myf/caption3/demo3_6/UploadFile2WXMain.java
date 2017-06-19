package myf.caption3.demo3_6;

import net.sf.json.JSONObject;
import myf.caption3.demo3_4.WxUtil;

/**
 * 上传临时素材
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class UploadFile2WXMain {
	public static void main(String[] args) {
		JSONObject result = WxUtil.sendMedia("image", "E://404.png");
		System.out.println(result);
	}
}
