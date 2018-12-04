package com.treefinance.saas.console;

import com.treefinance.saas.assistant.variable.notify.annotation.EnableVariableNotify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan("com.treefinance.saas.console.web")
@ImportResource("classpath:spring/applicationContext.xml")
@EnableVariableNotify
public class ConsoleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsoleServerApplication.class);
    }
}
