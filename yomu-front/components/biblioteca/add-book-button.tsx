// components/biblioteca/add-book-button.tsx
import { Plus } from "lucide-react";

interface AddBookButtonProps {
    onClick: () => void;
}

export function AddBookButton({ onClick }: AddBookButtonProps) {
    return (
        <button
            onClick={onClick}
            className="fixed bottom-32 right-6 md:bottom-32 md:right-8 bg-green-600 text-white p-4 md:p-6 rounded-full shadow-lg hover:bg-green-700 transition-all duration-300 z-30 active:scale-95 md:hover:scale-110"
            aria-label="Adicionar livro"
        >
            <Plus size={32} className="md:w-10 md:h-10 transition-transform" />
        </button>
    );
}