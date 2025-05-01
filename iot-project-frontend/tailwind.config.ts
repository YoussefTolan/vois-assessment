import type { Config } from 'tailwindcss';

const config: Config = {
    content: ['./src/**/*.{ts,tsx}'],
    theme: {
        extend: {
            fontFamily: {
                sans: ['var(--font-sans)'],
            },
            colors: {
                main: 'rgb(30, 65, 100)',
                secondary: 'rgb(218, 61, 0)',
                accent: 'rgb(118, 173, 225)',
                lightgray: 'rgb(211, 219, 230)',
                inactive: 'rgb(91, 101, 115)',
            },
            borderRadius: {
                DEFAULT: '12px',
                sm: '6px',
                md: '12px',
                lg: '16px'
            },
            boxShadow: {
                DEFAULT: '0 0 4px rgba(0, 0, 0, 0.04), 0 8px 16px rgba(0, 0, 0, 0.08)',
            },
        },
    },
    plugins: [],
};

export default config;