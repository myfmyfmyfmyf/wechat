package myf.caption3.demo3_6;

import net.sf.json.JSONObject;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import myf.caption3.demo3_4.Contant;
import myf.caption3.demo3_4.WxUtil;

/**
 * 获得已上传的永久素材个数
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public final class GetTotalNumMain {
public static void main(String[] args) {
	//createGetMsg封装主动调用get公用方法3.4节介绍
	JSONObject result = WxUtil.createGetMsg(Contant.URL_NEWS_NUM);
	System.out.println(result.toString());
	//返回结果
	//{"voice_count":0,"video_count":0,"image_count":16,"news_count":2}
}
}
