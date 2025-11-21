'use client'

import axios from "axios";
import { getSessionData, setSessionData } from "./core/sStorage";
import { getData } from "./core/lStorage";

const api = axios.create({
    baseURL: 'https://sturdy-space-trout-4xw9ppx56g7357r-8080.app.github.dev/yomu'
});

export const cadUsuario = async (data: any) => {
    try {
        const response = await api.post(`/api/usuarios`, data);
        setSessionData('token', response.data.token);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
};

export const postLogin = async (data: any) => {
    try {
        const response = await api.post('/api/usuarios/login', data);
        setSessionData('user', response.data);
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export const getPerfil = async () => {
    try {
        const response = await api.get(`/api/usuarios/${getSessionData('user').id}`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}