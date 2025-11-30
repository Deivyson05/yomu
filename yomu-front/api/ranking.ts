import { api } from "./api";

export async function getRanking() {
    try {
        const response = await api.get(`/api/rankings/geral/SEMANAL`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}