package myf.caption3.demo3_6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import myf.caption3.demo3_4.WxUtil;

/**
 * 下载临时素材
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>11 30, 2016			muyunfei			新建</p>
 */
public class DownloadFile2WXMain {
	public static void main(String[] args) {
		String mediaId = "gVLF549kak7j9n5gE4MgY6YnB8v13i6ptHnY1FNsujqn-ith8xV4fkUESbF6Vx6w";
		HttpServletResponse resp = null;
		//HttpServletRequest request = ServletActionContext.getRequest();
		try {
			InputStream in = WxUtil.downloadFile(mediaId, resp);
			//保存文件到本地
			 if(null!=in){
				 OutputStream outputStream = new FileOutputStream(new File("G:\\微信下载图片\\aaa.jpg"));
				 byte[] bytes = new byte[1024];  
				 int cnt=0;  
				 while ((cnt=in.read(bytes,0,bytes.length)) != -1) {  
					 outputStream.write(bytes, 0, cnt);  
				 }  
				 outputStream.flush();
				 outputStream.close();  
				 in.close();  
				 System.out.println("图片下载、保存成功");
			 }else{
				//图片获取失败，显示默认图片
				System.out.println("图片获取失败");
			 }
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
