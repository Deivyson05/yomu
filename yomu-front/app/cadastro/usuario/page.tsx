'use client'

import Link from "next/link";
import { Input } from "@/components/ui/input";
import { Label } from "@radix-ui/react-label";
import { ChangeEventHandler, useState } from "react";
import { ExampleCombobox } from "@/components/ui/combobox";
import { genders } from "./genders";
import { setData, updateData } from "@/core/lStorage";
import { useRouter } from "next/navigation";

export default function CadastroUsuario() {
    const [nome, setNome] = useState("");
    const [nascimento, setNascimento] = useState("");
    const [telefone, setTelefone] = useState("");
    const [genero, setGenero] = useState("");

    const router = useRouter();

    setData('cadastro', {
        nome: '',
        nascimento: '',
        telefone: '',
        genero: '',
        email: '',
        senha: ''
    });

    const handleSelection = (value: string) => {
        setGenero(value);
        console.log(value);
    }
    const handleNascimento = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNascimento(event.target.value);
    }
    const handleNome = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNome(event.target.value);
    }
    const handleTelefone = (event: React.ChangeEvent<HTMLInputElement>) => {
        setTelefone(event.target.value);
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        
        updateData('cadastro', 'nome', nome);
        updateData('cadastro', 'nascimento', nascimento);
        updateData('cadastro', 'telefone', telefone);
        updateData('cadastro', 'genero', genero);

        router.push('/cadastro/credenciais');
    }

    return (
        <main className="flex flex-col h-screen bg-[#598C58]">
            <header className="flex justify-between p-4 text-white">
                <Link href="#" className="font-bold text-xl">Voltar</Link>
                <h2 className="font-semibold text-xl">Cadastro</h2>
            </header>
            <section className="flex flex-col p-8 gap-8 flex-1 bg-gray-300 rounded-t-4xl fixed bottom-0 w-screen">
                <div className="flex flex-row justify-between">
                    <h2 className="text-5xl font-semibold">
                        <span className="text-primary">Personalize</span> <br /> seu perfil!
                    </h2>
                    <strong>1/2</strong>
                </div>
                <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
                    <Label htmlFor="nome" className="font-semibold">
                        Nome Completo
                        <Input id="nome" name="nome" placeholder="Alfredo Soares da Silva" onChange={handleNome}/>
                    </Label>
                    <Label htmlFor="nascimento" className="font-semibold">
                        Data de Nascimento
                        <Input id="nascimento" name="nascimento" type="date" placeholder="DD/MM/AAAA" onChange={handleNascimento}/>
                    </Label>
                    <Label htmlFor="telefone" className="font-semibold">
                        Telefone
                        <Input id="telefone" name="telefone" type="tel" placeholder="(XX) X XXXX-XXXX" onChange={handleTelefone}/>
                    </Label>
                    <Label className="font-semibold">
                        Sexo - Gênero
                        <ExampleCombobox items={genders} onSelect={handleSelection}/>
                    </Label>
                    <button className="bg-primary p-4 rounded-md text-white font-semibold">
                        Próxima Etapa
                    </button>
                    <span className="font-semibold">
                        Ainda não tem conta? <Link href="#" className="text-primary">Cadastre-se!</Link>
                    </span>
                </form>
            </section>
        </main>
    )
}