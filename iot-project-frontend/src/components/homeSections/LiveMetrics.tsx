"use client";

const metrics = [
    {
        label: "Total Devices",
        value: 2000000
    },
    {
        label: "Active Devices",
        value: 1000000
    }
];

export default function LiveMetrics() {
    return (
        <section className="py-12 bg-white">
            <div className="max-w-4xl mx-auto px-4">
                <h2 className="text-2xl md:text-3xl font-bold text-center mb-10">Live Metrics</h2>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                    {metrics.map((m, i) => (
                        <div
                            key={i}
                            className="rounded-lg bg-[var(--color-primary)] text-white p-6 text-center shadow"
                        >
                            <p className="text-3xl font-bold mb-2">{m.value.toLocaleString()}</p>
                            <p className="text-sm font-medium tracking-wide uppercase">{m.label}</p>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
}
