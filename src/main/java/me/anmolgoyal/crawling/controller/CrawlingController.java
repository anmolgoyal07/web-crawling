package me.anmolgoyal.crawling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.anmolgoyal.crawling.data.AcknowledgementToken;
import me.anmolgoyal.crawling.data.CrawlResponse;
import me.anmolgoyal.crawling.enums.CrawlStatus;
import me.anmolgoyal.crawling.service.CrawlingService;
/**
 * Front hand controller for all the crawling request
 * @author anmgoyal
 *
 */
@RestController
public class CrawlingController {
	
	@Autowired
	private CrawlingService crawlingService;
	
	@PostMapping("/crawling")
	public AcknowledgementToken doCrawling(@RequestParam String url,@RequestParam int depth) {
		return crawlingService.doCrawling(url, depth);
	}
	
	@GetMapping("/crawling")
	public CrawlStatus fetchStatus(@RequestParam String token) {
		return crawlingService.fetchStatus(token);
	}
	
	@GetMapping("/crawling/result")
	public CrawlResponse fetchResponse(@RequestParam String token) {
		return crawlingService.fetchResponse(token);
	}
}
