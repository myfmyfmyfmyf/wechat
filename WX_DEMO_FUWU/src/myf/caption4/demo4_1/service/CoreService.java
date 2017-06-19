/******************************************************************************
 * Copyright (C) 2012-2018 Yantai HaiYi Software Co., Ltd
 * All Rights Reserved.
 * 本软件为烟台海颐软件开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package myf.caption4.demo4_1.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import myf.caption4.QQTool.WXBizMsgCrypt;
import myf.caption4.util.WxUtil;
import myf.caption4.vo.TextMessage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 
 * 
 * @author muyunfei
 * 
 * <p>Modification History:</p> 
 * <p>Date       Author      Description</p>
 * <p>------------------------------------------------------------------</p>
 * <p>Aug 5, 2016           牟云飞       		 新建</p>
 */
public class CoreService {
	
	
	//处理微信消息
	public  void processRequest(HttpServletRequest request,HttpServletResponse response) {
		// 微信加密签名
		String sReqMsgSig = request.getParameter("msg_signature");
		//System.out.println("msg_signature :"+sReqMsgSig);
		// 时间戳
		String sReqTimeStamp = request.getParameter("timestamp");
		//System.out.println("timestamp :"+sReqTimeStamp);
		// 随机数
		String sReqNonce = request.getParameter("nonce");
		//System.out.println("nonce :"+sReqNonce);
		//加密类型
		String encryptType =request.getParameter("encrypt_type");
		//System.out.println("加密类型："+encrypt_type);
		String sToken = WxUtil.respMessageToken;
		String appId = WxUtil.messageAppId;
		String sEncodingAESKey = WxUtil.respMessageEncodingAesKey;
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// post请求的密文数据
			// sReqData = HttpUtils.PostData();
			ServletInputStream in = request.getInputStream();
			BufferedReader reader =new BufferedReader(new InputStreamReader(in));
			String sReqData="";
			String itemStr="";//作为输出字符串的临时串，用于判断是否读取完毕
			while(null!=(itemStr=reader.readLine())){
				sReqData+=itemStr;
			}
			//输出解密前的文件
//			System.out.println("after decrypt msg: " + sReqData);
			String sMsg=sReqData;
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, appId);
			if(encryptType!=null){
				//对消息进行处理获得明文
				sMsg = wxcpt.decryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sReqData);
			}
			//输出解密后的文件
			System.out.println("after decrypt msg: " + sMsg);
			// TODO: 解析出明文xml标签的内容进行处理
			// For example:
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(sMsg);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			//判断类型
			NodeList nodelistMsgType = root.getElementsByTagName("MsgType");
			String recieveMsgType = nodelistMsgType.item(0).getTextContent();
			String content="";
			if("text".equals(recieveMsgType)){//如果是文本消息
				//获得内容
				NodeList nodelist1 = root.getElementsByTagName("Content");
				//设置响应内容
				content = nodelist1.item(0).getTextContent();
				System.out.println("content:"+content);
				//昵称、解决乱码问题
				content=new String(content.getBytes("ISO-8859-1"),"UTF-8");
				System.out.println("---content:"+content);
			}else if("event".equals(recieveMsgType)){//如果是事件
				//获得事件类型
				NodeList nodelist1 = root.getElementsByTagName("Event");
				String eventType = nodelist1.item(0).getTextContent();
				if("subscribe".equals(eventType)){//关注
					//subscribe(root);
					content="欢迎关注“广州电动汽车充换电运营服务支撑平台”微信公众号";
					//
					
				}else if("unsubscribe".equals(eventType)){//取消关注
					//unSubscribe(root);
				}else if("CLICK".equals(eventType)){//取消关注
					//获取eventKey
					NodeList EventKeyNode = root.getElementsByTagName("EventKey");
					String EventKeyNodeContext = EventKeyNode.item(0).getTextContent();
					if("KF_TEL".equals(EventKeyNodeContext)){
						//客服电话
						content="客服电话：95598\r\n" +
								"服务时间：00:00-24:00";
					}
				}
				
			}
			//!!!!!!!!!!!!!!!!!!!设置回复！！！！！！！！！！
			//-------------------------------------------
			//回复人
			NodeList nodelistFromUser = root.getElementsByTagName("FromUserName");
			String mycreate = nodelistFromUser.item(0).getTextContent();
			//回复人
			NodeList nodelistToUserName = root.getElementsByTagName("ToUserName");
			String wxDevelop = nodelistToUserName.item(0).getTextContent();
			//回复人
			//时间
			String time=new Date().getTime()+"";
			//content="被动响应消息:"+content;
			
			//临时消息
			//content="";
			//生成一个被动响应的消息
			TextMessage txtMsg= new TextMessage();
			txtMsg.setContent(content);//文字内容
			txtMsg.setCreateTime(Long.valueOf(time));//创建时间
			txtMsg.setFromUserName(wxDevelop);//消息来源
			txtMsg.setMsgType(WxUtil.RESP_MESSAGE_TYPE_TEXT);//消息类型
			txtMsg.setToUserName(mycreate);
			String sRespData=WxUtil.messageToXml(txtMsg);
			String sEncryptMsg=sRespData;
			if(encryptType!=null){
				sEncryptMsg = wxcpt.encryptMsg(sRespData, time, sReqNonce);
			}
