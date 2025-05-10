package com.vois.iot.warehousing.service.dto;

import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class DeviceResponse {
    private UUID id;
    private String pin;
    private DeviceStatus status;
    private Double temperature;


    // this constructor is for testing purpose
    public DeviceResponse(UUID sampleId, String number, DeviceStatus deviceStatus, double i) {
    this.id = sampleId;
    this.pin = number;
    this.status = deviceStatus;
    this.temperature = i;
    }

    // this construct acts as the default constructor
    public DeviceResponse() {
    }
}
