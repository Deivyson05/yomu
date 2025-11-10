import { Header } from '@/components/home/Header'
import { Calendar } from '@/components/home/Calendar'
import { Progresso } from '@/components/home/Progresso'
import { AtividadeAmigos } from '@/components/home/AtividadeAmigos'
import { SeusLivros } from '@/components/home/SeusLivros'
import { Navigation } from '@/components/home/Navigation'

export default function Home() {
  return (
    <main className="min-h-screen bg-gray-50 pb-24">
      {/* Header fixo */}
      <Header />
      
      <div className="pt-24 px-4 pb-8">
        {/* Container centralizado */}
        <div className="w-full max-w-5xl mx-auto">
          <Calendar />
          <Progresso />
          <AtividadeAmigos />
          <SeusLivros />
        </div>
      </div>

      {/* Navegação fixa */}
      <Navigation />
    </main>
  )
}