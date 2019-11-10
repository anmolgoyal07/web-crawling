package me.anmolgoyal.crawling.configuration;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.anmolgoyal.crawling.component.CrawlLinkedBlockingQueue;
import me.anmolgoyal.crawling.data.AcknowledgementToken;
import me.anmolgoyal.crawling.data.CrawlRequest;
import me.anmolgoyal.crawling.data.CrawlResponse;
import me.anmolgoyal.crawling.enums.CrawlStatus;

@Configuration
public class CrawlingConfiguration {
	
	@Value("#{new Integer('${listenerNoOfThreads}')}")
	private int listenerNoOfThreads;
	
	@Value("#{new Integer('${crawlNoOfThreads}')}")
	private int crawlNoOfThreads;
	
	@Bean
	public Map<String,CrawlStatus> statusMap(){
		return new ConcurrentHashMap<>();
	}
	
	@Bean
	public Map<String,AcknowledgementToken> acknowledgementTokenMap(){
		return new ConcurrentHashMap<>();
	}
	
	@Bean
	public Map<String,CrawlResponse> crawlResponseMap(){
		return new ConcurrentHashMap<>();
	}
	
	@Bean
	public BlockingQueue<CrawlRequest> requestQueue(){
		return new CrawlLinkedBlockingQueue<>();
	}
	
	@Bean
	public Executor executorPool() {
		return Executors.newFixedThreadPool(listenerNoOfThreads);
	}
	
	@Bean
	public ExecutorService deepCrawlingExecutorPool() {
		return Executors.newFixedThreadPool(crawlNoOfThreads);
	}
	
}
