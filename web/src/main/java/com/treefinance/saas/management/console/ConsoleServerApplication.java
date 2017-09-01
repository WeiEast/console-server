package com.treefinance.saas.management.console;

import com.treefinance.saas.assistant.config.annotation.EnableConfigUpdatePlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan("com.treefinance.saas.management.console.web")
@ImportResource("classpath:spring/applicationContext.xml")
@EnableConfigUpdatePlugin
public class ConsoleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsoleServerApplication.class);
    }
}
