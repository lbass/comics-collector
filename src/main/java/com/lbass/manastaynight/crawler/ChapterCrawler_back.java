package com.lbass.manastaynight.crawler;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbass.common.utils.Utils;
import com.lbass.manastaynight.dto.ChapterBean;

public class ChapterCrawler_back {
	private static Logger logger = LoggerFactory.getLogger(ChapterCrawler_back.class);
	
	public ChapterBean runCrawling(URI chapterURI) {
		logger.debug(" Start Date : " + Utils.getCurrentDate());
		String[] result = null;
	    Document doc = null;
	    ChapterBean chapterBean = null;
	    
		WebDriver driver = new SafariDriver();
		String dom = null;
		try {
			driver.get(chapterURI.toString());
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			RemoteWebElement button = (RemoteWebElement)driver.findElement(By.className("maia-button maia-button-primary"));
			String realURI = button.getAttribute("href");
			driver.get(realURI);
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//driver.findElement(By.tagName("ul").className("rpw"));
			dom = driver.getPageSource();
		} catch (Exception e) {
			
		} finally {
			driver.quit();
		}
		
		doc = Jsoup.parse(dom);
		
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
		//chapterBean = new ChapterBean(chapterURI, chapterName, title, result);
		/*
		String dirName = AppConfig.getPropertyValue(AppConfigEnum.IMAGE_STORE_PATH) + title + "/" + chpaterName;
		File chapterDirectory = new File(dirName);
		if(!chapterDirectory.exists()) {
			chapterDirectory.mkdirs();
		}

		for(Element link : imageLinks) {
			try {
				String imageURI = link.attr("src");
				logger.debug("uri : " + imageURI);
				String fileName = imageURI.substring(imageURI.lastIndexOf("/") + 1, imageURI.length());
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				logger.debug("fileName : " + fileName);
				logger.debug("fileExt : " + fileExt);
				URL url = new URL(imageURI);
				BufferedImage img = ImageIO.read(url);
				String fileFullPath = dirName + "/" + fileName;
				File file = new File(fileFullPath);
				ImageIO.write(img, fileExt, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
		

	    logger.debug(" End Date : " + Utils.getCurrentDate());
	    return chapterBean;
	}
}
