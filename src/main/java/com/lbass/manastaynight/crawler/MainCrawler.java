package com.lbass.manastaynight.crawler;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lbass.common.utils.Utils;


//메인 최신 500개 업데이트 수 
@Component("MainCrawler")
public class MainCrawler {
	private static Logger logger = LoggerFactory.getLogger(MainCrawler.class);
	
	@Value("${msn.main.uri}")
	private String MANASTAYNIGHT_MAIN_URI;
	
	public List<String> runCrawling() {
		
		WebDriver driver = new SafariDriver();
		String dom = null;
		
		driver.get(MANASTAYNIGHT_MAIN_URI);
		
//case 1: 보안페이지 표시 시
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		WebElement button = driver.findElement(By.className("maia-button maia-button-primary"));
//		String realURI = button.getAttribute("href");
//		driver.get(realURI);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.tagName("ul").className("rpw"));		
		dom = driver.getPageSource();
		driver.quit();

		List<String> updateList = new ArrayList<String>();
		Document doc = Jsoup.parse(dom);
		Element rpwElement = doc.getElementsByClass("rpw").first();
		Elements links = rpwElement.select("a[href]");

		for (Element link : links) {
			String aHref = link.attr("href");
			//String comicChaterName = Utils.stringReplace(link.tagName("strong").text());

			String chaterURI = null;
			chaterURI = aHref;
			
			updateList.add(chaterURI);				
		}
		return updateList;
	}
	
}
