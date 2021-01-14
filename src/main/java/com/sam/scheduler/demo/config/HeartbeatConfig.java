package com.sam.scheduler.demo.config;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.sam.heartbeat.model.HeartbeatApp;
import com.sam.webutil.heartbeat.Registration;

@Configuration
@ComponentScan("com.sam.webutil.heartbeat")
@PropertySource(value = "classpath:heartbeat.properties")
public class HeartbeatConfig {

    @Value("${config.heartbeat.name}")
    private String name;
    @Value("${config.heartbeat.hostname}")
    private String hostname;
    @Value("${config.heartbeat.context:}")
    private String context;
    @Value("${config.heartbeat.channel}")
    private String channel;
    @Value("${config.heartbeat.bot}")
    private String bot;
    @Value("${config.heartbeat.log}")
    private String logPath;

    @Bean
    public HeartbeatApp heartbeatApp() {
        return HeartbeatApp.builder()
                .name(name)
                .hostname(hostname)
                .context(context)
                .channelName(channel)
                .botName(bot)
                .logPath(logPath)
                .build();
    }

    @PostConstruct
    public void init() {
        new Registration(heartbeatApp());
    }


}
