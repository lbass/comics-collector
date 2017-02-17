package com.lbass.slack;

import org.junit.Test;

import com.lbass.common.slack.SlackSender;

public class SlackTest {

	@Test
	public void testSlack() {
		SlackSender sender = new SlackSender();
		sender.sendSlack("test");
	}
}
