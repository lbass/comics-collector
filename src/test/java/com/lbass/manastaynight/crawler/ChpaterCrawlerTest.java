package com.lbass.manastaynight.crawler;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lbass.manastaynight.dto.ChapterBean;
import com.lbass.manastaynight.exception.NotUpdateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml","classpath:dispatcher-servlet.xml"})
public class ChpaterCrawlerTest {

	private static Logger logger = LoggerFactory.getLogger(ChpaterCrawlerTest.class);
	
//	@Autowired
//	private Crawler crawler;
	
	@Test
	public void crawlingTest() throws URISyntaxException {
		try {
			ChapterCrawler crawler = new ChapterCrawler();
			ChapterBean chapterBean = crawler.runCrawling("http://manastaynight09.blogspot.com/2017/01/18_29.html");
			logger.debug(chapterBean.toString());
			assertTrue(true);			
		} catch(NotUpdateException e) {
			logger.error("Not update page");
			assertTrue(true);
		}
	}

}
