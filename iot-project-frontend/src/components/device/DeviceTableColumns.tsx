"use client";

import { useRouter } from "next/navigation";
import { ColumnDef } from "@tanstack/react-table";
import { ArrowUpDown, MoreHorizontal } from "lucide-react";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/DropdownMenu";
import { Device } from "@/types/Device";
import { Button } from "../ui/Button";
import { toast } from "sonner";
import { useDeleteDevice } from "@/hooks/useDeleteDevice";

export function useDeviceColumns(): ColumnDef<Device>[] {
    const router = useRouter();
    const { deleteDevice, loading } = useDeleteDevice({
        onSuccess: () => {
            toast.success("Device deleted");
            router.refresh();
        },
        onError: () => toast.error("Failed to delete device"),
    });

    return [
        {
            accessorKey: "pin",
            header: ({ column }) => (
                <Button
                    variant="ghost"
                    onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
                >
                    Pin Code
                    <ArrowUpDown className="ml-2 h-4 w-4" />
                </Button>
            ),
            cell: ({ row }) => <span className="font-mono">{row.getValue("pin")}</span>,
        },
        {
            accessorKey: "status",
            header: "Status",
            cell: ({ row }) => {
                const value: string = row.getValue("status");
                const isActive = value === "ACTIVE";
                const bg = isActive ? "bg-[var(--color-primary)]" : "bg-black";
                return (
                    <span
                        className={`uppercase font-medium text-sm text-white py-1 px-2 rounded w-24 inline-block text-center ${bg}`}
                    >
                        {value}
                    </span>
                );
            },
        },
        {
            accessorKey: "temperature",
            header: () => <div className="text-right">Temperature (°C)</div>,
            cell: ({ row }) => (
                <div className="text-right font-medium">
                    {parseFloat(row.getValue("temperature")).toFixed(1)}°C
                </div>
            ),
        },
        {
            id: "actions",
            cell: ({ row }) => {
                const device = row.original;
                const isReady = device.status === "READY";

                return (
                    <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                            <Button variant="ghost" className="h-8 w-8 p-0">
                                <span className="sr-only">Open menu</span>
                                <MoreHorizontal className="h-4 w-4" />
                            </Button>
                        </DropdownMenuTrigger>
                        <DropdownMenuContent align="end">
                            <DropdownMenuLabel>Actions</DropdownMenuLabel>
                            <DropdownMenuItem
                                onClick={() => {
                                    navigator.clipboard.writeText(device.pin);
                                    toast.success("PIN copied to clipboard");
                                }}
                            >
                                Copy PIN
                            </DropdownMenuItem>
                            <DropdownMenuSeparator />
                            {isReady && (
                                <DropdownMenuItem
                                    onClick={() => router.push(`/configure?id=${device.id}`)}
                                >
                                    Configure Device
                                </DropdownMenuItem>
                            )}
                            <DropdownMenuItem
                                className="text-red-600"
                                onClick={() => deleteDevice(device.id)}
                                disabled={loading}
                            >
                                Delete Device
                            </DropdownMenuItem>
                        </DropdownMenuContent>
                    </DropdownMenu>
                );
            },
        },
    ];
}
