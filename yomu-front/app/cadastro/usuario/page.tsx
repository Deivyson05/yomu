import Link from "next/link";
import { Input } from "@/components/ui/input";
import { Label } from "@radix-ui/react-label";

export default function cadastroUsuario() {
    return (
        <main>
            <header className="flex justify-between p-4">
                <Link href="#" className="font-bold text-xl text-blue-600">Voltar</Link>
                <h2 className="font-semibold text-xl">Cadastro</h2>
            </header>
            <section className="flex flex-col p-4">
                <div className="flex flex-col">

                </div>
                <form className="flex flex-col">
                    <Label htmlFor="nome" className="font-semibold">
                        Nome Completo
                        <Input id="nome" name="nome"/>
                    </Label>
                    <Label htmlFor="nascimento" className="font-semibold">
                        Data de Nascimento
                        <Input id="nascimento" name="nascimento"/>
                    </Label>
                    <Label htmlFor="telefone" className="font-semibold">
                        Telefone
                        <Input id="telefone" name="telefone" type="tel"/>
                    </Label>
                </form>
                <button>
                    
                </button>
            </section>
        </main>
    )
}