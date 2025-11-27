// app/biblioteca/page.tsx
'use client'
import { useState, useEffect } from 'react';
import { MobileNavBar } from '@/components/navBar/mobile';
import { BookList } from '@/components/biblioteca/book-list';
import { AddBookButton } from '@/components/biblioteca/add-book-button';
import { AddBookModal } from '@/components/biblioteca/add-book-modal';
import { getUserLivros, postLivro } from '@/api/livros';
import { BookFormData } from '../../components/biblioteca/add-book-modal';
import { Book } from '@/components/livro/book-details';

export default function BibliotecaPage() {
    const [books, setBooks] = useState([
        { id: 1, titulo: "1984", capa: "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=300&h=400&fit=crop" },
    ]);

    useEffect(() => {
        async function fetchUserLivros() {
            const books: Book[] = await getUserLivros();
            console.log(books);
            setBooks(books);
        }
        fetchUserLivros();
    }, [])

    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleDeleteBook = (bookId: number) => {
        setBooks(books.filter(book => book.id !== bookId));
    };

    const handleAddBook = async (bookData: BookFormData) => {
        try { 
            const newBook = await postLivro(bookData);
            setBooks([...books, newBook]);
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

            <BookList books={books} onDeleteBook={handleDeleteBook} />

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