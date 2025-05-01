'use client';

import { useState } from 'react';
import Image from 'next/image';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { clsx } from 'clsx';
import { Menu, X } from 'lucide-react';

const NAV_LINKS = [
    { name: 'Devices', href: '/devices' },
    { name: 'Add Device', href: '/devices/create' },
    { name: 'Configure', href: '/configure' },
    { name: 'About', href: '/about' },
];

function NavItem({ name, href, isActive }: { name: string; href: string; isActive: boolean }) {
    return (
        <Link
            href={href}
            aria-current={isActive ? 'page' : undefined}
            className={clsx(
                'relative text-sm font-medium transition-colors duration-200 px-2 py-1',
                isActive ? 'text-[var(--color-primary)] font-semibold' : 'text-[var(--color-inactive)] hover:text-[var(--color-primary)]'
            )}
        >
            {name}
        </Link>
    );
}

export default function Navbar() {
    const pathname = usePathname();
    const [menuOpen, setMenuOpen] = useState(false);

    return (
        <nav className="w-full border-b border-gray-200 bg-[var(--color-background)]" role="navigation">
            <div className="max-w-screen-xl mx-auto px-4 py-5 flex items-center justify-between">
                {/* Logo */}
                <Link href="/" aria-label="Go to homepage">
                    <Image src="/images/logo.svg" alt="IoT Warehouse Logo" width={40} height={40} priority />
                </Link>

                {/* Desktop Nav */}
                <div className="hidden lg:flex flex-1 justify-center space-x-6 items-center" aria-label="Primary Navigation">
                    {NAV_LINKS.map(({ name, href }) => (
                        <NavItem key={name} name={name} href={href} isActive={pathname === href} />
                    ))}
                </div>

                {/* Mobile Toggle */}
                <button
                    onClick={() => setMenuOpen(!menuOpen)}
                    className="lg:hidden text-[var(--color-primary)] focus:outline-none"
                    aria-label="Toggle menu"
                >
                    {menuOpen ? <X size={24} /> : <Menu size={24} />}
                </button>
            </div>

            {/* Mobile Menu */}
            <div
                className={clsx(
                    'lg:hidden overflow-hidden transition-all duration-300 ease-in-out',
                    menuOpen ? 'max-h-[1000px] opacity-100 py-4 px-4' : 'max-h-0 opacity-0 px-4 py-0'
                )}
                style={{ transitionProperty: 'max-height, opacity, padding' }}
            >
                <div className="space-y-3">
                    {NAV_LINKS.map(({ name, href }) => (
                        <Link
                            key={name}
                            href={href}
                            onClick={() => setMenuOpen(false)}
                            className={clsx(
                                'block text-base font-medium py-2 border-b border-gray-100',
                                pathname === href ? 'text-[var(--color-primary)] font-semibold' : 'text-[var(--color-inactive)] hover:text-[var(--color-primary)]'
                            )}
                        >
                            {name}
                        </Link>
                    ))}
                </div>
            </div>
        </nav>
    );
}