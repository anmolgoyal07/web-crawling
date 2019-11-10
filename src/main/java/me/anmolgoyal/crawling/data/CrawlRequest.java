package me.anmolgoyal.crawling.data;

public class CrawlRequest {
	private String url;
	private String token;
	private int depth;

	public CrawlRequest(String url, String token, int depth) {
		super();
		this.url = url;
		this.token = token;
		this.depth = depth;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

}
