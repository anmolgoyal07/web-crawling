package me.anmolgoyal.crawling.service;

import me.anmolgoyal.crawling.data.AcknowledgementToken;
import me.anmolgoyal.crawling.data.CrawlResponse;
import me.anmolgoyal.crawling.enums.CrawlStatus;

public interface CrawlingService {

	public AcknowledgementToken doCrawling(String url, int depth);
	public CrawlStatus fetchStatus(String token);
	public CrawlResponse fetchResponse(String token);
}
