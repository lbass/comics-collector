package com.lbass.manastaynight.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml","classpath:dispatcher-servlet.xml"})
public class SchedulerTest {

	@Test
	public void test() throws InterruptedException {
		while(true) {
			Thread.sleep(10000);
		}
	}

}
