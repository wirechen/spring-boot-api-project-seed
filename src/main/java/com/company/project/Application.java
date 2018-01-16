package com.company.project;

import com.company.project.aliyun.MnsLocalServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

