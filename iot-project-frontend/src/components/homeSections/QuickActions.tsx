'use client';

import Link from "next/link";
import { ClipboardList, PlusCircle, Settings } from "lucide-react";
import { Button } from "../ui/Button";


const actions = [
    {
        title: "View All Devices",
        icon: <ClipboardList className="w-6 h-6 mr-2" />,
        href: "/devices"
    },
    {
        title: "Add New Device",
        icon: <PlusCircle className="w-6 h-6 mr-2" />,
        href: "/devices/create"
    },
    {
        title: "Configure a Device",
        icon: <Settings className="w-6 h-6 mr-2" />,
        href: "/configure"
    }
];

export default function QuickActions() {
    return (
        <section className="py-12 bg-[var(--color-background)]">
            <div className="max-w-5xl mx-auto px-4">
                <h2 className="text-2xl md:text-3xl font-bold text-center mb-8">Quick Actions</h2>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    {actions.map((a, i) => (
                        <div
                            key={i}
                            className=" rounded-lg p-6 flex items-center justify-center shadow-sm hover:shadow-md transition text-center"
                        >
                            <Link href={a.href}>
                                <Button className="w-full max-w-[220px] mx-auto flex justify-center items-center bg-[var(--color-primary)]">
                                    {a.icon} {a.title}
                                </Button>
                            </Link>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
}