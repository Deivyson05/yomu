// components/biblioteca/book-list.tsx
import { Trash2 } from "lucide-react";
import { useState, useEffect, useRef } from "react";

interface Book {
    id: number;
    title: string;
    cover: string;
}

interface BookCardProps {
    book: Book;
    onDelete: (id: number) => void;
}

interface BookListProps {
    books: Book[];
    onDeleteBook: (id: number) => void;
}

function BookCard({ book, onDelete }: BookCardProps) {
    return (
        <div className="relative group">
            <div className="flex flex-col items-center gap-2">
                <p className="text-gray-800 text-sm font-semibold text-center px-1 min-h-[40px] flex items-center w-28 leading-tight">
                    {book.title}
                </p>
                
                <div className="relative bg-gray-200 rounded-lg overflow-hidden shadow-md group-hover:shadow-xl transition-all duration-300 group-active:scale-95 md:group-hover:-translate-y-1 w-28 h-40">
                    <img 
                        src={book.cover} 
                        alt={book.title}
                        className="w-full h-full object-cover"
                    />
                </div>
            </div>
            
            <button
                onClick={() => onDelete(book.id)}
                className="absolute top-0 right-0 bg-red-500 text-white p-1.5 rounded-full opacity-0 group-hover:opacity-100 transition-all duration-300 hover:bg-red-600 z-10 active:scale-90"
                aria-label="Excluir livro"
            >
                <Trash2 size={16} />
            </button>
        </div>
    );
}

export function BookList({ books, onDeleteBook }: BookListProps) {
    const [shelves, setShelves] = useState<Book[][]>([]);
    const containerRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const calculateShelves = () => {
            if (!containerRef.current) return;

            const containerWidth = containerRef.current.offsetWidth;
            const bookWidth = 112 + 16;
            const booksPerRow = Math.floor(containerWidth / bookWidth);

            const newShelves: Book[][] = [];
            for (let i = 0; i < books.length; i += booksPerRow) {
                newShelves.push(books.slice(i, i + booksPerRow));
            }
            setShelves(newShelves);
        };

        calculateShelves();
        window.addEventListener('resize', calculateShelves);
        return () => window.removeEventListener('resize', calculateShelves);
    }, [books]);

    return (
        <div ref={containerRef} className="p-4 pb-32 space-y-8">
            {shelves.map((shelf, shelfIndex) => (
                <div key={shelfIndex} className="relative">
                    <div className="flex gap-4 items-end pb-4">
                        {shelf.map((book) => (
                            <BookCard key={book.id} book={book} onDelete={onDeleteBook} />
                        ))}
                    </div>
                    
                    <div className="w-full h-3 bg-gradient-to-b from-amber-700 to-amber-800 rounded-sm shadow-md"></div>
                    <div className="w-full h-1 bg-amber-900 rounded-sm"></div>
                </div>
            ))}
        </div>
    );
}
