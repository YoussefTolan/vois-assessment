import { useState } from "react";
import { DeviceService } from "@/services/DeviceService";

interface UseDeleteDeviceOptions {
    onSuccess?: () => void;
    onError?: (error: unknown) => void;
}

export function useDeleteDevice({ onSuccess, onError }: UseDeleteDeviceOptions = {}) {
    const [loading, setLoading] = useState(false);

    const deleteDevice = async (id: string) => {
        setLoading(true);
        try {
            await DeviceService.delete(id);
            onSuccess?.();
        } catch (err) {
            console.error("Delete error:", err);
            onError?.(err ?? new Error("Unknown deletion error"));
        } finally {
            setLoading(false);
        }
    };

    return { deleteDevice, loading };
}