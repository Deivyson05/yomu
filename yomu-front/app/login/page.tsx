'use client'

import { CardLogin } from "@/components/cardLogin"
import { Button } from "@/components/ui/button"
import { useRouter } from "next/navigation";
import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Label } from "@radix-ui/react-label";
import { postLogin } from "@/api";

export default function Login() {
    const router = useRouter();
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [erro, setErro] = useState("");

    const handleEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(event.target.value);
    }
    const handleSenha = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSenha(event.target.value);
    }

    return (
        <main className="flex flex-col items-center justify-end h-screen bg-[#598C58]">
            <header className="flex justify-start w-screen fixed top-0 p-4">
                <strong className="text-xl text-white">Nome do app</strong>
            </header>
            <section className="flex flex-col bg-gray-300 w-screen p-8 gap-4 rounded-t-4xl">
                <h1 className="text-4xl font-semibold">
                    <span className="text-primary">Iniciar</span> <br/> Sessão!
                </h1>
                <Label htmlFor="email" className="font-semibold text-primary">
                    Email
                    <Input id="email" name="email" placeholder="email@exemplo.com" type="text" onChange={handleEmail} />
                </Label>
                <Label htmlFor="pass" className="font-semibold text-primary">
                    <div className="flex justify-between">
                        Senha

                        <a href="#">Esqueceu a senha?</a>
                    </div>
                    <Input id="pass" name="pass" placeholder="#Soldadinho123" type="password" onChange={handleSenha} />
                </Label>

                <Button variant={"default"}
                    onClick={() => {
                        try {
                            postLogin({
                                email,
                                senha
                            });
                        } catch (error) {
                            console.error(error);
                            setErro("Erro ao realizar login");
                        } finally {
                            router.push("/profile");
                        }
                    }}
                >Entrar</Button>
                <span className="text-red-500">{erro}</span>
                <div className="font-semibold">
                    Ainda não tem conta? <a href="/cadastro/usuario" className="text-primary">Cadastre-se!</a>
                </div>
                <img src="/login.png" alt="login" />
            </section>
        </main>
    )
}