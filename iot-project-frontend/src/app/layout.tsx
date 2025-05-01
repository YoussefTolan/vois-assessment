import { Poppins } from 'next/font/google';
import './globals.css';
import { Metadata } from 'next';
import { ReactNode } from 'react';
import Navbar from '@/components/layout/Navbar';
import Footer from '@/components/layout/Footer';
import { Toaster } from 'sonner';

const poppins = Poppins({
  subsets: ['latin'],
  weight: ['400', '500', '600', '700'],
  variable: '--font-poppins',
  display: 'swap',
});

export const metadata: Metadata = {
  title: 'IoT Device Inventory | London Warehouse',
  description: 'Track, configure, and manage IoT devices ready for sale in the UK market.',
  openGraph: {
    title: 'IoT Device Inventory',
    description: 'Manage and configure millions of IoT tracking devices from warehouse to deployment.',
    url: 'https://iot-warehouse.example.com',
    siteName: 'IoT Device Inventory',
    type: 'website',
  },
};

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="en" className={poppins.variable}>
      <body className="font-sans bg-[var(--color-background)] text-[var(--color-text)]">
        <Navbar />
        <main className="min-h-screen"> <Toaster richColors />{children}</main>
        <Footer />
      </body>
    </html>
  );
}