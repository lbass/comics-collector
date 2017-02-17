package com.lbass.manastaynight.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lbass.manastaynight.service.CollectService;

@Component
public class Scheduler {
	
	@Autowired
	private CollectService service;
	
    @Scheduled(cron = "0 0/30 * * * *")
    public void collect(){
    	service.collectCoimcs();
    }
}

