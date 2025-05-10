package com.vois.iot.warehousing.service.controller;


import com.vois.iot.warehousing.service.Service.ConfigurationService;
import com.vois.iot.warehousing.service.model.Device;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConfigurationController.class)
class ConfigurationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConfigurationService configService;

    private UUID deviceId;
    private Device configuredDevice;

    @BeforeEach
    void setup() {
        deviceId = UUID.randomUUID();
        configuredDevice = new Device();
        configuredDevice.setId(deviceId);
        configuredDevice.setPin("0123456");
        configuredDevice.setStatus(DeviceStatus.ACTIVE);
        configuredDevice.setTemperature(5.5);
    }

    @Test
    @DisplayName("PUT /api/configure/{id} - should return 200 OK when configuration succeeds")
    void configureDevice_success() throws Exception {
        when(configService.configureDeviceOrThrow(deviceId)).thenReturn(configuredDevice);

        mockMvc.perform(put("/api/configure/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deviceId.toString()))
                .andExpect(jsonPath("$.pin").value("0123456"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.temperature").value(5.5));
    }

    @Test
    @DisplayName("PUT /api/configure/{id} - should return 404 if device not found")
    void configureDevice_notFound() throws Exception {
        when(configService.configureDeviceOrThrow(deviceId)).thenThrow(new EntityNotFoundException("Device not found"));

        mockMvc.perform(put("/api/configure/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/configure/{id} - should return 400 if device is not in READY state")
    void configureDevice_invalidState() throws Exception {
        when(configService.configureDeviceOrThrow(deviceId))
                .thenThrow(new IllegalStateException("Device must be in READY state to configure"));

        mockMvc.perform(put("/api/configure/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
