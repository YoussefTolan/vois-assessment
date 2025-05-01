import { ConfigService } from "@/services/ConfigService";
import { useState, useCallback } from "react";

interface UseConfigureDeviceOptions {
    onSuccess?: () => void;
    onError?: (error?: unknown) => void;
}

export function useConfigureDevice({ onSuccess, onError }: UseConfigureDeviceOptions) {
    const [loading, setLoading] = useState(false);

    const configureDevice = useCallback(async (deviceId: string) => {
        setLoading(true);
        try {
            await ConfigService.configure(deviceId);
            onSuccess?.();
        } catch (err) {
            console.error("Configuration error:", err);
            onError?.(err);
        } finally {
            setLoading(false);
        }
    }, [onSuccess, onError]);

    return { configureDevice, loading };
}