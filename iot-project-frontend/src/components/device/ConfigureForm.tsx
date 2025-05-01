"use client";

import { useConfigureDevice } from "@/hooks/useConfigDevice";
import { useSearchParams } from "next/navigation";
import { useState, useEffect } from "react";
import { toast } from "sonner";
import { Button } from "@/components/ui/Button";
import { Input } from "@/components/ui/Input";

export function ConfigureForm() {
    const searchParams = useSearchParams();
    const [deviceId, setDeviceId] = useState("");

    useEffect(() => {
        const id = searchParams.get("id");
        if (id) setDeviceId(id);
    }, [searchParams]);

    const { configureDevice, loading } = useConfigureDevice({
        onSuccess: () => toast.success("Device configured successfully"),
        onError: () => toast.error("Failed to configure device"),
    });

    return (
        <div className="flex flex-col sm:flex-row items-center gap-4 mb-6">
            <Input
                type="text"
                placeholder="Enter Device ID"
                value={deviceId}
                onChange={(e) => setDeviceId(e.target.value)}
                className="w-full"
            />
            <Button
                onClick={() => configureDevice(deviceId)}
                disabled={!deviceId || loading}
                className="min-w-[140px]"
            >
                {loading ? "Configuring..." : "Configure"}
            </Button>
        </div>
    );
}