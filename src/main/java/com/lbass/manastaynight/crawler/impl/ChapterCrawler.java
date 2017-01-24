package com.lbass.manastaynight.crawler.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbass.manastaynight.config.AppConfig;
import com.lbass.manastaynight.config.AppConfig.AppConfigEnum;
import com.lbass.manastaynight.util.Utils;
import com.lbass.manastaynight.vo.ChapterBean;

public class ChapterCrawler {
	
	private static Logger logger = LoggerFactory.getLogger(ChapterCrawler.class);
	
	public void runCrawling(ChapterBean chapterBean) {
		logger.debug(" Start Date : " + Utils.getCurrentData());
		
	    Document doc = null;
		try {
			doc = Jsoup.connect(chapterBean.getChaterURI().toString()).get();
			String title = doc.getElementsByClass("post-labels").first().select("a[href]").first().text();
			String chpaterName = chapterBean.getChapterName();
			Element postBody = doc.getElementsByClass("post-body entry-content").first();
			Elements imageLinks = postBody.select("img[src]");
			//첫 아이언맨 이미지는 버린다.
			imageLinks.remove(0);

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
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	    logger.debug(" End Date : " + Utils.getCurrentData());
	}
	
	
}
