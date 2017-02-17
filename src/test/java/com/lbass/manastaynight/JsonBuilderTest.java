package com.lbass.manastaynight;

import org.junit.Test;

import com.lbass.common.utils.Utils;
import com.lbass.manastaynight.dto.ChapterBean;

public class JsonBuilderTest {

	@Test
	public void test() throws Exception {
		String[] images = {"test", "test"};
		ChapterBean param = new ChapterBean("test", "chapterName", "title", images);
		String source = Utils.toJsonString(param);
		System.out.println(source);
	}

}
