import Link from "next/link";
import { Input } from "@/components/ui/input";
import { Label } from "@radix-ui/react-label";
import { User } from "lucide-react";

export default function Credenciais() {
    return (
        <main className="flex flex-col h-screen">
            <header className="flex justify-between p-4">
                <Link href="#" className="font-bold text-xl text-blue-600">Voltar</Link>
                <h2 className="font-semibold text-xl">Cadastro</h2>
            </header>
            <section className="flex flex-col p-4 justify-between flex-1">
                <div className="flex flex-row justify-between">
                    <h2 className="text-5xl font-semibold">
                        <span className="text-primary">Criar</span> <br /> conta!
                    </h2>
                    <strong>2/2</strong>
                </div>
                <form className="flex flex-col gap-4">
                    <Label htmlFor="nome" className="font-semibold">
                        Email
                        <Input id="email" name="email" placeholder="example@email.com" />
                    </Label>
                    <Label htmlFor="senha" className="font-semibold">
                        Senha
                        <Input id="senha" name="pass" type="password" placeholder="#Soldadinho123" />
                    </Label>
                    <Label htmlFor="repsenha" className="font-semibold">
                        Confirme sua senha
                        <Input id="repsenha" name="pass" type="password" placeholder="#Soldadinho123" />
                    </Label>
                    <button className="bg-primary p-4 rounded-md text-white font-semibold">
                        Finalizar
                    </button>
                    <span className="font-semibold">
                        Já tem conta? <Link href="#" className="text-primary">Realizar Login!</Link>
                    </span>
                </form>

                <img src="/cadastro.png" alt="cadastro" className="w-full"/>
            </section>
        </main>
    )
}