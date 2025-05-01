package com.vois.iot.warehousing.service.dto;

import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;

public class DeviceRequest {
    private String pin;                // must be 7-digit for create
    private DeviceStatus status;       // optional for update

    private Double temperature;       // optional for update


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
