package myf.caption3.demo3_7.vo;

import java.util.List;

/**
 * 文章数组类，用于生成json格式
 */
public class WxArticleList {
	private List<WxMpArticle> articles;
	public WxArticleList(List<WxMpArticle> articles) {
		super();
		this.articles = articles;
	}
	public List<WxMpArticle> getArticles() {
		return articles;
	}
	public void setArticles(List<WxMpArticle> articles) {
		this.articles = articles;
	}
}


