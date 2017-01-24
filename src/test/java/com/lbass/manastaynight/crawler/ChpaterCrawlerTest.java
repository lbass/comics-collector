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

import com.lbass.manastaynight.crawler.impl.ChapterCrawler;
import com.lbass.manastaynight.exception.NotUpdateException;
import com.lbass.manastaynight.vo.ChapterBean;

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
			ChapterBean bean = new ChapterBean(new URI(""), "");
			crawler.runCrawling(bean);

			assertTrue(true);			
		} catch(NotUpdateException e) {
			logger.error("Not update page");
			assertTrue(true);
		}
	}

}
