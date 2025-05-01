const baseURL = process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:8080";

export const ApiFetch = {
    async get<T>(url: string): Promise<T> {
        const res = await fetch(`${baseURL}${url}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
            cache: "no-store",
        });
        if (!res.ok) throw new Error(`GET ${url} failed: ${res.statusText}`);
        return res.json();
    },

    async post<T>(url: string, body: unknown): Promise<T> {
        const res = await fetch(`${baseURL}${url}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body),
        });
        if (!res.ok) throw new Error(`POST ${url} failed: ${res.statusText}`);
        return res.json();
    },

    async put<T>(url: string, body: unknown): Promise<T> {
        const res = await fetch(`${baseURL}${url}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body),
        });
        if (!res.ok) throw new Error(`PUT ${url} failed: ${res.statusText}`);
        return res.json();
    },

    async delete<T>(url: string): Promise<T> {
        const res = await fetch(`${baseURL}${url}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
        });
        if (!res.ok) throw new Error(`DELETE ${url} failed: ${res.statusText}`);
        return res.json().catch(() => null as T); // handle 204 No Content
    },
};