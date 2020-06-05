package com.ihooyah.proxy.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Spring boot app.
 *
 * @author shuaicj 2017/09/21
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AtomicLong taskCounter() {
        return new AtomicLong();
    }
}
