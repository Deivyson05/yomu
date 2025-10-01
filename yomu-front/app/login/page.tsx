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
    const [login, setLogin] = useState(false);
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
        <main className="flex flex-col items-center justify-end h-screen bg-gray-600">
            <section className="flex flex-col bg-gray-300 w-screen p-6 gap-2">
                <h1 className="text-4xl font-semibold text-primary">Bem vindo!</h1>
                {
                    login == false ? (
                        <Button
                            onClick={() => {
                                router.push("/cadastro/usuario");
                            }}
                        >Cadastre-se</Button>
                    ) : (
                        <>
                            <Label htmlFor="email" className="font-semibold">
                                Email
                                <Input id="email" name="email" placeholder="email@exemplo.com" type="text" onChange={handleEmail} />
                            </Label>
                            <Label htmlFor="pass" className="font-semibold">
                                Email
                                <Input id="pass" name="pass" placeholder="#Soldadinho123" type="password" onChange={handleSenha} />
                            </Label>
                        </>
                    )
                }

                <Button variant={login == true ? "default" : "secondary"}
                    onClick={() => {
                        if (login) {
                            try {
                                postLogin({
                                    email,
                                    senha
                                });
                                router.push("/profile");
                            } catch (error) {
                                console.error(error);
                                setErro("Erro ao realizar login");
                            }
                        } else {
                            setLogin(true);
                        }
                    }}
                >Entrar</Button>
                <span className="text-red-500">{erro}</span>
            </section>
        </main>
    )
}