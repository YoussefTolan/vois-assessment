import { ApiFetch } from "./ApiClient";

export const ConfigService = {
    async configure(deviceId: string): Promise<void> {
        try {
            await ApiFetch.put(`/api/configure/${deviceId}`, {});
        } catch (error) {
            console.error("Configuration failed:", error);
            throw new Error("Device configuration failed. Please try again.");
        }
    },
};