// components/biblioteca/book-list.tsx
import { Trash2 } from "lucide-react";
import { useState, useEffect, useRef } from "react";

interface Book {
    id: number;
    titulo: string;
    capa: string;
    autor?: string;
}

interface BookCardProps {
    book: Book;
}

interface BookListProps {
    books: Book[];
}

function BookCard({ book}: BookCardProps) {
    return (
        <div className="relative group">
            <div className="flex flex-col items-center gap-2">
                <p className="text-gray-800 text-sm font-semibold text-center px-1 min-h-[40px] flex items-center w-28 leading-tight">
                    {book.titulo}
                </p>
                
                <div className="relative bg-gray-200 rounded-lg overflow-hidden shadow-md group-hover:shadow-xl transition-all duration-300 group-active:scale-95 md:group-hover:-translate-y-1 w-28 h-40">
                    <img 
                        src={book.capa} 
                        alt={book.titulo}
                        className="w-full h-full object-cover"
                    />
                </div>
            </div>
        </div>
    );
}

export function BookList({ books }: BookListProps) {
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
                            <BookCard key={book.id} book={book}/>
                        ))}
                    </div>
                    
                    <div className="w-full h-3 bg-gradient-to-b from-amber-700 to-amber-800 rounded-sm shadow-md"></div>
                    <div className="w-full h-1 bg-amber-900 rounded-sm"></div>
                </div>
            ))}
        </div>
    );
}
