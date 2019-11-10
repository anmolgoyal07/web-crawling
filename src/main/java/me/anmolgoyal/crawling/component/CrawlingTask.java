package me.anmolgoyal.crawling.component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import me.anmolgoyal.crawling.data.CrawlRequest;
import me.anmolgoyal.crawling.data.CrawlResponse;
import me.anmolgoyal.crawling.enums.CrawlStatus;

/**
 * it takes the request from requestQueue and make a DeepCrawlingTask task.
 * For each link it make DeepCrawlingTask and submit this to deepCrawlingExecutorPool for parallel execution
 * 
 * @author anmgoyal
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CrawlingTask implements Runnable {

	final static Logger log = LogManager.getLogger(CrawlingTask.class);

	private CrawlRequest crawlRequest;

	@Autowired
	private Map<String, CrawlStatus> statusMap;

	@Autowired
	private Map<String, CrawlResponse> crawlResponseMap;

	@Autowired
	private ExecutorService deepCrawlingExecutorPool;

	/**
	 * Using for visiting the link is url
	 */
	private LinkedList<String> links;

	private CrawlResponse crawlResponse;
	
	private Set<String> uniqueLinks;

	@PostConstruct
	public void postProcessor() {
		links = new LinkedList<>();
		crawlResponse = new CrawlResponse();
		uniqueLinks = new HashSet<>();
	}

	@Override
	public void run() {
		boolean requestProcessed = false;

		try {
			statusMap.replace(crawlRequest.getToken(), CrawlStatus.IN_PROCESS);
			processDeepCrawling();
			requestProcessed = true;
		} catch (Exception e) {
			statusMap.replace(crawlRequest.getToken(), CrawlStatus.FAILED);
			log.error(String.format("Error getting crawling request for url %s", crawlRequest.getUrl()), e);
		}

		if (requestProcessed) {
			statusMap.replace(crawlRequest.getToken(), CrawlStatus.PROCESSED);
		}

	}

	private void processDeepCrawling() {

		int depth = crawlRequest.getDepth();
		links.add(crawlRequest.getUrl());
		int level = 0;

		int i;
		while (level <= depth) {

			Future<DeepCrawlingResponse> deepCrawlingFuture[] = new Future[links.size()];
			i = 0;
			while (!links.isEmpty()) {

				String url = links.removeFirst();
				
				if(uniqueLinks.contains(url)) {
					continue;
				}
				uniqueLinks.add(url);
				
				DeepCrawlingTask deepCrawlingTask = getDeepCrawlingTask();
				deepCrawlingTask.setDepth(depth);
				deepCrawlingTask.setUrl(url);
				deepCrawlingTask.setLevel(level);

				deepCrawlingFuture[i++] = deepCrawlingExecutorPool.submit(deepCrawlingTask);

			}
			// getting response from deep crawling task
			getResponseFromCrawling(deepCrawlingFuture);

			level++;
		}

		crawlResponseMap.put(crawlRequest.getToken(), crawlResponse);

	}

	/**
	 * 
	 * @param deepCrawlingFuture
	 */
	private void getResponseFromCrawling(Future<DeepCrawlingResponse> deepCrawlingFuture[]) {
		for (int j = 0; j < deepCrawlingFuture.length; j++) {
			DeepCrawlingResponse deepCrawlingResponse = null;
			try {
				if ( deepCrawlingFuture[j]!=null) {
					deepCrawlingResponse = deepCrawlingFuture[j].get();
				}
			} catch (Exception e) {
				log.error(String.format("Error while geting the future result for url %s", crawlRequest.getUrl()), e);
			}
			if (deepCrawlingResponse != null) {
				links.addAll(deepCrawlingResponse.getLinks());

				crawlResponse.addPage(deepCrawlingResponse.getPage());

				crawlResponse.updateInfo(deepCrawlingResponse.getLinks().size(),
						deepCrawlingResponse.getPage().getImageCount());
			}

		}
	}

	@Lookup
	public DeepCrawlingTask getDeepCrawlingTask() {
		return null;
	}

	public void setCrawlRequest(CrawlRequest crawlRequest) {
		this.crawlRequest = crawlRequest;
	}

}
