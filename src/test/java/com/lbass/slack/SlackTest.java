package com.lbass.slack;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lbass.common.dto.SlackBean;
import com.lbass.common.slack.SlackSender;
import com.lbass.common.utils.Utils;
import com.lbass.core.TestCore;

public class SlackTest extends TestCore{
	
	@Autowired
	private SlackSender sender;
	
	private List<SlackBean> updateList = null;
	private String updateDate;
	
	@Before
	public void setFixture() {
		updateList = new ArrayList<SlackBean>();
		for(int i = 0 ; i < 10 ; i ++) {
			updateList.add(new SlackBean("chapterName_" + i, "http://manastaynight09.blogspot.kr/2017/02/203.html"));
		}
		updateDate = Utils.getCurrentDate();
	}

	@Test
	public void testSlack() {
		String result = sender.sendSlack(updateDate, updateList);
		assertEquals(result, "ok");
	}
}
