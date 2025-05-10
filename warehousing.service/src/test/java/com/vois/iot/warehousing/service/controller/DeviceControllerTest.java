package com.vois.iot.warehousing.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vois.iot.warehousing.service.Service.DeviceService;
import com.vois.iot.warehousing.service.dto.DeviceRequest;
import com.vois.iot.warehousing.service.dto.DeviceResponse;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeviceService service;

    @Autowired
    private ObjectMapper objectMapper; // for JSON serialization

    private UUID sampleId;
    private DeviceResponse sampleResponse;
    private DeviceRequest sampleRequest;

    @BeforeEach
    void setup() {
        sampleId = UUID.randomUUID();
        sampleResponse = new DeviceResponse(
                sampleId,
                "0123456",
                DeviceStatus.READY,
                -1.0
        );
        sampleRequest = new DeviceRequest();
        sampleRequest.setPin("0123456");
        sampleRequest.setStatus(DeviceStatus.READY);
        sampleRequest.setTemperature(-1.0);
    }

    @Test
    @DisplayName("POST /api/devices → 201 CREATED")
    void createDevice() throws Exception {
        given(service.createDevice(any(DeviceRequest.class))).willReturn(sampleResponse);

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(sampleId.toString()))
                .andExpect(jsonPath("$.pin").value("0123456"))
                .andExpect(jsonPath("$.status").value("READY"))
                .andExpect(jsonPath("$.temperature").value(-1));
    }

    @Test
    @DisplayName("GET /api/devices/{id} → 200 OK")
    void getDeviceById() throws Exception {
        given(service.getDevice(sampleId)).willReturn(sampleResponse);

        mockMvc.perform(get("/api/devices/{id}", sampleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleId.toString()))
                .andExpect(jsonPath("$.pin").value("0123456"));
    }

    @Test
    @DisplayName("GET /api/devices → 200 OK paginated list")
    void listAllDevices() throws Exception {
        List<DeviceResponse> list = Collections.singletonList(sampleResponse);
        Page<DeviceResponse> page = new PageImpl<>(list, PageRequest.of(0, 10), 1);

        given(service.listAll(PageRequest.of(0, 10))).willReturn(page);

        mockMvc.perform(get("/api/devices")
                        .param("saleable", "false")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(sampleId.toString()))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.pageSize").value(10));
    }

    @Test
    @DisplayName("PUT /api/devices/{id} → 200 OK")
    void updateDevice() throws Exception {
        DeviceRequest updateReq = new DeviceRequest();
        updateReq.setStatus(DeviceStatus.ACTIVE);
        updateReq.setTemperature(5.0);

        DeviceResponse updated = new DeviceResponse(
                sampleId,
                "0123456",
                DeviceStatus.ACTIVE,
                5
        );

        given(service.updateDevice(eq(sampleId), any(DeviceRequest.class)))
                .willReturn(updated);

        mockMvc.perform(put("/api/devices/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.temperature").value(5));
    }

    @Test
    @DisplayName("GET /api/devices?saleable=true → 200 OK paginated list")
    void listSaleableDevices() throws Exception {
        List<DeviceResponse> list = Collections.singletonList(sampleResponse);
        Page<DeviceResponse> page = new PageImpl<>(list, PageRequest.of(0, 10), 1);

        given(service.listSaleable(PageRequest.of(0, 10))).willReturn(page);

        mockMvc.perform(get("/api/devices")
                        .param("saleable", "true")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(sampleId.toString()))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.pageSize").value(10));
    }

    @Test
    @DisplayName("DELETE /api/devices/{id} → 200 OK")
    void deleteDevice() throws Exception {
        doNothing().when(service).deleteDevice(sampleId);

        mockMvc.perform(delete("/api/devices/{id}", sampleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Device deleted successfully"));
    }
}
