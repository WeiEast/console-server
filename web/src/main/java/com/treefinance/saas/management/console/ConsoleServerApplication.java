package com.treefinance.saas.management.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan("com.treefinance.saas.management.console.web")
@ImportResource("classpath:spring/applicationContext.xml")
public class ConsoleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsoleServerApplication.class);
    }
}
