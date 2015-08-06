package com.cmbl.chemabuluo_kjf.share;

/***
 * 分享实体类
 * 
 * @author YuLing
 * @UpdateDate 2014-11-05 11:39:26
 */
public class ShareUnit {
	/***
	 * 信息的标题
	 */
	private String title;
	/***
	 * 信息的链接地址
	 */
	private String url;
	/***
	 * 信息的内容
	 */
	private String summary;
	/***
	 * 分享图片的URL或者本地路径
	 */
	private String imageUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
