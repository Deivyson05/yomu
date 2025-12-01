'use client'
import { Header } from '@/components/Header'
import { Calendar } from '@/components/Calendar'
import { Progresso } from '@/components/Progresso'
import { AtividadeAmigos } from '@/components/AtividadeAmigos'
import { SeusLivros } from '@/components/SeusLivros'
import { useEffect, useState } from 'react'
import { getUserLivros } from '@/api/livros'
import { Book } from '@/components/biblioteca/book-list'
import { BookFormData } from '@/components/biblioteca/add-book-modal'


export default function Home() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [books, setBooks] = useState<Book[]>([]);

  useEffect(() => {
    async function fetchUserLivros() {
      const books: Book[] = await getUserLivros();
      console.log(books);
      setBooks(books);
      setIsLoaded(true);
    }
    setIsLoaded(false);
    fetchUserLivros();
  }, [])


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
          <SeusLivros books={books} />
        </div>
      </div>
    </main>
  )
}