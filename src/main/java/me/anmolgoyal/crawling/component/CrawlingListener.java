package me.anmolgoyal.crawling.component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import me.anmolgoyal.crawling.data.CrawlRequest;

/**
 * It listening to requestQueue. For every new request it create crawling task.
 * it submit the crawling request to executorPool
 * @author anmgoyal
 *
 */
@Component
public class CrawlingListener implements Runnable {

	final static Logger log = LogManager.getLogger(CrawlingListener.class);
	
	@Autowired
	private BlockingQueue<CrawlRequest> requestQueue;
	
	@Autowired
	private Executor executorPool;
	
	@PostConstruct
	public void postProcessor(){
		//creating a listener thread which will be listening on blocking queue
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {

		while(true) {
			
			try {
				CrawlRequest crawlRequest = requestQueue.poll();
				CrawlingTask deepCrawlingTask = getPrototypeBean();
				deepCrawlingTask.setCrawlRequest(crawlRequest);
				executorPool.execute(deepCrawlingTask);
				
			} catch (RuntimeException e) {
				log.error("Error while taking the request from requestQueue", e);
			}
		}
		
		
	}
	
	
	@Lookup
    public CrawlingTask getPrototypeBean() {
        return null;
    }
}
