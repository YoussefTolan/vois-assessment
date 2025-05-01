package com.vois.iot.warehousing.service.dto;

import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;

import java.util.UUID;

public class DeviceResponse {
    private UUID id;
    private String pin;
    private DeviceStatus status;

    private Double temperature;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }


    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {

        this.temperature = temperature;
    }

}
