package com.vois.iot.warehousing.service.Service;

import com.vois.iot.warehousing.service.dto.DeviceRequest;
import com.vois.iot.warehousing.service.model.Device;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import com.vois.iot.warehousing.service.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final DeviceRepository repository;

    @Transactional
    public Device configureDeviceOrThrow(UUID id) {
        log.info("Attempting to configure device with ID: {}", id);

        Device device = repository.findById(id).orElseThrow(() -> {
            log.warn("Device {} not found", id);
            throw new EntityNotFoundException("Device not found");
        });

        if (device.getStatus() == DeviceStatus.ACTIVE) {
            log.warn("Device {} is already configured", id);
            throw new IllegalStateException("Device is already configured (ACTIVE)");
        }

        if (device.getStatus() != DeviceStatus.READY) {
            log.warn("Device {} is not in READY state: {}", id, device.getStatus());
            throw new IllegalStateException("Device must be in READY state to configure");
        }

        double temp = Math.round(ThreadLocalRandom.current().nextDouble(0.0, 10.0) * 10.0) / 10.0;
        device.setStatus(DeviceStatus.ACTIVE);
        device.setTemperature(temp);

        Device updated = repository.save(device);
        log.info("Device {} configured successfully", id);
        return updated;
    }
}