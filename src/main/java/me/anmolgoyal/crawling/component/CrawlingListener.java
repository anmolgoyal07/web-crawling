package me.anmolgoyal.crawling.component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import me.anmolgoyal.crawling.data.CrawlRequest;

@Component
public class CrawlingListener implements Runnable {

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
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	
	@Lookup
    public CrawlingTask getPrototypeBean() {
        return null;
    }
}
