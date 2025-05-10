package com.vois.iot.warehousing.service.service;
//
//import com.vois.iot.warehousing.service.Service.DeviceService;
//import com.vois.iot.warehousing.service.dto.DeviceRequest;
//import com.vois.iot.warehousing.service.dto.DeviceResponse;
//import com.vois.iot.warehousing.service.model.Device;
//import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
//import com.vois.iot.warehousing.service.repository.DeviceRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.data.domain.*;
//
//import java.time.Instant;
//import java.util.*;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.BDDMockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class DeviceServiceTest {
//
//    @Mock
//    private DeviceRepository repo;
//
//    @InjectMocks
//    private DeviceService service;
//
//    private UUID id;
//    private DeviceRequest req;
//    private Device existing;
//
//    @BeforeEach
//    void setUp() {
//        id = UUID.randomUUID();
//        req = new DeviceRequest();
//        req.setPin("1234567");
//        // default status/temp in createDevice
//
//        existing = new Device();
//        existing.setId(id);
//        existing.setPin("1234567");
//        existing.setStatus(DeviceStatus.READY);
//        existing.setTemperature(-1.0);
//        existing.setUpdatedAt(Instant.now());
//    }
//
//    @Test
//    @DisplayName("createDevice: success")
//    void createDeviceSuccess() {
//        // given
//        Device toSave = new Device();
//        toSave.setPin("1234567");
//        toSave.setStatus(DeviceStatus.READY);
//        toSave.setTemperature(-1.0);
//
//        Device saved = new Device();
//        saved.setId(id);
//        saved.setPin("1234567");
//        saved.setStatus(DeviceStatus.READY);
//        saved.setTemperature(-1.0);
//
//        given(repo.save(any(Device.class))).willReturn(saved);
//
//        // when
//        DeviceResponse resp = service.createDevice(req);
//
//        // then
//        then(repo).should().save(argThat(d ->
//                d.getPin().equals("1234567") &&
//                        d.getStatus() == DeviceStatus.READY &&
//                        d.getTemperature() == -1.0
//        ));
//        assertThat(resp.getId()).isEqualTo(id);
//        assertThat(resp.getPin()).isEqualTo("1234567");
//        assertThat(resp.getStatus()).isEqualTo(DeviceStatus.READY);
//        assertThat(resp.getTemperature()).isEqualTo(-1.0);
//    }
//
//    @Test
//    @DisplayName("createDevice: duplicate PIN → DataIntegrityViolationException")
//    void createDeviceDuplicate() {
//        // given
//        given(repo.save(any())).willThrow(new DataIntegrityViolationException("dup"));
//
//        // when / then
//        assertThrows(DataIntegrityViolationException.class,
//                () -> service.createDevice(req));
//        then(repo).should().save(any(Device.class));
//    }
//
//    @Test
//    @DisplayName("updateDevice: success")
//    void updateDeviceSuccess() {
//        // given
//        DeviceRequest update = new DeviceRequest();
//        update.setStatus(DeviceStatus.ACTIVE);
//        update.setTemperature(5.0);
//
//        Device found = new Device();
//        found.setId(id);
//        found.setPin("1234567");
//        found.setStatus(DeviceStatus.READY);
//        found.setTemperature(-1.0);
//
//        given(repo.findById(id)).willReturn(Optional.of(found));
//        given(repo.save(any(Device.class))).willAnswer(inv -> inv.getArgument(0));
//
//        // when
//        DeviceResponse resp = service.updateDevice(id, update);
//
//        // then
//        assertThat(resp.getStatus()).isEqualTo(DeviceStatus.ACTIVE);
//        assertThat(resp.getTemperature()).isEqualTo(5.0);
//        then(repo).should().findById(id);
//        then(repo).should().save(found);
//    }
//
//    @Test
//    @DisplayName("updateDevice: not found → EntityNotFoundException")
//    void updateDeviceNotFound() {
//        given(repo.findById(id)).willReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class,
//                () -> service.updateDevice(id, req));
//        then(repo).should().findById(id);
//        then(repo).should(never()).save(any());
//    }
//
//    @Test
//    @DisplayName("deleteDevice: success")
//    void deleteDeviceSuccess() {
//        given(repo.existsById(id)).willReturn(true);
//        willDoNothing().given(repo).deleteById(id);
//
//        service.deleteDevice(id);
//
//        then(repo).should().existsById(id);
//        then(repo).should().deleteById(id);
//    }
//
//    @Test
//    @DisplayName("deleteDevice: not found → EntityNotFoundException")
//    void deleteDeviceNotFound() {
//        given(repo.existsById(id)).willReturn(false);
//
//        assertThrows(EntityNotFoundException.class,
//                () -> service.deleteDevice(id));
//        then(repo).should().existsById(id);
//        then(repo).should(never()).deleteById(any());
//    }
//
//    @Test
//    @DisplayName("getDevice: success")
//    void getDeviceSuccess() {
//        given(repo.findById(id)).willReturn(Optional.of(existing));
//
//        DeviceResponse resp = service.getDevice(id);
//
//        assertThat(resp.getId()).isEqualTo(id);
//        assertThat(resp.getPin()).isEqualTo("1234567");
//        then(repo).should().findById(id);
//    }
//
//    @Test
//    @DisplayName("getDevice: not found → EntityNotFoundException")
//    void getDeviceNotFound() {
//        given(repo.findById(id)).willReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class,
//                () -> service.getDevice(id));
//        then(repo).should().findById(id);
//    }
//
//    @Test
//    @DisplayName("listSaleable: returns mapped page")
//    void listSaleable() {
//        List<Device> actives = List.of(existing);
//        Page<Device> pageIn = new PageImpl<>(actives, PageRequest.of(0,1), 1);
//        given(repo.findByStatusAndTemperatureBetweenOrderByPinAsc(
//                eq(DeviceStatus.ACTIVE), eq(0), eq(10), any(Pageable.class)))
//                .willReturn(pageIn);
//
//        Page<DeviceResponse> pageOut = service.listSaleable(PageRequest.of(0,1));
//
//        assertThat(pageOut.getTotalElements()).isEqualTo(1);
//        assertThat(pageOut.getContent().get(0).getId()).isEqualTo(id);
//        then(repo).should().findByStatusAndTemperatureBetweenOrderByPinAsc(
//                DeviceStatus.ACTIVE, 0, 10, PageRequest.of(0,1));
//    }
//
//    @Test
//    @DisplayName("listAll: returns mapped page")
//    void listAll() {
//        List<Device> all = List.of(existing);
//        Page<Device> pageIn = new PageImpl<>(all, PageRequest.of(0,1), 1);
//        given(repo.findAll(any(Pageable.class))).willReturn(pageIn);
//
//        Page<DeviceResponse> pageOut = service.listAll(PageRequest.of(0,1));
//
//        assertThat(pageOut.getTotalElements()).isEqualTo(1);
//        assertThat(pageOut.getContent().get(0).getId()).isEqualTo(id);
//        then(repo).should().findAll(PageRequest.of(0,1));
//    }
//}

