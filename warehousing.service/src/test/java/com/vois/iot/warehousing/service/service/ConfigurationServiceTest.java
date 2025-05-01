package com.vois.iot.warehousing.service.service;


import com.vois.iot.warehousing.service.model.Device;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import com.vois.iot.warehousing.service.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ConfigurationServiceTest {

    @Mock
    private DeviceRepository repository;

    @InjectMocks
    private ConfigurationService service;

    private UUID id;
    private Device readyDevice;
    private Device activeDevice;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();

        readyDevice = new Device();
        readyDevice.setId(id);
        readyDevice.setStatus(DeviceStatus.READY);
        readyDevice.setTemperature(-1.0);

        activeDevice = new Device();
        activeDevice.setId(id);
        activeDevice.setStatus(DeviceStatus.ACTIVE);
        activeDevice.setTemperature(5.0);
    }

//    @Test
//    @DisplayName("configureDeviceOrThrow: READY → becomes ACTIVE with temp 0–10")
//    void whenDeviceIsReady_thenConfigureSuccessfully() {
//        // given
//        given(repository.findById(id)).willReturn(Optional.of(readyDevice));
//        // capture what gets saved
//        ArgumentCaptor<Device> captor = ArgumentCaptor.forClass(Device.class);
//        given(repository.save(captor.capture()))
//                .willAnswer(inv -> inv.getArgument(0));
//
//        // when
//        Device result = service.configureDeviceOrThrow(id);
//
//        // then
//        assertThat(result.getStatus()).isEqualTo(DeviceStatus.ACTIVE);
//        assertThat(result.getTemperature()).isBetween(0.0, 10.0);
//        // verify save was called with the same instance
//        Device saved = captor.getValue();
//        assertThat(saved).isSameAs(readyDevice);
//        then(repository).should().findById(id);
//        then(repository).should().save(readyDevice);
//    }
//
//    @Test
//    @DisplayName("configureDeviceOrThrow: missing ID → EntityNotFoundException")
//    void whenDeviceNotFound_thenThrowEntityNotFound() {
//        // given
//        given(repository.findById(id)).willReturn(Optional.empty());
//
//        // when / then
//        assertThrows(EntityNotFoundException.class,
//                () -> service.configureDeviceOrThrow(id));
//        then(repository).should().findById(id);
//        then(repository).should(never()).save(any());
//    }
//
//    @Test
//    @DisplayName("configureDeviceOrThrow: already ACTIVE → IllegalStateException")
//    void whenDeviceAlreadyActive_thenThrowIllegalState() {
//        // given
//        given(repository.findById(id)).willReturn(Optional.of(activeDevice));
//
//        // when / then
//        IllegalStateException ex = assertThrows(IllegalStateException.class,
//                () -> service.configureDeviceOrThrow(id));
//        assertThat(ex).hasMessageContaining("already configured");
//        then(repository).should().findById(id);
//        then(repository).should(never()).save(any());
//    }
}
