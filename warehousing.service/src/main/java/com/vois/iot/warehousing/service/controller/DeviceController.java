package com.vois.iot.warehousing.service.controller;

import com.vois.iot.warehousing.service.Service.DeviceService;
import com.vois.iot.warehousing.service.dto.DeviceRequest;
import com.vois.iot.warehousing.service.dto.DeviceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Slf4j
@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> create(@RequestBody DeviceRequest req) {
        log.info("Creating new device with PIN: {}", req.getPin());
        DeviceResponse response = service.createDevice(req);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> get(@PathVariable UUID id) {
        log.info("Fetching device with ID: {}", id);
        return ResponseEntity.ok(service.getDevice(id));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(defaultValue = "false") Boolean saleable,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<DeviceResponse> pagedDevices = Boolean.TRUE.equals(saleable)
                ? service.listSaleable(PageRequest.of(page, size))
                : service.listAll(PageRequest.of(page, size));

        Map<String, Object> response = new HashMap<>();
        response.put("data", pagedDevices.getContent());
        response.put("total", pagedDevices.getTotalElements());
        response.put("page", pagedDevices.getNumber());
        response.put("pageSize", pagedDevices.getSize());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> update(@PathVariable UUID id, @RequestBody DeviceRequest req) {
        log.info("Updating device with ID: {}", id);
        DeviceResponse updated = service.updateDevice(id, req);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable UUID id) {
        log.info("Deleting device with ID: {}", id);
        service.deleteDevice(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Device deleted successfully"));
    }


}