import com.vois.iot.warehousing.service.Service.DeviceService;
import com.vois.iot.warehousing.service.dto.DeviceRequest;
import com.vois.iot.warehousing.service.dto.DeviceResponse;
import com.vois.iot.warehousing.service.model.Device;
import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
import com.vois.iot.warehousing.service.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceTest {

    private DeviceRepository repo;
    private DeviceService service;

    @BeforeEach
    void setUp() {
        repo = mock(DeviceRepository.class);
        service = new DeviceService(repo);
    }

    @Test
    void testCreateDevice_Success() {
        DeviceRequest request = new DeviceRequest();
        request.setPin("1234567");

        Device mockDevice = new Device();
        mockDevice.setId(UUID.randomUUID());
        mockDevice.setPin("1234567");
        mockDevice.setStatus(DeviceStatus.READY);
        mockDevice.setTemperature(-1.0);

        when(repo.save(any(Device.class))).thenReturn(mockDevice);

        DeviceResponse response = service.createDevice(request);
        assertEquals("1234567", response.getPin());
        assertEquals(DeviceStatus.READY, response.getStatus());
    }

    @Test
    void testCreateDevice_DuplicatePIN() {
        DeviceRequest request = new DeviceRequest();
        request.setPin("1234567");

        when(repo.save(any(Device.class))).thenThrow(new DataIntegrityViolationException("Duplicate"));

        assertThrows(DataIntegrityViolationException.class, () -> service.createDevice(request));
    }

    @Test
    void testUpdateDevice_FullUpdate() {
        UUID id = UUID.randomUUID();
        DeviceRequest request = new DeviceRequest();
        request.setStatus(DeviceStatus.ACTIVE);
        request.setTemperature(5.5);

        Device device = new Device();
        device.setId(id);
        device.setPin("9876543");
        device.setStatus(DeviceStatus.READY);
        device.setTemperature(-1.0);

        when(repo.findById(id)).thenReturn(Optional.of(device));
        when(repo.save(any(Device.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DeviceResponse response = service.updateDevice(id, request);
        assertEquals(DeviceStatus.ACTIVE, response.getStatus());
        assertEquals(5.5, response.getTemperature());
    }

    @Test
    void testUpdateDevice_PartialUpdate() {
        UUID id = UUID.randomUUID();
        DeviceRequest request = new DeviceRequest();
        request.setTemperature(8.0);  // No status provided

        Device device = new Device();
        device.setId(id);
        device.setPin("1231231");
        device.setStatus(DeviceStatus.READY);
        device.setTemperature(-1.0);

        when(repo.findById(id)).thenReturn(Optional.of(device));
        when(repo.save(any(Device.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DeviceResponse response = service.updateDevice(id, request);
        assertEquals(DeviceStatus.READY, response.getStatus()); // unchanged
        assertEquals(8.0, response.getTemperature());
    }

    @Test
    void testUpdateDevice_NotFound() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.updateDevice(id, new DeviceRequest()));
    }

    @Test
    void testDeleteDevice_Success() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(true);
        doNothing().when(repo).deleteById(id);
        assertDoesNotThrow(() -> service.deleteDevice(id));
    }

    @Test
    void testDeleteDevice_NotFound() {
        UUID id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteDevice(id));
    }

    @Test
    void testGetDevice_Success() {
        UUID id = UUID.randomUUID();
        Device device = new Device();
        device.setId(id);
        device.setPin("7777777");
        device.setStatus(DeviceStatus.READY);
        device.setTemperature(5.0);

        when(repo.findById(id)).thenReturn(Optional.of(device));
        DeviceResponse response = service.getDevice(id);

        assertEquals("7777777", response.getPin());
    }

    @Test
    void testGetDevice_NotFound() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getDevice(id));
    }

    @Test
    void testListAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Device> devices = List.of(new Device());
        Page<Device> page = new PageImpl<>(devices);
        when(repo.findAll(pageable)).thenReturn(page);

        Page<DeviceResponse> result = service.listAll(pageable);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void updateDevice_setsOnlyTemperatureIfStatusIsNull() {
        UUID id = UUID.randomUUID();

        DeviceRequest request = new DeviceRequest();
        request.setTemperature(5.5);

        Device existingDevice = new Device();
        existingDevice.setId(id);
        existingDevice.setPin("9999999");
        existingDevice.setStatus(DeviceStatus.READY);
        existingDevice.setTemperature(-1.0);

        when(repo.findById(id)).thenReturn(Optional.of(existingDevice));
        when(repo.save(any(Device.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DeviceResponse response = service.updateDevice(id, request);

        assertEquals(5.5, response.getTemperature());
        assertEquals(DeviceStatus.READY, response.getStatus());
        verify(repo).save(any(Device.class));
    }

    @Test
    void updateDevice_shouldUpdateTemperature_whenTemperatureProvided() {
        UUID deviceId = UUID.randomUUID();
        Device existingDevice = new Device();
        existingDevice.setId(deviceId);
        existingDevice.setPin("1234567");
        existingDevice.setStatus(DeviceStatus.READY);
        existingDevice.setTemperature(-1.0);

        DeviceRequest updateRequest = new DeviceRequest();
        updateRequest.setTemperature(6.5);

        when(repo.findById(deviceId)).thenReturn(Optional.of(existingDevice));
        when(repo.save(any(Device.class))).thenAnswer(i -> i.getArgument(0));

        DeviceResponse response = service.updateDevice(deviceId, updateRequest);

        assertEquals(6.5, response.getTemperature());
        verify(repo).save(any(Device.class));
    }

    @Test
    void updateDevice_shouldNotChangeTemperature_whenTemperatureIsNull() {
        UUID deviceId = UUID.randomUUID();
        Device existingDevice = new Device();
        existingDevice.setId(deviceId);
        existingDevice.setPin("1234567");
        existingDevice.setStatus(DeviceStatus.READY);
        existingDevice.setTemperature(5.0); // original temp

        DeviceRequest updateRequest = new DeviceRequest();
        updateRequest.setStatus(DeviceStatus.ACTIVE);
        updateRequest.setTemperature(null); // temperature not provided

        when(repo.findById(deviceId)).thenReturn(Optional.of(existingDevice));
        when(repo.save(any(Device.class))).thenAnswer(i -> i.getArgument(0));

        DeviceResponse response = service.updateDevice(deviceId, updateRequest);

        assertEquals(5.0, response.getTemperature()); // unchanged
        assertEquals(DeviceStatus.ACTIVE, response.getStatus()); // status updated
        verify(repo).save(any(Device.class));
    }


    @Test
    void testListSaleable() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Device> devices = List.of(new Device());
        Page<Device> page = new PageImpl<>(devices);
        when(repo.findByStatusAndTemperatureBetweenOrderByPinAsc(DeviceStatus.ACTIVE, 0, 10, pageable)).thenReturn(page);

        Page<DeviceResponse> result = service.listSaleable(pageable);
        assertEquals(1, result.getTotalElements());
    }
}
