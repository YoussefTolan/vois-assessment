import { Suspense } from "react";
import { ConfigureForm } from "@/components/device/ConfigureForm";

export default function ConfigureDevicePage() {
    return (
        <div className="max-w-xl mx-auto px-4 py-16 text-center">
            <h1 className="text-3xl font-bold mb-6">Configure Device</h1>
            <p className="text-gray-600 mb-8 text-sm">
                Enter the ID of a device to send it to the configuration service. This will set its
                status to <strong>ACTIVE</strong> and assign a valid temperature (0–10°C).
            </p>

            <Suspense fallback={<div>Loading...</div>}>
                <ConfigureForm />
            </Suspense>
        </div>
    );
}