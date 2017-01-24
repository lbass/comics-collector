package com.lbass.manastaynight.crawler;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lbass.manastaynight.crawler.impl.ChapterCrawler;
import com.lbass.manastaynight.crawler.impl.MainCrawler;
import com.lbass.manastaynight.vo.ChapterBean;
import com.lbass.manastaynight.vo.MainColletingBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml","classpath:dispatcher-servlet.xml"})
public class ComicsCollectTest {

	private static Logger logger = LoggerFactory.getLogger(ComicsCollectTest.class);
	
	
	@Test
	public void collecyTest() {
		MainCrawler mainCrawler = new MainCrawler();
		ChapterCrawler chapterCrawler = new ChapterCrawler();			
		MainColletingBean mainColletingBean = (MainColletingBean)mainCrawler.runCrawling();
		List<ChapterBean> collectData = mainColletingBean.getChapterBeans();
		
		for(ChapterBean bean : collectData) {
			chapterCrawler.runCrawling(bean);
		}
		
	}
}
