'use client'

import Link from "next/link";
import { Input } from "@/components/ui/input";
import { Label } from "@radix-ui/react-label";
import { ChangeEventHandler, useEffect, useState } from "react";
import { ExampleCombobox } from "@/components/ui/combobox";
import { genders } from "./genders";
import { setData, updateData } from "@/core/lStorage";
import { useRouter } from "next/navigation";

export default function CadastroUsuario() {
    const [nome, setNome] = useState("");
    const [nickname, setNickname] = useState("");
    const [telefone, setTelefone] = useState("");
    const [genero, setGenero] = useState("Prefiro não dizer");

    const [preview, setPreview] = useState("https://www.northcountryjanitorial.com/wp-content/uploads/2017/01/generic-profile-icon-8.jpg");

    const router = useRouter();

    useEffect(() => {
        setData('cadastro', {
            nome: '',
            nomeUsuario: '',
            fotoPerfil: '',
            genero: '',
            email: '',
            senha: ''
        });
    }, []);

    const handleFileChange: ChangeEventHandler<HTMLInputElement> = (event) => {
        const file = event.target.files?.[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                const base64String = reader.result as string;
                setPreview(base64String);
                updateData('cadastro', 'foto', base64String);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleSelection = (value: string) => {
        setGenero(value);
        console.log(value);
    }
    const handleNickName = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNickname(event.target.value);
    }
    const handleNome = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNome(event.target.value);
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        updateData('cadastro', 'nome', nome);
        updateData('cadastro', 'nomeUsuario', nickname);
        updateData('cadastro', 'genero', genero);
        updateData('cadastro', 'fotoPerfil', preview);

        router.push('/cadastro/credenciais');
    }

    return (
        <main className="flex flex-col h-screen bg-[#598C58]">
            <header className="flex justify-between p-4 text-white">
                <Link href="#" onClick={()=> router.back()} className="font-bold text-xl">Voltar</Link>
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
                    <div className="flex flex-col items-center gap-4">
                        <img src={preview} alt="" className="w-30 h-30 bg-gray-300 rounded-full object-cover border-2 border-primary border-dashed" />
                        <Input type="file" name="foto-de-perfil" id="img" onChange={handleFileChange} placeholder="Carregar foto" required />
                    </div>
                    <Label htmlFor="nome" className="font-semibold">
                        Nome Completo
                        <Input id="nome" name="nome" placeholder="Alfredo Soares da Silva" onChange={handleNome} required />
                    </Label>
                    <Label htmlFor="nickname" className="font-semibold">
                        Apelido
                        <Input id="nickname" name="username" placeholder="alfredo_123" onChange={handleNickName} required />
                    </Label>
                    <Label className="font-semibold">
                        Sexo - Gênero
                        <ExampleCombobox items={genders} onSelect={handleSelection} />
                    </Label>
                    <button className="bg-primary p-4 rounded-md text-white font-semibold">
                        Próxima Etapa
                    </button>
                    <span className="font-semibold">
                        Já tem conta? <Link href="/login" className="text-primary">Realizar Login!</Link>
                    </span>
                </form>
            </section>
        </main>
    )
}