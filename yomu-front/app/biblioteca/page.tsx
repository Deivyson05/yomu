// app/biblioteca/page.tsx
'use client'
import { useState } from 'react';
import { MobileNavBar } from '@/components/navBar/mobile';
import { BookList } from '@/components/biblioteca/book-list';
import { AddBookButton } from '@/components/biblioteca/add-book-button';
import { AddBookModal,  BookFormData} from '@/components/biblioteca/add-book-modal';
import { getUserLivros, postLivro } from '@/api/livros';
import { Loading } from '@/components/loading';
import useSWR, {mutate} from 'swr';
import { useRouter } from 'next/navigation';

export default function BibliotecaPage() {
    const { data: books, error, isLoading } = useSWR("user-livros", getUserLivros)

    const [isModalOpen, setIsModalOpen] = useState(false);

    const router = useRouter();

    const handleAddBook = async (bookData: BookFormData) => {
        try {
            await postLivro(bookData);
            mutate("user-livros");
        } catch (error) {
            alert("Erro ao cadastrar livro");
        }
        
        setIsModalOpen(false);
    };

    return (
        <div className="min-h-screen bg-white">
            <header className="bg-white border-b-2 p-6 sticky top-0 z-30">
                <h1 className="text-3xl font-bold text-gray-800">Sua Estante</h1>
            </header>

             {
                isLoading == false? (
                    <BookList books={books} />
                ): (
                    <div className='w-full h-180 flex items-center justify-center'>
                        <Loading />
                    </div>
                )
            }

            {
                error? (
                    <div className='w-full h-screen flex items-center justify-center'>
                        <h1 className='text-2xl font-bold text-gray-800'>Erro ao carregar livros</h1>
                    </div>
                ):(null)
            }

            
            <AddBookButton onClick={() => setIsModalOpen(true)} />

            <AddBookModal 
                isOpen={isModalOpen} 
                onClose={() => setIsModalOpen(false)}
                onSubmit={handleAddBook}
            />

            <div className="md:hidden">
                <MobileNavBar />
            </div>
        </div>
    );
}

// ============================================