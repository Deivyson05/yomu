import { CardLogin } from "@/components/cardLogin"
import { Button } from "@/components/ui/button"

export default function Login() {
    return (
        <main className="flex flex-col items-center justify-end h-screen bg-gray-600">
            <section className="flex flex-col bg-background w-screen p-6 gap-2">
                <h1 className="text-4xl font-semibold text-primary">Bem vindo!</h1>
                <Button>Cadastre-se</Button>
                <Button variant="secondary">Entrar</Button>
            </section>
        </main>
    )
}