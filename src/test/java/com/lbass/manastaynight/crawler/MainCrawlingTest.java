package com.lbass.manastaynight.crawler;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lbass.manastaynight.exception.NotUpdateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml","classpath:dispatcher-servlet.xml"})
public class MainCrawlingTest {

	private static Logger logger = LoggerFactory.getLogger(MainCrawler.class);
	
	@Autowired
	private MainCrawler crawler;
	
	@Test
	public void crawlingTest() {
		try {
			List<String> chpterURIList = crawler.runCrawling();
			if(chpterURIList != null && !chpterURIList.isEmpty()) {
				for(int i = 0 ; i < chpterURIList.size() ; i++) {
					String uri = chpterURIList.get(i);
					logger.debug(uri.toString());
				}
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} catch(NotUpdateException e) {
			logger.error("Not update page");
			assertTrue(true);
		}
	}
}
