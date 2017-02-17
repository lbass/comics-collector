package com.lbass.common.slack;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lbass.common.dto.SlackBean;

@Component("slackSender")
public class SlackSender {
	
	private static Logger logger = LoggerFactory.getLogger(SlackSender.class);
	@Value("${slack.webhook.uri}")
	private String URI;
	@Value("${slack.webhook.username}")
	private String USERNAME;	
	
	
	public String sendSlack(String updateDate, List<SlackBean> updateList) {
		String result = null;
		try {
			String sendData = "{" +
					"\"username\": \"" + USERNAME + "\"," +
					"\"fallback\": \""+ updateDate + " 업데이트 목록\"," +
					"\"text\" : \""+ updateDate + " 업데이트 목록\"," +
					"\"color\": \"good\"," +
					"\"fields\": [";
			for(int i = 0 ; i < updateList.size() ; i++) {
				SlackBean slackbean = updateList.get(i);
				sendData += 
						"{" +
							"\"title\": \"" + slackbean.getName() + "\"," +
							"\"value\": \"<" + slackbean.getLink() + "| ClickView>\"," +
							"\"short\": true" +
						"}";
				if(i < updateList.size() - 1) {
					sendData += ",";
				}
			}
			sendData +=	"]}";			
			
			logger.debug("[Send Slack]");
			logger.debug("Send Data : " + sendData);
			
			result = Jsoup.connect(URI)
					.data("payload",sendData)
	                .method(Connection.Method.POST)
	                .header("Accept", "application/json")
	                .header("X-Requested-With", "XMLHttpRequest")
	                .ignoreContentType(true)
	                .execute()
	                .body();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}
}
