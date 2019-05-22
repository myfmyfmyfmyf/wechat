/******************************************************************************
 * Copyright (C) 2012-2018 Yantai HaiYi Software Co., Ltd
 * All Rights Reserved.
 * 本软件为烟台海颐软件开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package myf.caption4.demo4_1;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myf.caption4.QQTool.AesException;
import myf.caption4.demo4_1.service.CoreService;
import myf.caption4.util.WxUtil;

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
public class CoreServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 请求校验（确认请求来自微信服务器）
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		System.out.println("signature:"+signature);
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		System.out.println("timestamp:"+timestamp);
		// 随机数
		String nonce = request.getParameter("nonce");
		System.out.println("nonce:"+nonce);
		// 随机字符串
		String echostr = request.getParameter("echostr");
		System.out.println("echostr:"+echostr);
		
		
		String sToken = WxUtil.respMessageToken;
//		String sEncodingAESKey = WxUtil.respMessageEncodingAesKey;
//		String appId = WxUtil.messageAppId;
		try {
//			WXBizMsgCrypt pc = new WXBizMsgCrypt(sToken, sEncodingAESKey, appId);
//			String sEchoStr = pc.verifyUrl(signature, timestamp, nonce, echostr);
//			System.out.println("verifyurl sEchoStr: " + sEchoStr);
			String sEchoStr=""; //需要返回的明文
			//验证签名
			String sigStr = getSHA1(sToken, timestamp,nonce);
			if(sigStr.equals(signature)){
				sEchoStr=echostr;
			}
			System.out.println("verifyurl sigStr: " + sigStr);
			System.out.println("verifyurl sEchoStr: " + sEchoStr);
			// 验证URL成功，将sEchoStr返回
			PrintWriter out = response.getWriter();
			out.write(sEchoStr);
			out.flush();
			out.close();
		} catch (Exception e) {
			//验证URL失败，错误原因请查看异常
			e.printStackTrace();
		}

	}
	
	//加密签名
	public  String getSHA1(String token, String timestamp, String nonce) throws AesException
	  {
		try {
			String[] array = new String[] { token, timestamp, nonce };
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < 3; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();
		
			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private CoreService service=new CoreService();
	
	/**
	 * 处理微信服务器发来的消息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//读取消息，执行消息处理
		
		service.processRequest(request,response);
	}
}
