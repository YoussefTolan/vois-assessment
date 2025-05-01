// package com.vois.iot.warehousing.service.SQLseed;

// import com.vois.iot.warehousing.service.model.Device;
// import com.vois.iot.warehousing.service.model.Enums.DeviceStatus;
// import com.vois.iot.warehousing.service.repository.DeviceRepository;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Profile;
// import org.springframework.stereotype.Component;

// import java.util.Random;
// import java.util.UUID;

// @Component
// @Profile("!test") // Avoid running during tests
// public class DeviceSeeder implements CommandLineRunner {

//     private final DeviceRepository repository;

//     public DeviceSeeder(DeviceRepository repository) {
//         this.repository = repository;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (repository.count() >= 15)
//             return;

//         Random random = new Random();

//         for (int i = 0; i < 15; i++) {
//             Device device = new Device();
//             device.setId(UUID.randomUUID());
//             device.setPin(String.format("%07d", random.nextInt(9999999)));
//             device.setStatus(random.nextBoolean() ? DeviceStatus.READY : DeviceStatus.ACTIVE);
//             device.setTemperature(device.getStatus() == DeviceStatus.READY ? -1.0 : random.nextDouble(11.0));
//             repository.save(device);
//         }

//         System.out.println("✅ Seeded 15 devices");
//     }
// }