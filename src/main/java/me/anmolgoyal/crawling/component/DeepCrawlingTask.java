package me.anmolgoyal.crawling.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import me.anmolgoyal.crawling.data.Page;
import me.anmolgoyal.crawling.exception.SystemException;

/**
 * It take the request from CrawlingTask. 
 * it hit the given url and find the total no of images, all links on that page , title for given url.
 * than is pass on the information back to CrawlingTask
 * @author anmgoyal
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeepCrawlingTask implements Callable<DeepCrawlingResponse> {

	final static Logger log = LogManager.getLogger(DeepCrawlingTask.class);

	@Value("#{new Integer('${crawlTimeOut}')}")
	private int crawlTimeOut;

	@Value("#{new Boolean('${isFollowRedirectsAllowed}')}")
	private boolean isFollowRedirectsAllowed;

	private String url;
	private int depth;
	private int level;

	@Override
	public DeepCrawlingResponse call() throws Exception {
		return crawl();
	}

	private DeepCrawlingResponse crawl() {

		log.info("Fetching contents for url: {}", url);
		DeepCrawlingResponse deepCrawlingResponse = new DeepCrawlingResponse();
		Page pageInfo = new Page();
		deepCrawlingResponse.setPage(pageInfo);
		try {
			Document doc = Jsoup.connect(url).timeout(crawlTimeOut).followRedirects(isFollowRedirectsAllowed).get();

			Elements links = null;
			if(level != depth) {
				/** .select returns a list of links here **/
				links = doc.select("a[href]");
				getUrlsFromElements(links, deepCrawlingResponse);
			}else {
				deepCrawlingResponse.setLinks(Collections.EMPTY_LIST);
			}
			
			deepCrawlingResponse.setDepthLevel(depth);
			String title = doc.title();

			log.debug("Fetched title: {} for url: {}", title, url);

			pageInfo = deepCrawlingResponse.getPage();

			Elements imageTags = doc.select("img");
			pageInfo.setImageCount(imageTags.size());
			pageInfo.setPageTitle(title);
			pageInfo.setPageLink(url);

		} catch (final IOException | IllegalArgumentException e) {
			log.error(String.format("Error getting contents of url %s", url), e);
			throw new SystemException(e.getMessage(), String.format("Error getting contents of url %s", url));
		}

		return deepCrawlingResponse;

	}

	private void getUrlsFromElements(Elements links, DeepCrawlingResponse deepCrawlingResponse) {
		List<String> urls = new ArrayList<>(links.size());
		for (Element link : links) {
			urls.add(link.attr("abs:href"));
		}

		deepCrawlingResponse.setLinks(urls);

	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
