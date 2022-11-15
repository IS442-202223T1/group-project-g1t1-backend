package com.is442project.cpa.config;

import org.springframework.stereotype.Component;

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
        globalConfig.setLoanLimitPerMonth(configDTO.getLoanLimitPerMonth());
        globalConfig.setPassLimitPerLoan(configDTO.getPassLimitPerLoan());
        globalConfig.setLetterHeadUrl(configDTO.getLetterHeadUrl());
        globalConfig.setCorporateMemberName(configDTO.getCorporateMemberName());
        return globalConfigRepository.save(globalConfig);
    }
}
