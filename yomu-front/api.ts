'use client'

import axios from "axios";
import { getSessionData, setSessionData } from "./core/sStorage";
import { getData } from "./core/lStorage";

const api = axios.create({
    baseURL: 'http://localhost:3001/'
});

export const cadUsuario = async (data: any) => {
    try {
        const response = await api.post(`/user/new`, data);
        setSessionData('token', response.data.token);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
};

export const postLogin = async (data: any) => {
    try {
        const response = await api.post('user/login', data);
        setSessionData('token', response.data.token);
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export const getPerfil = async () => {
    try {
        const response = await api.get(`user/${getSessionData('token')}`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}