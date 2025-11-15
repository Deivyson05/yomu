// app/biblioteca/page.tsx
'use client'
import { useState } from 'react';
import { MobileNavBar } from '@/components/navBar/mobile';
import { BookList } from '@/components/biblioteca/book-list';
import { AddBookButton } from '@/components/biblioteca/add-book-button';
import { AddBookModal } from '@/components/biblioteca/add-book-modal';

export default function BibliotecaPage() {
    const [books, setBooks] = useState([
        { id: 1, title: "1984", cover: "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=300&h=400&fit=crop" },
        { id: 2, title: "Harry Potter", cover: "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=400&fit=crop" },
        { id: 3, title: "O Cortiço", cover: "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=300&h=400&fit=crop" },
        { id: 4, title: "Dom Casmurro", cover: "https://images.unsplash.com/photo-1524578271613-d550eacf6090?w=300&h=400&fit=crop" },
        { id: 5, title: "Livro 5", cover: "https://images.unsplash.com/photo-1495446815901-a7297e633e8d?w=300&h=400&fit=crop" },
        { id: 6, title: "Livro 6", cover: "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=300&h=400&fit=crop" },
        { id: 7, title: "Livro 7", cover: "https://images.unsplash.com/photo-1589998059171-988d887df646?w=300&h=400&fit=crop" },
        { id: 8, title: "Livro 8", cover: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300&h=400&fit=crop" },
        { id: 9, title: "Wonder", cover: "https://images.unsplash.com/photo-1491841573634-28140fc7ced7?w=300&h=400&fit=crop" },
        { id: 10, title: "Harry Potter", cover: "https://images.unsplash.com/photo-1519682337058-a94d519337bc?w=300&h=400&fit=crop" },
        { id: 11, title: "Livro 11", cover: "https://images.unsplash.com/photo-1516979187457-637abb4f9353?w=300&h=400&fit=crop" },
        { id: 12, title: "A Arte da Guerra", cover: "https://images.unsplash.com/photo-1526243741027-444d633d7365?w=300&h=400&fit=crop" },
    ]);

    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleDeleteBook = (bookId: number) => {
        setBooks(books.filter(book => book.id !== bookId));
    };

    const handleAddBook = (bookData: any) => {
        const newBook = {
            id: books.length + 1,
            title: bookData.titulo,
            cover: bookData.capa || "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=300&h=400&fit=crop"
        };
        setBooks([...books, newBook]);
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