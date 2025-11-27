import { ArrowLeft, BookOpen, User, FileText, Hash, Calendar, CheckCircle, Pencil, Trash } from "lucide-react";
import { AddBookModal } from "../biblioteca/add-book-modal";
import { useState } from "react";
import { deleteLivroId, putLivro } from "@/api/livros";
import { useRouter } from "next/navigation";

// components/livro/book-details.tsx
interface Book {
    id: number;
    titulo: string;
    autor: string;
    numeroPaginas: number;
    numeroCapitulos: number;
    capa: string;
    descricao: string;
    tipoRegistro: string;
    finalizado: boolean;
    createdAt: string;
}

interface BookDetailsProps {
    book: Book;
    isLoaded: boolean
}

export function BookDetails({ book, isLoaded }: BookDetailsProps) {
    const router = useRouter();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const getTipoRegistroLabel = (tipo: string) => {
        const tipos: Record<string, string> = {
            'PAGINAS': 'Páginas',
            'CAPITULOS': 'Capitulos',
        };
        return tipos[tipo] || tipo;
    };

    const handleEditBook = async (data: Book) => {
        const response = await putLivro(book.id, data);
        console.log(response);
    };

    const getTipoRegistroColor = (tipo: string) => {
        const cores: Record<string, string> = {
            'PAGINAS': 'bg-blue-100 text-blue-800',
            'CAPITULOS': 'bg-purple-100 text-purple-800',
        };
        return cores[tipo] || 'bg-gray-100 text-gray-800';
    };

    return (
        <div className="bg-white min-h-screen pb-8">
            {/* Header com botão voltar */}
            <div className="sticky top-0 bg-white border-b z-20 px-4 py-4 flex items-center gap-4">
                <button
                    onClick={() => window.history.back()}
                    className="p-2 hover:bg-gray-100 rounded-full transition-colors active:scale-95"
                    aria-label="Voltar"
                >
                    <ArrowLeft size={24} />
                </button>
                <h1 className="text-xl font-bold text-gray-800 truncate">Detalhes do Livro</h1>
            </div>

            <div className="p-6 max-w-4xl mx-auto">
                {/* Capa e Informações principais */}
                <div className="flex flex-col md:flex-row gap-6 mb-8">
                    {/* Capa */}
                    <div className="flex-shrink-0 mx-auto md:mx-0">
                        <img
                            src={book.capa}
                            alt={book.titulo}
                            className="w-48 h-72 object-cover rounded-lg shadow-xl"
                        />
                    </div>

                    {/* Informações principais */}
                    <div className="flex-1 space-y-4">
                        <div>
                            <h2 className="text-3xl font-bold text-gray-900 mb-2">{book.titulo}</h2>
                            <div className="flex items-center gap-2 text-gray-600 mb-3">
                                <User size={18} />
                                <span className="text-lg">{book.autor}</span>
                            </div>
                        </div>

                        {/* Tags de status */}
                        <div className="flex flex-wrap gap-2">
                            <span className={`px-3 py-1 rounded-full text-sm font-semibold ${getTipoRegistroColor(book.tipoRegistro)}`}>
                                {getTipoRegistroLabel(book.tipoRegistro)}
                            </span>
                            {book.finalizado ? (
                                <span className="px-3 py-1 rounded-full text-sm font-semibold bg-green-100 text-green-800 flex items-center gap-1">
                                    <CheckCircle size={16} />
                                    Finalizado
                                </span>
                            ) : (
                                <span className="px-3 py-1 rounded-full text-sm font-semibold bg-yellow-100 text-yellow-800 flex items-center gap-1">
                                    <BookOpen size={16} />
                                    Em andamento
                                </span>
                            )}
                        </div>

                        {/* Grid de informações */}
                        <div className="grid grid-cols-2 gap-4 pt-4">
                            <div className="bg-gray-50 p-4 rounded-lg">
                                <div className="flex items-center gap-2 text-gray-600 mb-1">
                                    <FileText size={18} />
                                    <span className="text-sm font-medium">Páginas</span>
                                </div>
                                <p className="text-2xl font-bold text-gray-900">{book.numeroPaginas}</p>
                            </div>

                            <div className="bg-gray-50 p-4 rounded-lg">
                                <div className="flex items-center gap-2 text-gray-600 mb-1">
                                    <Hash size={18} />
                                    <span className="text-sm font-medium">Capítulos</span>
                                </div>
                                <p className="text-2xl font-bold text-gray-900">{book.numeroCapitulos}</p>
                            </div>
                        </div>

                        {/* Data de adição */}
                        <div className="flex justify-between items-center">
                            <div className="flex items-center gap-2 text-gray-500 text-sm pt-2">
                                <Calendar size={16} />
                                <span>Adicionado em {new Date(book.createdAt).toLocaleDateString('pt-BR')}</span>
                            </div>
                            <div className="flex gap-2">
                                <button className="bg-blue-400 text-white p-2 rounded-full"
                                    onClick={() => setIsModalOpen(true)}
                                >
                                    <Pencil size={24} />
                                </button>
                                <button className="bg-red-400 text-white p-2 rounded-full"
                                    onClick={async ()=> {
                                        await deleteLivroId(book.id);
                                        alert('Livro excluido com sucesso');
                                        router.push('/biblioteca');
                                    }}
                                >
                                    <Trash size={24} />
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                {
                    isLoaded ? (
                        <AddBookModal
                            isOpen={isModalOpen}
                            onClose={() => setIsModalOpen(false)}
                            onSubmit={handleEditBook}
                            book={book}
                        />
                    ): ""
                }

                {/* Descrição */}
                {book.descricao && (
                    <div className="bg-gray-50 p-6 rounded-lg">
                        <h3 className="text-xl font-bold text-gray-900 mb-3 flex items-center gap-2">
                            <BookOpen size={22} />
                            Sobre o livro
                        </h3>
                        <p className="text-gray-700 leading-relaxed whitespace-pre-line">
                            {book.descricao}
                        </p>
                    </div>
                )}
            </div>
        </div>
    );
}
