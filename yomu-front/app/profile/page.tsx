'use client'
import { useState, useEffect } from "react";
import { getPerfil } from "@/api";
import { getSessionData } from "@/core/sStorage";


export default function Profile() {
    const [nome, setNome] = useState("Nome");
    const [email, setEmail] = useState("Email");

    useEffect(()=>{
        const getUser = async () => {
            try {
                const response = await getPerfil();
                console.log(response);
                setNome(response.nome);
                setEmail(response.email);
            } catch (error) {
                console.error(error);
            }
        };

        getUser();
    },[])

    return (
        <main>
            <h1>{nome}</h1>
            <h2>{email}</h2>
        </main>
    )
}