import { Header } from '@/components/Header'
import { Calendar } from '@/components/Calendar'
import { Progresso } from '@/components/Progresso'
import { AtividadeAmigos } from '@/components/AtividadeAmigos'
import { SeusLivros } from '@/components/SeusLivros'

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
    </main>
  )
}