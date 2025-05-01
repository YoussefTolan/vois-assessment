'use client';

import Link from 'next/link';
import { AlertTriangle } from 'lucide-react';
import { Button } from '@/components/ui/Button';


export default function NotFound() {
    return (
        <div className="flex flex-col items-center justify-center h-[80vh] text-center px-4">
            <AlertTriangle size={48} className="text-[var(--color-primary)] mb-4" />

            <h1 className="text-3xl md:text-4xl font-bold text-[var(--color-text)] mb-2">
                Device Not Found
            </h1>

            <p className="text-md text-[var(--color-inactive)] max-w-md mb-6">
                The device you are looking for either does not exist, is not been added yet, or its configuration is incomplete.
            </p>

            <Link href="/devices">
                <Button className="px-6 py-2 text-sm bg-[var(--color-primary)] text-white hover:bg-red-700">
                    Browse Available Devices
                </Button>
            </Link>
        </div>
    );
}