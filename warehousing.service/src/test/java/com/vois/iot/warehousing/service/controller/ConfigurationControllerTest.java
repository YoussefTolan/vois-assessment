package com.vois.iot.warehousing.service.controller;

import com.vois.iot.warehousing.service.Service.ConfigurationService;
import com.vois.iot.warehousing.service.model.Device;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConfigurationController.class)
class ConfigurationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConfigurationService configService;

    @Test
    @DisplayName("PUT /api/configure/{id} → 200 OK with Device JSON")
    void whenConfigureExistingDevice_thenReturnsDevice() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        Device mockDevice = new Device();
        mockDevice.setId(id);
        mockDevice.setPin("1234567");
        mockDevice.setStatus(DeviceStatus.ACTIVE);
        mockDevice.setTemperature(7.0);
        mockDevice.setUpdatedAt(Instant.now());

        // Stub the service
        when(configService.configureDeviceOrThrow(id))
                .thenReturn(mockDevice);

        // When & Then
        mockMvc.perform(put("/api/configure/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.pin").value("1234567"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.temperature").value(7));
    }
}
