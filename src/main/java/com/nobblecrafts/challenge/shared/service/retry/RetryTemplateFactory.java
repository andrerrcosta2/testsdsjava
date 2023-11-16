package com.nobblecrafts.challenge.shared.service.retry;

import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class RetryTemplateFactory {

    public RetryTemplate createRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        return retryTemplate;
    }
}
