'use client'

import axios from "axios";
import { getSessionData} from "../core/sStorage";

export const api = axios.create({
    baseURL: 'https://yomu-api-0tys.onrender.com/yomu'
});

export async function getUserLivros() {
    try {
        const response = await api.get(`/api/livros/usuario/17185358-ef54-4b9a-a68f-f19eb3a9c3e0`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function getLivroId(id: string) {
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
        const response = await api.get(`/api/livros/usuario/17185358-ef54-4b9a-a68f-f19eb3a9c3e0/finalizado/${finalizado}`);
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
        const response = await api.post(`/api/livros/usuario/17185358-ef54-4b9a-a68f-f19eb3a9c3e0`, data);
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

export async function deleteLivroId(id: number) {
    try {
        const response = await api.delete(`/api/livros/${id}`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}