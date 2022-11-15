package com.is442project.cpa.config;

public interface GlobalConfigOps {

    GlobalConfig getOne();

    GlobalConfig updateConfig(ConfigDTO configDTO);
}
