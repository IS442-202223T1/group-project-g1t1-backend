package com.is442project.cpa.config;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/global-config")
public class GlobalConfigController {
    private final GlobalConfigOps globalConfigOps;

    public GlobalConfigController(GlobalConfigService globalConfigService) {
        this.globalConfigOps = globalConfigService;
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity getOne() {
      GlobalConfig result = globalConfigOps.getOne();
      return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity UpdateConfig(@RequestBody ConfigDTO ConfigDTO) {
        GlobalConfig result = globalConfigOps.updateConfig(ConfigDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
      }
}
