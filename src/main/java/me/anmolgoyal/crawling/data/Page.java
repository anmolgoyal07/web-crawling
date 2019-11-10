package me.anmolgoyal.crawling.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Page {
	@JsonProperty("page_title")
	private String pageTitle;
	
	@JsonProperty("page_link")
	private String pageLink;
	
	@JsonProperty("image_count")
	private int imageCount;
	

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageLink() {
		return pageLink;
	}

	public void setPageLink(String pageLink) {
		this.pageLink = pageLink;
	}

	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}

	@Override
	public String toString() {
		return String.format("Page [pageTitle=%s, pageLink=%s, imageCount=%s]", pageTitle, pageLink, imageCount);
	}

}
