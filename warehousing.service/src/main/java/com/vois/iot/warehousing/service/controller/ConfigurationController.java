package com.vois.iot.warehousing.service.controller;

import com.vois.iot.warehousing.service.Service.ConfigurationService;
import com.vois.iot.warehousing.service.model.Device;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/configure")
@RequiredArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configService;

    @PutMapping("/{id}")
    public ResponseEntity<Device> configureDevice(@PathVariable UUID id) {
        Device configured = configService.configureDeviceOrThrow(id);
        return ResponseEntity.ok(configured);
    }
}