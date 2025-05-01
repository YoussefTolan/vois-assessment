import { useCallback, useEffect, useState } from "react";
import { Device } from "@/types/Device";
import { DeviceService } from "@/services/DeviceService";
export function useDevices({ saleable = false, page = 0, size = 20 } = {}) {
    const [devices, setDevices] = useState<Device[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [total, setTotal] = useState(0);

    const fetchDevices = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await DeviceService.getAll({ saleable, page, size });
            setDevices(res.data);
            setTotal(res.total);
        } catch (e) {
            console.error("Failed to load devices", e);
            setError("Failed to load devices");
        } finally {
            setLoading(false);
        }
    }, [saleable, page, size]);

    useEffect(() => {
        fetchDevices();
    }, [fetchDevices]);

    return { devices, total, loading, error, refetch: fetchDevices };
}