package com.sifang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TimerTask {
    @Autowired
    private NumberMessageService numberMessageService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void scheduledTask(){
        System.out.println("开始自动更新号源");
        this.numberMessageService.autoupdate();
    }
}
