import { getSessionData } from "@/core/sStorage";
import { api } from "./api";

export async function postProgresso(id: number, data: any) {
    try {
        const response = await api.post(`/api/progressos/usuario/${getSessionData('user').id}/livro/${id}`, data);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function getProgresso(id: string) {
    try {
        const response = await api.get(`/api/progressos/livro/${id}`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}