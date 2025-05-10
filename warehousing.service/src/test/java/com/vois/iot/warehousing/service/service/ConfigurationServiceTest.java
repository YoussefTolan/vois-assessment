package com.vois.iot.warehousing.service.service;

import com.vois.iot.warehousing.service.Service.ConfigurationService;
import com.vois.iot.warehousing.service.model.Device;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import com.vois.iot.warehousing.service.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigurationServiceTest {

    private DeviceRepository repository;
    private ConfigurationService service;

    @BeforeEach
    void setUp() {
        repository = mock(DeviceRepository.class);
        service = new ConfigurationService(repository);
    }

    @Test
    void shouldConfigureReadyDeviceSuccessfully() {
        UUID deviceId = UUID.randomUUID();
        Device device = new Device();
        device.setId(deviceId);
        device.setStatus(DeviceStatus.READY);

        when(repository.findById(deviceId)).thenReturn(Optional.of(device));
        when(repository.save(any(Device.class))).thenAnswer(i -> i.getArgument(0));

        Device result = service.configureDeviceOrThrow(deviceId);

        assertEquals(DeviceStatus.ACTIVE, result.getStatus());
        assertNotNull(result.getTemperature());
        assertTrue(result.getTemperature() >= 0.0 && result.getTemperature() <= 10.0);
        verify(repository).save(device);
    }

    @Test
    void shouldThrowIfDeviceNotFound() {
        UUID deviceId = UUID.randomUUID();
        when(repository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.configureDeviceOrThrow(deviceId));
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowIfDeviceAlreadyActive() {
        UUID deviceId = UUID.randomUUID();
        Device device = new Device();
        device.setId(deviceId);
        device.setStatus(DeviceStatus.ACTIVE);

        when(repository.findById(deviceId)).thenReturn(Optional.of(device));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> service.configureDeviceOrThrow(deviceId));

        assertEquals("Device is already configured (ACTIVE)", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowIfDeviceNotReady() {
        UUID deviceId = UUID.randomUUID();
        Device device = new Device();
        device.setId(deviceId);
        device.setStatus(DeviceStatus.UNKNOWN); // or any other status except READY

        when(repository.findById(deviceId)).thenReturn(Optional.of(device));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> service.configureDeviceOrThrow(deviceId));

        assertEquals("Device must be in READY state to configure", exception.getMessage());
        verify(repository, never()).save(any());
    }
    @Test
    void shouldThrowIfDeviceStatusIsNull() {
        UUID deviceId = UUID.randomUUID();
        Device device = new Device();
        device.setId(deviceId);
        device.setStatus(null); // status not set

        when(repository.findById(deviceId)).thenReturn(Optional.of(device));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> service.configureDeviceOrThrow(deviceId));

        assertEquals("Device must be in READY state to configure", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
