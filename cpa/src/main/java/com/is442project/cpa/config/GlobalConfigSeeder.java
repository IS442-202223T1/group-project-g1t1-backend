package com.is442project.cpa.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class GlobalConfigSeeder {

    private final GlobalConfigRepository globalConfigRepository;

    public GlobalConfigSeeder(GlobalConfigRepository globalConfigRepository) {
        this.globalConfigRepository = globalConfigRepository;
        insertTestBooking();
    }

    private void insertTestBooking(){
        GlobalConfig globalConfig = new GlobalConfig(2,2,"src/main/resources/images/LetterHead.png", "Singapore Sports School");

        globalConfigRepository.saveAllAndFlush(Arrays.asList(globalConfig));

        System.out.println("======TEST GLOBAL CONFIG INSERTED======");
    }
}
