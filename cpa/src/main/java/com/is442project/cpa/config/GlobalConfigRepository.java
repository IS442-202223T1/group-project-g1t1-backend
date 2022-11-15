package com.is442project.cpa.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalConfigRepository extends JpaRepository<GlobalConfig, Integer> {

    GlobalConfig findFirstBy();
  
}
