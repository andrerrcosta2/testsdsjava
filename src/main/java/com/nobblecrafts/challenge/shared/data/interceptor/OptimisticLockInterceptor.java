package com.nobblecrafts.challenge.shared.data.interceptor;

import com.nobblecrafts.challenge.shared.service.retry.RetryTemplateFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.OptimisticLockException;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class OptimisticLockInterceptor {

    private final RetryTemplate retryTemplate;

    @Autowired
    public OptimisticLockInterceptor(RetryTemplateFactory retryTemplateFactory) {
        this.retryTemplate = retryTemplateFactory.createRetryTemplate();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Around("@annotation(com.nobblecrafts.challenge.shared.data.transaction.OptimisticLockMode)")
    @Order(101)
    public Object retryOnOptimisticLockException(ProceedingJoinPoint joinPoint) throws Throwable {
        return retryTemplate.execute(retryContext -> joinPoint.proceed());
    }

    @PostConstruct
    public void init() {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(5);
        retryTemplate.setRetryPolicy(retryPolicy);
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(100L);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(OptimisticLockException.class, true);
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(5, retryableExceptions, true);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
    }

}
