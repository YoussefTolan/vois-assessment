import { Device } from "@/types/Device";
import { ApiFetch } from "./ApiClient";
import { GetAllDevicesOptions } from "@/types/PaginatedResponse";

export const DeviceService = {

    async getAll(options: GetAllDevicesOptions = {}): Promise<{
        data: Device[];
        total: number;
        page: number;
        pageSize: number;
    }> {
        const params = new URLSearchParams();
        if (options.saleable) params.append("saleable", "true");
        if (options.page !== undefined) params.append("page", options.page.toString());
        if (options.size !== undefined) params.append("size", options.size.toString());

        return await ApiFetch.get(`/api/devices?${params.toString()}`);
    },

    async getById(id: string): Promise<Device> {
        return await ApiFetch.get(`/api/devices/${id}`);
    },

    async create(device: Partial<Device>): Promise<Device> {
        return await ApiFetch.post<Device>("/api/devices", device);
    },

    async update(id: string, updates: Partial<Device>): Promise<Device> {
        return await ApiFetch.put(`/api/devices/${id}`, updates);
    },

    async delete(id: string): Promise<void> {
        await ApiFetch.delete(`/api/devices/${id}`);
    },

    async configure(id: string): Promise<Device> {
        return await ApiFetch.post(`/api/configure/${id}`, {});
    }
};