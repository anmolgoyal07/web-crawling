package me.anmolgoyal.crawling.service;

import me.anmolgoyal.crawling.data.AcknowledgementToken;
import me.anmolgoyal.crawling.data.CrawlResponse;
import me.anmolgoyal.crawling.enums.CrawlStatus;

public interface CrawlingService {

	/**
	 * It do the web crawling for given url and upto the depth level provided
	 * @param url
	 * @param depth
	 * @return AcknowledgementToken
	 */
	public AcknowledgementToken doCrawling(String url, int depth);
	
	/**
	 * it fetch crawling status for given token
	 * @param token
	 * @return
	 */
	public CrawlStatus fetchStatus(String token);
	
	/**
	 * It gives the crawling response for given token
	 * @param token
	 * @return
	 */
	public CrawlResponse fetchResponse(String token);
}
