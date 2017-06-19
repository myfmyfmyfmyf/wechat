package myf.caption3.demo3_4;

import java.io.UnsupportedEncodingException;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONObject;

/**
 * 菜单
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class MetuTestMain {
	public static void main(String[] args) {
//		//创建默认菜单
//		String jsonStr_DEFAULT ="{"
//					     +"\"button\":["
//					     +"{	"
//					     +"     \"type\":\"click\","
//					     +"     \"name\":\"今日歌曲\","
//					     +"     \"key\":\"V1001_TODAY_MUSIC\""
//					     +" },{"
//					     +"      \"name\":\"天籁之音\","
//					     +"      \"sub_button\":["
//					     +"      {	"
//					     +"          \"type\":\"view\","
//					     +"          \"name\":\"子菜单一\","
//					     +"          \"url\":\"http://www.muyunfei.com/\""
//					     +"       },"
//					     +"       {"
//					     +"          \"type\":\"click\","
//					     +"          \"name\":\"子菜单二\","
//					     +"          \"key\":\"V1001_GOOD\""
//					     +"       }]"
//					     +"  },{	"
//					     +"     \"type\":\"click\","
//					     +"     \"name\":\"我的歌曲\","
//					     +"     \"key\":\"V1001_TODAY_MUSIC\""
//					     +" }]"
//					     +"}";
//		JSONObject result = WxUtil.createPostMsg(Contant.URL_MENU_DEFAULT, jsonStr_DEFAULT);
//---------------------------------------------------------------------------------------------		
//		//创建权限菜单
//		String jsonStr_Condition ="{"
//		     +"\"button\":["
//		     +"{	"
//		     +"     \"type\":\"click\","
//		     +"     \"name\":\"今日歌曲\","
//		     +"     \"key\":\"V1001_TODAY_MUSIC\""
//		     +" },{"
//		     +"      \"name\":\"权限菜单\","
//		     +"      \"sub_button\":["
//		     +"      {	"
//		     +"          \"type\":\"view\","
//		     +"          \"name\":\"权限子菜单一\","
//		     +"          \"url\":\"http://www.muyunfei.com/\""
//		     +"       },"
//		     +"       {"
//		     +"          \"type\":\"click\","
//		     +"          \"name\":\"权限子菜单二测试菜单情况\","
//		     +"          \"key\":\"V1001_GOOD\""
//		     +"       }]"
//		     +"  },{	"
//		     +"     \"type\":\"click\","
//		     +"     \"name\":\"我的账户\","
//		     +"     \"key\":\"V1001_TODAY_MUSIC\""
//		     +" }],"
//		     +"	\"matchrule\":{"
//		     +"	  \"country\":\"中国\","
//		     +"	  \"province\":\"山东\""
//		     +"	  }\""
//		     +"}";
//		JSONObject result = WxUtil.createPostMsg(Contant.URL_MENU_CONDITION, jsonStr_Condition);
//        System.out.println(result);
//---------------------------------------------------------------------------------------------			
//		//查询菜单
//		JSONObject result = WxUtil.createGetMsg(Contant.URL_MENU_QUERY);
//		try {
//			System.out.println(new String((result.toString()).getBytes("ISO-8859-1"),"UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//---------------------------------------------------------------------------------------------			
		//删除菜单
		JSONObject result = WxUtil.createGetMsg(Contant.URL_MENU_DELETE);
		System.out.println(result.toString());
	}
}
