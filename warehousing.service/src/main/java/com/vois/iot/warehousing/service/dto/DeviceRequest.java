package com.vois.iot.warehousing.service.dto;

import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeviceRequest {
    private String pin;                // must be 7-digit for create
    private DeviceStatus status;       // optional for update
    private Double temperature;       // optional for update
}
