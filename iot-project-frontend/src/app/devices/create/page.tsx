"use client";

import { useCreateDevice } from "@/hooks/useCreateDevice";
import { toast } from "sonner";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/Button";
import { Input } from "@/components/ui/Input";
import { useState } from "react";

export default function CreateDevicePage() {
    const [pin, setPin] = useState("");
    const router = useRouter();

    const { createDevice, loading } = useCreateDevice({
        onSuccess: () => {
            toast.success("Device created successfully");
            router.push("/devices");
        },
        onError: () => toast.error("Failed to create device"),
    });

    const handleSubmit = () => {
        if (!/^\d{7}$/.test(pin)) {
            toast.error("PIN must be exactly 7 digits.");
            return;
        }
        createDevice(pin);
    };

    return (
        <div className="max-w-2xl mx-auto px-4 py-16 text-center">
            <h1 className="text-3xl font-bold mb-4">Add New Device</h1>
            <p className="text-gray-600 mb-8">
                New devices are created with default status <strong>READY</strong> and temperature <strong>-1°C</strong>. You only need to enter a 7-digit PIN.
            </p>

            <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mb-6 max-w-md mx-auto">
                <Input
                    placeholder="Enter 7-digit PIN"
                    value={pin}
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => setPin(e.target.value)}
                    maxLength={7}
                    inputMode="numeric"
                />
                <Button onClick={handleSubmit} disabled={loading}>
                    {loading ? "Creating..." : "Create Device"}
                </Button>
            </div>
        </div>
    );
}