"use client";

import { useEffect, useRef, useState } from "react";
import Image from "next/image";

export default function Hero() {
    const videoRef = useRef<HTMLVideoElement | null>(null);
    const [videoError, setVideoError] = useState(false);

    useEffect(() => {
        if (videoRef.current) {
            videoRef.current
                .play()
                .catch((err) => {
                    console.warn("Autoplay failed:", err);
                    setVideoError(true);
                });
        }
    }, []);

    return (
        <section className="relative w-full h-[80vh] overflow-hidden">
            {/* Background Video or Fallback Image */}
            {!videoError ? (
                <video
                    ref={videoRef}
                    autoPlay
                    loop
                    muted
                    playsInline
                    preload="auto"
                    onError={() => setVideoError(true)}
                    className="absolute inset-0 w-full h-full object-cover"
                >
                    <source src="/videos/intro.mp4" type="video/mp4" />
                </video>
            ) :
                (
                    <Image
                        src="/images/placeholder.png"
                        alt="IoT background"
                        fill
                        className="absolute object-cover"
                    />
                )}


            {/* Overlay */}
            <div className="relative inset-0 bg-black bg-opacity-50 z-10" />

            {/* Content */}
            <div className="relative z-20 flex flex-col items-center justify-center h-full text-center px-4">
                <h1 className="text-white text-4xl md:text-5xl font-bold mb-4 drop-shadow-lg">
                    Vodafone IoT
                </h1>
                <p className="text-white text-lg md:text-xl font-medium drop-shadow">
                    Pioneering IoT through global networks and future-ready technologies
                </p>
            </div>
        </section>
    );
}