package com.vois.iot.warehousing.service.model;

import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {

    @Test
    void testGettersAndSetters() {
        Device d = new Device();

        // Test default createdAt & updatedAt are non-null and roughly "now"
        Instant before = Instant.now().minusSeconds(1);
        assertNotNull(d.getCreatedAt(), "createdAt should be initialized");
        assertNotNull(d.getUpdatedAt(), "updatedAt should be initialized");
        assertTrue(d.getCreatedAt().isAfter(before), "createdAt should be very recent");
        assertTrue(d.getUpdatedAt().isAfter(before), "updatedAt should be very recent");

        // Test setting and getting ID
        UUID id = UUID.randomUUID();
        d.setId(id);
        assertEquals(id, d.getId());

        // Test pin
        d.setPin("1234567");
        assertEquals("1234567", d.getPin());

        // Test status
        d.setStatus(DeviceStatus.READY);
        assertEquals(DeviceStatus.READY, d.getStatus());

        d.setStatus(DeviceStatus.ACTIVE);
        assertEquals(DeviceStatus.ACTIVE, d.getStatus());

        // Test temperature
        d.setTemperature(-1.0);
        assertEquals(-1.0, d.getTemperature());

        d.setTemperature(8.5);
        assertEquals(8.5, d.getTemperature());

        // Test updatedAt setter
        Instant later = Instant.now().plusSeconds(60);
        d.setUpdatedAt(later);
        assertEquals(later, d.getUpdatedAt());
    }

    @Test
    void testDefaultValuesOnNewInstance() {
        Device d = new Device();
        // id, pin, status, temperature are null until set
        assertNull(d.getId());
        assertNull(d.getPin());
        assertNull(d.getStatus());
        assertNull(d.getTemperature());
    }
}
