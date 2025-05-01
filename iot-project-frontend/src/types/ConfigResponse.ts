import { DeviceStatus } from "./Device";

export interface ConfigResponse {
    id: string;
    status: DeviceStatus;
    temperature: number;
    updatedAt: string;
}