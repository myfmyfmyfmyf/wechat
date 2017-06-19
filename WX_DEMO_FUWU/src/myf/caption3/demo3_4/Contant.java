package myf.caption3.demo3_4;
/**
 * 微信请求链接
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class Contant {

	//创建菜单链接——默认菜单
	public static final String URL_MENU_DEFAULT = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	
	//创建菜单链接——权限菜单
	public static final String URL_MENU_CONDITION = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=";
	
	//查询菜单链接——权限菜单
	public static final String URL_MENU_QUERY = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
	
	//删除菜单链接
	public static final String URL_MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";
	
	//上传永久图文消息链接
	public static final String URL_NEWS_UPLOAD = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";
	
	//修改永久图文消息链接
	public static final String URL_NEWS_MODIFY = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=";
	
	//删除永久素材链接
	public static final String URL_NEWS_DEL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=";
	
	//获得总数
	public static final String URL_NEWS_NUM = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=";
	
	//群发消息接口链接
	public static final String URL_MSG_ALL = "";
	
	//模板消息接口链接
	public static final String URL_MSG_MODEL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	//48小时客服消息接口链接
	public static final String URL_MSG_AGENT = "";
}
