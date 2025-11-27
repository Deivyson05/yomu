'use client'

import Link from "next/link";
import { Input } from "@/components/ui/input";
import { Label } from "@radix-ui/react-label";
import { getData, updateData } from "@/core/lStorage";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { cadUsuario } from "@/api/api";

export default function Credenciais() {
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [repSenha, setRepSenha] = useState("");
    const [erro, setErro] = useState("");

    const router = useRouter();

    const handleEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(event.target.value);
    }
    const handleSenha = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSenha(event.target.value);
    }
    const handleRepSenha = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRepSenha(event.target.value);
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        updateData('cadastro', 'email', email);

        if(senha != repSenha) {
            setErro("As senhas precisam ser iguais");
            return;
        }

        updateData('cadastro', 'senha', senha);

        try {
            cadUsuario(getData('cadastro'));
            router.push("/login");
        } catch(error) {
            console.error(error);
            setErro("Erro aou realizar cadastro");
        }
    }

    return (
        <main className="flex flex-col h-screen bg-[#598C58]">
            <header className="flex justify-between p-4 text-white">
                <Link href="#" className="font-bold text-xl">Voltar</Link>
                <h2 className="font-semibold text-xl">Cadastro</h2>
            </header>
            <section className="flex flex-col p-8 justify-between flex-1 bg-gray-300 rounded-t-4xl fixed bottom-0 w-screen">
                <div className="flex flex-row justify-between">
                    <h2 className="text-5xl font-semibold">
                        <span className="text-primary">Criar</span> <br /> conta!
                    </h2>
                    <strong>2/2</strong>
                </div>
                <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
                    <Label htmlFor="nome" className="font-semibold">
                        Email
                        <Input id="email" name="email" placeholder="example@email.com" onChange={handleEmail}/>
                    </Label>
                    <Label htmlFor="senha" className="font-semibold">
                        Senha
                        <Input id="senha" name="pass" type="password" placeholder="#Soldadinho123" onChange={handleSenha}/>
                    </Label>
                    <Label htmlFor="repsenha" className="font-semibold">
                        Confirme sua senha
                        <Input id="repsenha" name="pass" type="password" placeholder="#Soldadinho123" onChange={handleRepSenha}/>
                    </Label>
                    <button className="bg-primary p-4 rounded-md text-white font-semibold">
                        Finalizar
                    </button>
                    <span className="font-semibold">
                        Já tem conta? <Link href="#" className="text-primary">Realizar Login!</Link>
                    </span>
                    <span className="text-red-200">
                        {erro}
                    </span>
                </form>
            </section>
        </main>
    )
}