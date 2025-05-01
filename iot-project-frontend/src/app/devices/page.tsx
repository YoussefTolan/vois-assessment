"use client";

import { useDevices } from "@/hooks/useDevices";
import { useDeviceColumns } from "@/components/device/DeviceTableColumns";
import { DataTable } from "@/components/ui/DataTable";
import { Input } from "@/components/ui/Input";
import DeviceTableSkeleton from "@/components/device/DeviceTableSkeleton";
import { useDebouncedValue } from "@/hooks/useDebouncedValue";
import { useState } from "react";
import { Switch } from "@/components/ui/Switch";

const PAGE_SIZE = 10;

export default function DevicesPage() {
    const [search, setSearch] = useState("");
    const debouncedSearch = useDebouncedValue(search, 300);
    const [showOnlyActive, setShowOnlyActive] = useState(false);
    const [page, setPage] = useState(0);

    const { devices, loading, total } = useDevices({
        saleable: showOnlyActive,
        page,
        size: PAGE_SIZE,
    });

    const columns = useDeviceColumns();
    // const totalPages = Math.ceil(total / PAGE_SIZE);

    return (
        <div className="max-w-7xl mx-auto px-4 py-12">
            <h1 className="text-3xl font-bold mb-6">All Devices</h1>
            <p className="text-gray-600 mb-4 text-sm">
                Devices are listed in ascending order of their secret pin code. Devices must be{" "}
                <strong>ACTIVE</strong> and between <strong>0–10°C</strong> to be available for sale.
            </p>

            <div className="mb-6 max-w-sm space-y-4">
                <Input
                    aria-label="Search devices by PIN code"
                    placeholder="Search by Pin Code..."
                    value={search}
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                        setPage(0);
                        setSearch(e.target.value);
                    }}
                />

                <div className="flex items-center gap-2">
                    <Switch
                        id="activeToggle"
                        checked={showOnlyActive}
                        onCheckedChange={(checked) => {
                            setShowOnlyActive(checked);
                            setPage(0);
                        }}
                    />
                    <label htmlFor="activeToggle" className="text-sm text-gray-700">
                        Show only ACTIVE devices
                    </label>
                </div>
            </div>

            {loading ? (
                <DeviceTableSkeleton />
            ) : devices.length > 0 ? (
                <DataTable
                    columns={columns}
                    data={devices.filter((device) =>
                        device.pin.toLowerCase().includes(debouncedSearch.toLowerCase())
                    )}
                    loading={loading}
                    page={page}
                    pageSize={PAGE_SIZE}
                    total={total}
                    onPageChange={setPage}
                />
            ) : (
                <p className="text-center text-gray-500">No devices found with this pin.</p>
            )}
        </div>
    );
}