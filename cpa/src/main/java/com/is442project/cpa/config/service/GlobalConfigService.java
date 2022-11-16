package com.is442project.cpa.config.service;

import org.springframework.stereotype.Component;

import com.is442project.cpa.config.dto.ConfigDTO;
import com.is442project.cpa.config.model.GlobalConfig;
import com.is442project.cpa.config.model.GlobalConfigRepository;

@Component
public class GlobalConfigService implements GlobalConfigOps{

    private final GlobalConfigRepository globalConfigRepository;

    public GlobalConfigService(GlobalConfigRepository globalConfigRepository) {
        this.globalConfigRepository = globalConfigRepository;
    }

    public GlobalConfig getOne() {
        return globalConfigRepository.findFirstBy();
    }

    public GlobalConfig updateConfig(ConfigDTO configDTO) {
        GlobalConfig globalConfig = globalConfigRepository.findFirstBy();

        if (configDTO.getLoanLimitPerMonth() != 0) {
            globalConfig.setLoanLimitPerMonth(configDTO.getLoanLimitPerMonth());
        }
        if (configDTO.getPassLimitPerLoan() != 0) {
            globalConfig.setPassLimitPerLoan(configDTO.getPassLimitPerLoan());
        }
        if (configDTO.getLetterHeadUrl() != null) {
            globalConfig.setLetterHeadUrl(configDTO.getLetterHeadUrl());
        }
        if (configDTO.getCorporateMemberName() != null) {
            globalConfig.setCorporateMemberName(configDTO.getCorporateMemberName());
        }

        return globalConfigRepository.save(globalConfig);
    }

}
