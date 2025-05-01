"use client";

import { Skeleton } from "@/components/ui/Skeleton";

export default function DeviceTableSkeleton() {
    return (
        <div className="space-y-4">
            {Array.from({ length: 5 }).map((_, i) => (
                <div
                    key={i}
                    className="grid grid-cols-4 gap-4 items-center bg-white p-4 rounded-md shadow-sm border"
                >
                    <Skeleton className="h-4 w-32" />  {/* PIN Code */}
                    <Skeleton className="h-6 w-24 rounded-full" />  {/* Status badge */}
                    <Skeleton className="h-4 w-20 ml-auto" />  {/* Temperature */}
                    <Skeleton className="h-8 w-8 ml-auto rounded-md" />  {/* Actions */}
                </div>
            ))}
        </div>
    );
}