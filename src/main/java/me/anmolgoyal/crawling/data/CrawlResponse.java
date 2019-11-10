package me.anmolgoyal.crawling.data;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrawlResponse {
	
	@JsonProperty("total_links")
	private int totalLinks;
	
	@JsonProperty("total_images")
	private int totalImages;
	
	private List<Page> details;
	
	public CrawlResponse() {
		details = new LinkedList<Page>();
	}
	
	public int getTotalLinks() {
		return totalLinks;
	}
	public void setTotalLinks(int totalLinks) {
		this.totalLinks = totalLinks;
	}
	public int getTotalImages() {
		return totalImages;
	}
	public void setTotalImages(int totalImages) {
		this.totalImages = totalImages;
	}
	public List<Page> getDetails() {
		return details;
	}
	public void setDetails(List<Page> details) {
		this.details = details;
	}
	
	public boolean addPage(Page page) {
		return details.add(page);
	}
	
	public boolean updateInfo(int totalLinks , int totalImages) {
		synchronized (this) {
			this.totalLinks += totalLinks;
			this.totalImages += totalImages;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("CrawlResponse [totalLinks=%s, totalImages=%s, details=%s]", totalLinks, totalImages,
				details);
	}
	
	
}
