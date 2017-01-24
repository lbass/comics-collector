package com.lbass.manastaynight.crawler;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lbass.manastaynight.crawler.impl.MainCrawler;
import com.lbass.manastaynight.exception.NotUpdateException;
import com.lbass.manastaynight.vo.ChapterBean;
import com.lbass.manastaynight.vo.MainColletingBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml","classpath:dispatcher-servlet.xml"})
public class MainCrawlingTest {

	private static Logger logger = LoggerFactory.getLogger(MainCrawler.class);
	
	@Autowired
	private Crawler crawler;
	
	@Test
	public void crawlingTest() {
		try {
			MainColletingBean mainColletingBean = (MainColletingBean)crawler.runCrawling();
			List<ChapterBean> chpterBeans = mainColletingBean.getChapterBeans();
			if(chpterBeans != null && !chpterBeans.isEmpty()) {
				for(int i = 0 ; i < chpterBeans.size() ; i++) {
					ChapterBean bean = chpterBeans.get(i);
					logger.debug(bean.toString());
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
