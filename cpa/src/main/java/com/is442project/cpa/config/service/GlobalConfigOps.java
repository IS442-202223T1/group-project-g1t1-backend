package com.is442project.cpa.config.service;

import com.is442project.cpa.config.dto.ConfigDTO;
import com.is442project.cpa.config.model.GlobalConfig;

public interface GlobalConfigOps {

    GlobalConfig getOne();

    GlobalConfig updateConfig(ConfigDTO configDTO);
    
}
