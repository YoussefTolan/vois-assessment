"use client";

import { Server, Settings2, CheckCircle } from "lucide-react";

const features = [
    {
        icon: <Server className="w-8 h-8 text-[var(--color-primary)]" />,
        title: "Device Inventory",
        description: "Track and manage IoT devices across global warehouses."
    },
    {
        icon: <Settings2 className="w-8 h-8 text-[var(--color-primary)]" />,
        title: "Configuration Service",
        description: "Easily configure devices to meet UK industry standards."
    },
    {
        icon: <CheckCircle className="w-8 h-8 text-[var(--color-primary)]" />,
        title: "Sale-ready Compliance",
        description: "Ensure all devices are compliant and ready for distribution."
    }
];

export default function PlatformOverview() {
    return (
        <section className="py-12 bg-white text-center">
            <h2 className="text-3xl md:text-4xl font-bold mb-6">Manage and configure millions of IoT devices at scale</h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-6xl mx-auto mt-12 px-4">
                {features.map((f, i) => (
                    <div key={i} className="flex flex-col items-center text-center">
                        {f.icon}
                        <h3 className="text-lg font-semibold mt-4 mb-2">{f.title}</h3>
                        <p className="text-gray-600 text-sm max-w-xs">{f.description}</p>
                    </div>
                ))}
            </div>
        </section>
    );
}
