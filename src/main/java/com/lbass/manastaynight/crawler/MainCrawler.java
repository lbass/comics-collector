package com.lbass.manastaynight.crawler.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lbass.manastaynight.config.AppConfig;
import com.lbass.manastaynight.config.AppConfig.AppConfigEnum;
import com.lbass.manastaynight.crawler.Crawler;
import com.lbass.manastaynight.util.Utils;
import com.lbass.manastaynight.vo.ChapterBean;
import com.lbass.manastaynight.vo.MainColletingBean;


//메인 최신 500개 업데이트 수 
@Component("MainCrawler")
public class MainCrawler implements Crawler<MainColletingBean> {
	private static Logger logger = LoggerFactory.getLogger(MainCrawler.class);
	private final static String DEFAULT_URL = "http://manastaynight09.blogspot.kr/";
	
	@Override
	public MainColletingBean runCrawling() {
		MainColletingBean bean = new MainColletingBean();
		String startDate = Utils.getCurrentData();
		logger.debug(" Start Date : " + startDate);
		bean.setCollecDate(startDate);

		WebDriver driver = new SafariDriver();
		String dom = null;
		try {
			driver.get(AppConfig.getPropertyValue(AppConfigEnum.DEFAULT_URL));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.tagName("ul").className("rpw"));

			dom = driver.getPageSource();
		} catch (Exception e) {
		} finally {
			driver.quit();
		}

		List<ChapterBean> updateList = new ArrayList<ChapterBean>();
		Document doc = Jsoup.parse(dom);
		Element rpwElement = doc.getElementsByClass("rpw").first();
		Elements links = rpwElement.select("a[href]");
		for (Element link : links) {
			String aHref = link.attr("href");
			String comicChaterName = Utils.stringReplace(link.tagName("strong").text());

			logger.debug(aHref);
			logger.debug(comicChaterName);
			
			ChapterBean chapterBean = null;
			try {
				URI chaterURI = new URI(aHref);
				chapterBean = new ChapterBean(chaterURI, comicChaterName);
			} catch (URISyntaxException e) {
				String errorMessage = "[" + aHref + "] is not correct";
				logger.error("[" + aHref + "] is not correct");
				chapterBean = new ChapterBean(comicChaterName, true, errorMessage);
			}
			updateList.add(chapterBean);				
		}
		bean.setChapterBeans(updateList);
		logger.debug(" End Date : " + Utils.getCurrentData());
		return bean;
	}
	
}
