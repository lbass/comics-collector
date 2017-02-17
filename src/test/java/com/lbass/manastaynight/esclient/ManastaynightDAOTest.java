package com.lbass.manastaynight.esclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lbass.core.TestCore;
import com.lbass.manastaynight.dao.ManastaynightDAO;
import com.lbass.manastaynight.dto.ChapterBean;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;

public class ManastaynightDAOTest extends TestCore {

	private static Logger logger = LoggerFactory.getLogger(ManastaynightDAOTest.class);
	
	@Autowired
	private ManastaynightDAO manastaynightDAO;
	
	private ChapterBean chapterBean;
	
	@Before
	public void setFixture() {		
		String chpaterURI = "http://manastaynight09.blogspot.com/2017/01/18_29.html";
		String chapterName = "미소된장국으로_건배_18화";
		String title = "미소된장국으로 건배";
		String[] images = {
				  "https://1.bp.blogspot.com/-N9O0yNRknZo/WJG79a6CW7I/AAAAAAANl_E/z7XwGMBxdiETmsXccdodMrzfsmgbAwVSQCLcB/s1600/000.jpg"
				, "https://1.bp.blogspot.com/-N9O0yNRknZo/WJG79a6CW7I/AAAAAAANl_E/z7XwGMBxdiETmsXccdodMrzfsmgbAwVSQCLcB/s1600/001.jpg"
				, "https://1.bp.blogspot.com/-N9O0yNRknZo/WJG79a6CW7I/AAAAAAANl_E/z7XwGMBxdiETmsXccdodMrzfsmgbAwVSQCLcB/s1600/002.jpg" 
				, "https://1.bp.blogspot.com/-N9O0yNRknZo/WJG79a6CW7I/AAAAAAANl_E/z7XwGMBxdiETmsXccdodMrzfsmgbAwVSQCLcB/s1600/003.jpg"
		};
		chapterBean = new ChapterBean(chpaterURI, chapterName, title, images);
	}	
		
	//@Test
	public void putTest() throws Exception {
		String[] images = {"test", "test"};
		ChapterBean param = new ChapterBean("test", "chapterName", "title", images);
		manastaynightDAO.insertChapter(param);
	}

	//@Test
	public void bulkTest() throws Exception {
		List param = new ArrayList();
		for(int i = 0 ; i < 30 ; i++) {
			String[] images = {"test" + String.valueOf(i), "test" + String.valueOf(i)};
			ChapterBean bean = new ChapterBean("test"  + String.valueOf(i), "chapterName"  + String.valueOf(i), "title"  + String.valueOf(i), images);
			bean.setOrder(i);
			bean.setSession_seq(1);
			param.add(bean);
		}
		//esRepository.bulk(param);
	}
	
	@Test
	public void initES() {
		manastaynightDAO.initRepository();
	}	
	
	
	//@Test
	public void test() throws IOException, Exception {
		JestClient client = null;
		JestResult result = null;
		try {
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200")
					.defaultCredentials("lbass", "lbass81").multiThreaded(true).build());
			client = factory.getObject();

			//String query =  "{ \"fields\": [\"title\"], \"query\": { \"match\": { \"title\": \"Love\" } } }";
			String query = "{" +
				  "\"query\": {" +
				    "\"match\": {" +
				      "\"title\": \"카구야님\"" +
				    "}" +
				  "}" +
				"}";
			logger.debug(query);
			
			Search putMapper = new Search.Builder(query).build();
			logger.debug(putMapper.toString());

			result = client.execute(putMapper);
			System.out.println(result.getJsonString());
			System.out.println(((Map)result.getJsonMap().get("hits")).get("hits").toString());
			logger.debug(((Map)result.getJsonMap().get("hits")).get("hits").toString());
			

		} catch (IOException e) {
			logger.error("ES connect error", e);
		} finally {
			if (client != null) {
				client.shutdownClient();
			}
		}
	}

}
