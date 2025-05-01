import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  reactStrictMode: true,
  env: {
    SERVER_API_URL: process.env.SERVER_API_URL,
  },
};

export default nextConfig;