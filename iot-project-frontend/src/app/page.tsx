'use client';

import Hero from "@/components/homeSections/Hero";
import LiveMetrics from "@/components/homeSections/LiveMetrics";
import PlatformOverview from "@/components/homeSections/PlatformOverview";
import QuickActions from "@/components/homeSections/QuickActions";


export default function Home() {
  return (
    <main className="space-y-16">
      <Hero />
      <PlatformOverview />
      <QuickActions />
      <LiveMetrics />
    </main>
  );
}