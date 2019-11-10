package me.anmolgoyal.crawling.service.impl;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.anmolgoyal.crawling.data.AcknowledgementToken;
import me.anmolgoyal.crawling.data.CrawlRequest;
import me.anmolgoyal.crawling.data.CrawlResponse;
import me.anmolgoyal.crawling.enums.CrawlStatus;
import me.anmolgoyal.crawling.exception.SystemException;
import me.anmolgoyal.crawling.service.CrawlingService;
import me.anmolgoyal.crawling.service.TokenGenerationService;

@Service
public class CrawlingServiceImpl implements CrawlingService {

	@Autowired
	private TokenGenerationService tokenGenerationService;

	@Autowired
	private Map<String, CrawlStatus> statusMap;

	@Autowired
	private Map<String, AcknowledgementToken> acknowledgementTokenMap;

	@Autowired
	private BlockingQueue<CrawlRequest> requestQueue;
	
	@Autowired
	private Map<String,CrawlResponse> crawlResponseMap;

	@Override
	public AcknowledgementToken doCrawling(String url, int depth) {
		AcknowledgementToken acknowledgementToken;
		String token = "";

		// doing thread safe if multiple calls coming for same url simultaneously
		synchronized (acknowledgementTokenMap) {
			// checking if request is already submitted or not
			if (acknowledgementTokenMap.containsKey(url)) {
				acknowledgementToken = new AcknowledgementToken(acknowledgementTokenMap.get(url));
				acknowledgementToken.setMessage("Request is already submitted for this Url");
				return acknowledgementToken;
			}

			token = tokenGenerationService.generateToken();
			acknowledgementToken = new AcknowledgementToken(token);
			acknowledgementToken.setMessage("Request is submitted for given Url");
			acknowledgementTokenMap.put(url, acknowledgementToken);
		}

		CrawlRequest crawlRequest = new CrawlRequest(url, token, depth);
		requestQueue.add(crawlRequest);
		statusMap.put(token, CrawlStatus.SUBMITTED);

		return acknowledgementToken;
	}

	@Override
	public CrawlStatus fetchStatus(String token) {
		return statusMap.get(token);
	}

	@Override
	public CrawlResponse fetchResponse(String token) {
		CrawlStatus crawlStatus = statusMap.get(token);
		CrawlResponse crawlResponse = null;
		switch(crawlStatus) {
		case IN_PROCESS:
			throw new SystemException("Your request is still undeprocessing", "Your request is still undeprocessing");
		case SUBMITTED:
			throw new SystemException("Your request is in pending state", "Your request is in pending state");
		case FAILED:
			throw new SystemException("sorry your request got failed.we are unable to process your request", "sorry your request got failed.we are unable to process your request");
		case PROCESSED:
			crawlResponse =  crawlResponseMap.get(token);
		}
		return crawlResponse;
	}

}
