import Link from "next/link";
import { Input } from "@/components/ui/input";
import { Label } from "@radix-ui/react-label";
import { User } from "lucide-react";

export default function CadastroUsuario() {
    return (
        <main className="flex flex-col h-screen">
            <header className="flex justify-between p-4">
                <Link href="#" className="font-bold text-xl text-blue-600">Voltar</Link>
                <h2 className="font-semibold text-xl">Cadastro</h2>
            </header>
            <section className="flex flex-col p-4 justify-between flex-1">
                <div className="flex flex-row justify-between">
                    <h2 className="text-5xl font-semibold">
                        <span className="text-primary">Personalize</span> <br /> seu perfil!
                    </h2>
                    <strong>1/2</strong>
                </div>
                <form className="flex flex-col gap-4">
                    <Label htmlFor="nome" className="font-semibold">
                        Nome Completo
                        <Input id="nome" name="nome" placeholder="Alfredo Soares da Silva" />
                    </Label>
                    <Label htmlFor="nascimento" className="font-semibold">
                        Data de Nascimento
                        <Input id="nascimento" name="nascimento" type="date" placeholder="DD/MM/AAAA" />
                    </Label>
                    <Label htmlFor="telefone" className="font-semibold">
                        Telefone
                        <Input id="telefone" name="telefone" type="tel" placeholder="(XX) X XXXX-XXXX" />
                    </Label>
                    <button className="bg-primary p-4 rounded-md text-white font-semibold">
                        Próxima Etapa
                    </button>
                    <span className="font-semibold">
                        Ainda não tem conta? <Link href="#" className="text-primary">Cadastre-se!</Link>
                    </span>
                </form>

                <img src="/cadastro.png" alt="cadastro" className="w-full"/>
            </section>
        </main>
    )
}