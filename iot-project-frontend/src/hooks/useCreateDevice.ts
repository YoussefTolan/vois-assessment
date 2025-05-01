import { useState } from "react";
import { DeviceService } from "@/services/DeviceService";

interface UseCreateDeviceOptions {
    onSuccess?: () => void;
    onError?: () => void;
}

export function useCreateDevice({ onSuccess, onError }: UseCreateDeviceOptions) {
    const [loading, setLoading] = useState(false);

    const createDevice = async (pin: string) => {
        setLoading(true);
        try {
            await DeviceService.create({ pin });
            onSuccess?.();
        } catch (err) {
            console.error("Device creation failed", err);
            onError?.();
        } finally {
            setLoading(false);
        }
    };

    return { createDevice, loading };
}