//			System.out.println("回复消息："+sRespData);
//			System.out.println("回复消息加密："+sEncryptMsg);
			//

			//输出
			PrintWriter out = response.getWriter();
			out.write(sEncryptMsg);
			out.flush();
			out.close();
			
		} catch (Exception e) {
			// TODO
			// 解密失败，失败原因请查看异常
			e.printStackTrace();
		}
	}
	
//	/**
//	 * 订阅事件处理；
//	 */
//	public void  subscribe(Element root){
//		//哪个员工进入
//		NodeList enter_people_note = root.getElementsByTagName("FromUserName");
//		String enter_people = enter_people_note.item(0).getTextContent();
//		//根据openid获取数据库人员
//		QueryParamList params = new QueryParamList();
//		params.addParam("openid", enter_people);
//		WxPerson person = null;
//		List<WxPerson> list = JPAUtil.load(WxPerson.class, params, null, null, null, null);
//		if(null==list||0==list.size()){
//			person=new WxPerson();
//			person.setPersonId(SequenceUtil.genEntitySequenceNo(WxPerson.class));
//		}else{
//			person=list.get(0);
//		}
//		
//		//根据人员id获得账户信息
//		WxUtil util = new WxUtil();
//		JSONObject json=util.getPeopleByOpenId(enter_people);
//		//获取微信openid
//		Object wxId=json.get("openid");
//		person.setOpenid(json.get("openid")==null?"":(json.get("openid")+""));
//		
//		person.setGroupid(json.get("groupid")==null?null:Long.valueOf(json.get("groupid")+""));
//		person.setHeadimgurl(json.get("headimgurl")==null?"":(json.get("headimgurl")+""));
//		person.setLanguage(json.get("language")==null?"":(json.get("language")+""));
//		
//		try {
//			//昵称、解决乱码问题
//			String nickName=json.get("nickname")==null?"":(json.get("nickname")+"");
//			nickName=new String(nickName.getBytes("ISO-8859-1"),"UTF-8");
//			person.setNickname(nickName);
//			//省份
//			String province=json.get("province")==null?"":(json.get("province")+"");
//			province=new String(province.getBytes("ISO-8859-1"),"UTF-8");
//			person.setProvince(province);
//			String city=json.get("city")==null?"":(json.get("city")+"");
//			city=new String(city.getBytes("ISO-8859-1"),"UTF-8");
//			person.setCity(city);
//			String country=json.get("country")==null?"":(json.get("country")+"");
//			country=new String(country.getBytes("ISO-8859-1"),"UTF-8");
//			person.setCountry(country);
//			String remark=json.get("remark")==null?"":(json.get("remark")+"");
//			remark=new String(remark.getBytes("ISO-8859-1"),"UTF-8");
//			person.setRemark(remark);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		person.setSex(json.get("sex")==null?"":(json.get("sex")+""));
//		person.setSubscribe(json.get("subscribe")==null?"":(json.get("subscribe")+""));
//		person.setSubscribeTime(json.get("subscribe_time")==null?"":(json.get("subscribe_time")+""));
//		person.setTagidList(json.get("tagid_list")==null?"":(json.get("tagid_list")+""));
//		person.setUnionid(json.get("unionid")==null?"":(json.get("unionid")+""));
//		JPAUtil.saveOrUpdate(person);
//	}
//	
//	/**
//	 * 取消订阅
//	 * @param root
//	 */
//	public void  unSubscribe(Element root){
//		//哪个员工进入
//		NodeList enter_people_note = root.getElementsByTagName("FromUserName");
//		String enter_people = enter_people_note.item(0).getTextContent();
//		//根据openid获取数据库人员
//		QueryParamList params = new QueryParamList();
//		params.addParam("openid", enter_people);
//		WxPerson person = null;
//		List<WxPerson> list = JPAUtil.load(WxPerson.class, params, null, null, null, null);
//		if(null!=list&&0!=list.size()){
//			person=list.get(0);
//			//值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
//			person.setSubscribe("0");
//			JPAUtil.saveOrUpdate(person);
//		}
//	}
}
