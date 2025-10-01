'use client'

import axios from "axios";
import { getSessionData, setSessionData } from "./core/sStorage";
import { getData } from "./core/lStorage";

const api = axios.create({
    baseURL: 'http://urldaapi:3000/'
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