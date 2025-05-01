package com.vois.iot.warehousing.service.Service;

import com.vois.iot.warehousing.service.dto.DeviceRequest;
import com.vois.iot.warehousing.service.dto.DeviceResponse;
import com.vois.iot.warehousing.service.model.Device;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import com.vois.iot.warehousing.service.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@Service
public class DeviceService {

    private final DeviceRepository repo;

    public DeviceService(DeviceRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public DeviceResponse createDevice(DeviceRequest req) {
        try {
            Device device = new Device();
            device.setPin(req.getPin());
            device.setStatus(DeviceStatus.READY);
            device.setTemperature(-1.0);

            Device saved = repo.save(device);
            log.info("Device created with ID: {}", saved.getId());
            return toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            log.warn("Duplicate PIN attempted: {}", req.getPin());
            throw new DataIntegrityViolationException("Device with this PIN already exists");
        }
    }

    public DeviceResponse updateDevice(UUID id, DeviceRequest req) {
        Device d = repo.findById(id).orElseThrow(() -> {
            log.warn("Device not found for update: {}", id);
            return new EntityNotFoundException("Device not found");
        });

        if (req.getStatus() != null) {
            d.setStatus(req.getStatus());
        }

        if (req.getTemperature() != null) {
            d.setTemperature(req.getTemperature());
        }

        d.setUpdatedAt(Instant.now());
        Device updated = repo.save(d);
        log.info("Device updated: {}", updated.getId());
        return toResponse(updated);
    }

    public void deleteDevice(UUID id) {
        if (!repo.existsById(id)) {
            log.warn("Delete failed. Device not found: {}", id);
            throw new EntityNotFoundException("Device not found");
        }
        repo.deleteById(id);
        log.info("Device deleted: {}", id);
    }

    public DeviceResponse getDevice(UUID id) {
        Device d = repo.findById(id).orElseThrow(() -> {
            log.warn("Device not found: {}", id);
            return new EntityNotFoundException("Device not found");
        });
        return toResponse(d);
    }

    public Page<DeviceResponse> listSaleable(Pageable pageable) {
        Page<Device> devices = repo.findByStatusAndTemperatureBetweenOrderByPinAsc(DeviceStatus.ACTIVE, 0, 10,
                pageable);
        return devices.map(this::toResponse);
    }

    public Page<DeviceResponse> listAll(Pageable pageable) {
        Page<Device> devices = repo.findAll(pageable);
        return devices.map(this::toResponse);
    }

    private DeviceResponse toResponse(Device d) {
        DeviceResponse r = new DeviceResponse();
        r.setId(d.getId());
        r.setPin(d.getPin());
        r.setStatus(d.getStatus());
        r.setTemperature(d.getTemperature());
        return r;
    }


}