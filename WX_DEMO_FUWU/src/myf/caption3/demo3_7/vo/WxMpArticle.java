package myf.caption3.demo3_7.vo;
/**
 * @author    muyunfei    
 * 图文消息
 */
public class WxMpArticle {
	// 图文消息名称
	private String title;
	// 图文消息缩略图的media_id
	private String thumb_media_id;
	// 作者
	private String author;
	// 图文消息点击“阅读原文”之后的页面链接 
	private String content_source_url;
	// 内容，支持html标签，不超过666 K个字节 
	private String content;
	//图文消息的描述，不超过512个字节，超过会自动截断 
	private String digest;
	//是否显示封面，1为显示，0为不显示 
	private String show_cover_pic;
	
	public WxMpArticle(String title, String thumbMediaId, String author,
			String contentSourceUrl, String content, String digest,
			String showCoverPic) {
		super();
		this.title = title;
		thumb_media_id = thumbMediaId;
		this.author = author;
		content_source_url = contentSourceUrl;
		this.content = content;
		this.digest = digest;
		show_cover_pic = showCoverPic;
	}
	public String getTitle() {
		return title;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public String getAuthor() {
		return author;
	}
	public String getContent_source_url() {
		return content_source_url;
	}
	public String getContent() {
		return content;
	}
	public String getDigest() {
		return digest;
	}
	public String getShow_cover_pic() {
		return show_cover_pic;
	}
}


