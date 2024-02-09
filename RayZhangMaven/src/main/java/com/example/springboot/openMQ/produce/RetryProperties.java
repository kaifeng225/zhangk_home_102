package com.example.springboot.openMQ.produce;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "retry")
public class RetryProperties {
    private long firstRetryBackoffMs = 1000;
    private long maxRetryTimes = 10;
    private long base = 3;
    private String mqDestinationName = "pm.retry";
    private String mqListenerConcurrency = "2-4";
    private String mqSubscription = "PaymentManager.PMRetrySubscriber";
}
