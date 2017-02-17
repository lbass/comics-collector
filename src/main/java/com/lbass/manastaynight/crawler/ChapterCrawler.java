package com.lbass.manastaynight.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lbass.manastaynight.dto.ChapterBean;

@Component("chapterCrawler")
public class ChapterCrawler {
	
	private static Logger logger = LoggerFactory.getLogger(ChapterCrawler.class);
	
	public ChapterBean runCrawling(String chapterURI) {
		String[] result = null;
	    Document doc = null;
	    ChapterBean chapterBean = null;
	    
		try {
			doc = Jsoup.connect(chapterURI).get();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
		String title = doc.getElementsByClass("post-labels").first().select("a[href]").first().text();
		Element chpterTitleNode = doc.getElementsByClass("post-title entry-title").select("h3[itemprop]").first();
		
		Element postBody = doc.getElementsByClass("post-body entry-content").first();
		Elements imageLinks = postBody.select("img[src]");
		//첫 아이언맨 이미지는 버린다.
		imageLinks.remove(0);
		
		result = new String[imageLinks.size()];
		int i = 0;
		for(Element link : imageLinks) {
			result[i++] = link.attr("src");
		}
		

		Element chpterTitleChildNode = doc.getElementsByClass("post-body entry-content").first();
		chpterTitleChildNode.remove();
		String chapterName = chpterTitleNode.text();
		chapterBean = new ChapterBean(chapterURI, chapterName, title, result);

	    return chapterBean;
	}
	
	
}
