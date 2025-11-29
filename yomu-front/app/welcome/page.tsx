'use client'
import { Button } from "@/components/ui/button"
import { useRouter } from "next/navigation";

export default function Welcome() {
    const router = useRouter();
    return (
        <main className="flex flex-col">
            <header className="flex justify-start p-4 fixed w-screen top-0">
                <strong className="text-xl text-primary">Yomu</strong>
            </header>
            <section className="h-screen flex flex-col justify-center p-8 gap-2">
                <img src="/welcome.png" alt="welcome" />
                <h2 className="font-bold text-4xl text-center">
                    Quem lê <span className="text-primary">constrói</span> o futuro e <span className="text-primary">inspira</span> os amigos no presente!
                </h2>
                <span className="text-center text-xl">
                    Venha fazer parte!
                </span>
            </section>
            <section className="flex flex-col gap-4 p-8 fixed bottom-0 w-screen">
                <Button variant={"secondary"}
                    onClick={()=>{
                        router.push("/login");
                    }}
                >
                    Entrar
                </Button>
                <Button
                    onClick={()=>{
                        router.push("/cadastro/usuario");
                    }}
                >
                    Cadastrar-se
                </Button>
            </section>
        </main>
    )
}