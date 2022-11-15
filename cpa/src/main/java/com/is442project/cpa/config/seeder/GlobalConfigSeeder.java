package com.is442project.cpa.config.seeder;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.is442project.cpa.config.model.GlobalConfig;
import com.is442project.cpa.config.model.GlobalConfigRepository;

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
    