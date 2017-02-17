package com.lbass.common.slack;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SlackSender {
	
	//https://hooks.slack.com/services/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
	//payload={"text": "This is a line of text in a channel.\nAnd this is another line of text."}
	//curl -X POST --data-urlencode 'payload={"channel": "#general", "username": "webhookbot", "text": "This is posted to #general and comes from a bot named webhookbot.", "icon_emoji": ":ghost:"}' 
	//https://hooks.slack.com/services/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
	public void sendSlack(String string) {
		
		try {
			
			String sendData = "{" +
				"\"fallback\": \"Required text summary of the attachment that is shown by clients that understand attachments but choose not to show them.\"," +
				"\"text\": \"MainText\"," +
				"\"pretext\": \"preText\"," +
				"\"color\": \"#36a64f\"," +
				"\"fields\": [" +
					"{" +
						"\"title\": \"Required Field Title\"," +
						"\"value\": \"Text value of the field. May contain standard message markup and must be escaped as normal. May be multi-line.\"," +
						"\"short\": false" +
					"}" +
				"]" +
			"}";			
			
			String res = Jsoup.connect("https://hooks.slack.com/services/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")
					.data("payload",sendData)
	                .method(Connection.Method.POST)
	                .header("Accept", "application/json")
	                .header("X-Requested-With", "XMLHttpRequest")
	                .ignoreContentType(true)
	                .execute()
	                .body();
	        System.out.println(res);

	        //System.out.println(document.toString());		    
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
				
	}
}
