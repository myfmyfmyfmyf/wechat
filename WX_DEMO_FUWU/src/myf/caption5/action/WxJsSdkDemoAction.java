package myf.caption5.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myf.caption5.util.WxUtil;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * 微信服务
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>8 4, 2016			muyunfei			新建</p>
 */
public class WxJsSdkDemoAction extends DispatchAction{

	//初始化考勤页面
	public ActionForward  initPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse response){
		//识别微信浏览器
		String userAgent=req.getHeader("User-Agent");//里面包含了设备类型
		if(-1==userAgent.indexOf("MicroMessenger")){
			//如果不是微信浏览器,跳转到安全页
			return mapping.findForward("safePage");
		}
		//生成微信js授权
		WxUtil msgUtil=new WxUtil();
		String jsapi_ticket=msgUtil.getJsapiTicketFromWx();//签名
		String url = WxUtil.webUrl //域名地址，自己的外网请求的域名，如：http://www.baidu.com/Demo
        		+ req.getServletPath()      //请求页面或其他地址  
        		+ "?" + (req.getQueryString()); //参数 
        Map<String, String> ret = WxUtil.sign(jsapi_ticket, url);
        req.setAttribute("messageAppId", WxUtil.messageAppId);
		req.setAttribute("str1", ret.get("signature"));
		req.setAttribute("time", ret.get("timestamp"));
		req.setAttribute("nonceStr", ret.get("nonceStr"));
		
		//获取网址
		req.setAttribute("addressUrl", WxUtil.webUrl);//范围
		return mapping.findForward("success");
	}
}
