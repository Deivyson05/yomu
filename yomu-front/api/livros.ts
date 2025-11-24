import { getSessionData } from "@/core/sStorage";
import { api } from "./api";

export async function getUserLivros() {
    try {
        const response = await api.get(`/api/livros/usuario/${getSessionData('user').id}`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function getLivrosId(id: string) {
    try {
        const response = await api.get(`/api/livros/${id}`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function getLivroFinalizado(finalizado: boolean) {
    try {
        const response = await api.get(`/api/livros/usuario/${getSessionData('user').id}/finalizado/${finalizado}`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function setLivroConcluido(id: any) {
    try {
        const response = await api.patch(`/api/livros/${id}/finalizar`);
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function postLivro(data: any) {
    try {
        const response = await api.post(`/api/livros/usuario/${getSessionData('user').id}`, data);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function putLivro(id: any, data: any) {
    try {
        const response = await api.put(`/api/livros/${id}`, data);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}