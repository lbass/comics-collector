package com.lbass.manastaynight.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lbass.core.TestCore;
import com.lbass.manastaynight.service.CollectService;

public class CollectServiceTest extends TestCore {

	private static Logger logger = LoggerFactory.getLogger(CollectServiceTest.class);
	
	@Autowired
	private CollectService service;
	

	@Test
	public void collecyTest() {
		service.collectCoimcs();
		//수집한 내역을 지우는 모듈 기능이 필요함 - 수집 때마다 식별 값이 필요 할 듯 하다.
	}
}
