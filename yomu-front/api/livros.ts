import { getSessionData } from "@/core/sStorage";
import { api } from "./api";
import { get } from "http";

export async function getUserLivros() {
    try {
        const response = await api.get(`/api/livros/usuario/${getSessionData('user').id}`);
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

export async function deleteLivroId(id: number) {
    try {
        const response = await api.delete(`/api/livros/${id}`);
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function getProgressoLivro(id: string) {
    try {
        const response = await api.get(`/api/progressos/livro/${id}`);
        if (response.data.length == 0) return 0;
        let prog = 0;
        response.data.forEach((p: any) => {
            prog += p.quantidade;
        });
        return prog;
    } catch (error) {
        console.error(error);
        throw error;
    }
}

export async function getContinuarLeitura() {
    try {
        const response: any = await getUserLivros();
        
        let result: any = [];
        for (let i = 0; i < response.length; i++) {
            const livro = response[i];
            const prog: any = await getProgressoLivro(livro.id);
            let porc = 0;

            if(livro.tipoRegistro == 'PAGINA') {
                porc = (prog / livro.numeroPaginas) * 100;
            } else {
                porc = (prog / livro.numeroCapitulos) * 100;
            }

            result.push({
                id: livro.id,
                titulo: livro.titulo,
                autor: livro.autor,
                progresso: Math.trunc(porc),
            })
        };

        return result;
    } catch (error) {
        console.error(error);
        throw error;
    }
}