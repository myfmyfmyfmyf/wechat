package myf.caption2;

import java.io.StringReader;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * XML数据格式生成与解析
 *
 * @author 牟云飞
 *
 *<p>Modification History:</p>
 *<p>Date					Author			Description</p>
 *<p>------------------------------------------------------------------</p>
 *<p>8 4, 2016			muyunfei			新建</p>
 */
class XmlTestMain {
	public static void main(String[] args) {
		//通过com.thoughtworks.xstream生成XML
		XmlTestMain xmlClassMain = new XmlTestMain();
		User user = new User("牟云飞",123456);//内部类
		xmlClassMain.xstream.alias("xml", user.getClass());
		String xmlString = xmlClassMain.xstream.toXML(user);
		System.out.println(xmlString);
		
		//通过w3c解析数据
		try {
			//创建XML解析器
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmlString);
			InputSource is = new InputSource(sr);
			//获得XML各个标签（节点）信息
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			//获得节点名字为name的标签内容
			NodeList nodelist_name = root.getElementsByTagName("name");
			String name = nodelist_name.item(0).getTextContent();
			System.out.println("name:"+name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 扩展xstream使其支持CDATA
	 * 内部类XppDriver
	 */
	private  XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
	
}
