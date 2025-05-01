export type DeviceStatus = "READY" | "ACTIVE";

export interface Device {
    id: string;
    pin: string;
    status: DeviceStatus;
    temperature: number;
    createdAt: string;
    updatedAt: string;
}

