import Image from "next/image"
import Link from "next/link"
import { Facebook, Linkedin, Youtube, Instagram, Twitter } from "lucide-react"

export default function Footer() {
    const footerLinks = {
        Devices: [
            { name: "View Devices", href: "/devices" },
            { name: "Add New Device", href: "/devices/create" },
            { name: "Saleable Devices", href: "/devices?saleable=true" },
        ],
        Configuration: [
            { name: "Configure Device", href: "/configure" },
            { name: "How Configuration Works", href: "/about#config" },
            { name: "Configuration Service", href: "/configure/info" },
        ],
        Support: [
            { name: "About Us", href: "/about" },
            { name: "Contact", href: "/contact" },
            { name: "Documentation", href: "/docs" },
            { name: "FAQs", href: "/faq" },
        ],
        Legal: [
            { name: "Terms of Use", href: "/terms" },
            { name: "Privacy Policy", href: "/privacy" },
        ],
    }

    const bottomLinks = [
        { name: "Devices", href: "/devices" },
        { name: "Add Device", href: "/devices/create" },
        { name: "Configure", href: "/configure" },
        { name: "About", href: "/about" },
        { name: "Contact", href: "/contact" },
    ]

    const socialLinks = [
        { Icon: Facebook, url: "https://www.facebook.com/VodafoneEgypt" },
        { Icon: Linkedin, url: "https://www.linkedin.com/company/vodafone" },
        { Icon: Youtube, url: "https://www.youtube.com/vodafone" },
        { Icon: Instagram, url: "https://www.instagram.com/vodafoneegypt/" },
        { Icon: Twitter, url: "https://twitter.com/VodafoneGroup" },
    ]

    return (
        <footer className="bg-[var(--color-background)] pt-16 pb-8 mt-12 font-poppins border-t">
            <div className="container mx-auto px-4">
                {/* Main Footer Links */}
                <div className="grid grid-cols-2 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-12">
                    {Object.entries(footerLinks).map(([section, links]) => (
                        <div key={section}>
                            <h3 className="text-lg font-bold text-[var(--color-primary)] mb-4">{section}</h3>
                            <ul className="space-y-2">
                                {links.map((link, i) => (
                                    <li key={i}>
                                        <Link
                                            href={link.href}
                                            className="text-gray-600 hover:text-[var(--color-primary)] text-sm block truncate"
                                        >
                                            {link.name}
                                        </Link>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    ))}
                </div>

                {/* Logo and Social */}
                <div className="flex flex-col lg:flex-row justify-between items-start lg:items-center border-t border-gray-200 pt-8 pb-6 gap-6">
                    <div>
                        <Image
                            src="/images/logo.svg"
                            alt="IoT Warehouse Logo"
                            width={150}
                            height={40}
                            className="mb-3 h-10 w-auto"
                        />
                        <p className="text-gray-600 text-sm mb-4">IoT Warehouse & Configuration System</p>
                        <div className="flex space-x-3">
                            {socialLinks.map(({ Icon, url }, i) => (
                                <a
                                    key={i}
                                    href={url}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="w-8 h-8 rounded-full bg-[var(--color-primary)] flex items-center justify-center text-white hover:bg-red-700 transition-colors"
                                >
                                    <Icon size={18} />
                                </a>
                            ))}
                        </div>
                    </div>

                </div>

                {/* Bottom Links */}
                <div className="flex flex-col md:flex-row justify-between items-center border-t border-gray-200 pt-6 gap-y-4">
                    <p className="text-gray-600 text-sm">© {new Date().getFullYear()} IoT Warehouse. All rights reserved.</p>
                    <div className="flex flex-wrap justify-center gap-x-4 gap-y-2">
                        {bottomLinks.map((link, index) => (
                            <Link
                                key={index}
                                href={link.href}
                                className="text-gray-600 hover:text-[var(--color-primary)] text-sm"
                            >
                                {link.name}
                            </Link>
                        ))}
                    </div>
                </div>
            </div>
        </footer>
    )
}