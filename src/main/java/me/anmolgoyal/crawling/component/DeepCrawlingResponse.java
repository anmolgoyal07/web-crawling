package me.anmolgoyal.crawling.component;

import java.util.List;

import me.anmolgoyal.crawling.data.Page;

public class DeepCrawlingResponse {
	
	private Page page;
	private int depthLevel;
	private List<String> links;
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public int getDepthLevel() {
		return depthLevel;
	}
	public void setDepthLevel(int depthLevel) {
		this.depthLevel = depthLevel;
	}
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	
	
	
}
