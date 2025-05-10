package com.vois.iot.warehousing.service.repository;

import com.vois.iot.warehousing.service.model.Device;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@Rollback
class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @BeforeEach
    void setUp() {
        //deviceRepository.deleteAll(); // clean up
        Device device = new Device();
        device.setPin("1234567");
        device.setStatus(DeviceStatus.READY);
        device.setTemperature(5.0);
        Device testDevice = deviceRepository.saveAndFlush(device);
    }


    @Test
    void testFindAllWithPagination() {
        Page<Device> page = deviceRepository.findAll(PageRequest.of(0, 10));
        assertThat(page).isNotEmpty();
    }
}

