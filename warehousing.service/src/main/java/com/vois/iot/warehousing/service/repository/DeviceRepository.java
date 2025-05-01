package com.vois.iot.warehousing.service.repository;

import com.vois.iot.warehousing.service.model.Device;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    Page<Device> findByStatusAndTemperatureBetweenOrderByPinAsc(
        DeviceStatus status, int min, int max, Pageable pageable);

    Page<Device> findAll(Pageable pageable);
}