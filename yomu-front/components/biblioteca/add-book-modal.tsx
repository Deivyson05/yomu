// components/biblioteca/add-book-modal.tsx
import { postLivro } from "@/api/livros";
import { on } from "events";
import { X } from "lucide-react";
import { useState } from "react";

export interface BookFormData {
    titulo: string;
    autor: string;
    numeroPaginas: number;
    numeroCapitulos: number;
    capa: string;
    descricao: string;
    tipoRegistro: string;
    finalizado: boolean;
}

interface AddBookModalProps {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (data: BookFormData) => void;
    book?: BookFormData;
}

export function AddBookModal({ isOpen, onClose, onSubmit, book }: AddBookModalProps) {
    const [formData, setFormData] = useState<BookFormData>(book || {
        titulo: '',
        autor: '',
        numeroPaginas: 0,
        numeroCapitulos: 0,
        capa: '',
        descricao: '',
        tipoRegistro: 'PAGINA',
        finalizado: false
    });

    const [errors, setErrors] = useState<any>({});

    if (!isOpen) return null;

    const validate = () => {
        const newErrors: Partial<Record<keyof BookFormData, string>> = {};

        if (!formData.titulo.trim()) {
            newErrors.titulo = 'Título é obrigatório';
        }
        if (!formData.autor.trim()) {
            newErrors.autor = 'Autor é obrigatório';
        }
        if (formData.numeroPaginas < 0) {
            newErrors.numeroPaginas = 'Número de páginas não pode ser negativo';
        }
        if (formData.numeroCapitulos < 0) {
            newErrors.numeroCapitulos = 'Número de capítulos não pode ser negativo';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (validate()) {
            onSubmit(formData);
            onClose();
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value, type } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: type === 'number' ? Number(value) : value
        }));
    };

    const handleCheckbox = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData(prev => ({
            ...prev,
            finalizado: e.target.checked
        }));
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
            <div className="bg-white rounded-lg shadow-xl w-full max-w-2xl max-h-[90vh] overflow-y-auto">
                {/* Header */}
                <div className="flex items-center justify-between p-6 border-b sticky top-0 bg-white">
                    <h2 className="text-2xl font-bold text-gray-800">Adicionar Livro</h2>
                    <button
                        onClick={onClose}
                        className="text-gray-500 hover:text-gray-700 transition-colors"
                        aria-label="Fechar modal"
                    >
                        <X size={24} />
                    </button>
                </div>

                {/* Form */}
                <form onSubmit={handleSubmit} className="p-6 space-y-4">
                    {/* Título */}
                    <div>
                        <label htmlFor="titulo" className="block text-sm font-semibold text-gray-700 mb-1">
                            Título *
                        </label>
                        <input
                            type="text"
                            id="titulo"
                            name="titulo"
                            value={formData.titulo}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
                            placeholder="Digite o título do livro"
                        />
                        {errors.titulo && <p className="text-red-500 text-sm mt-1">{errors.titulo}</p>}
                    </div>

                    {/* Autor */}
                    <div>
                        <label htmlFor="autor" className="block text-sm font-semibold text-gray-700 mb-1">
                            Autor *
                        </label>
                        <input
                            type="text"
                            id="autor"
                            name="autor"
                            value={formData.autor}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
                            placeholder="Digite o nome do autor"
                        />
                        {errors.autor && <p className="text-red-500 text-sm mt-1">{errors.autor}</p>}
                    </div>

                    {/* Páginas e Capítulos */}
                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <label htmlFor="numeroPaginas" className="block text-sm font-semibold text-gray-700 mb-1">
                                Número de Páginas
                            </label>
                            <input
                                type="number"
                                id="numeroPaginas"
                                name="numeroPaginas"
                                value={formData.numeroPaginas}
                                onChange={handleChange}
                                min="0"
                                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
                            />
                            {errors.numeroPaginas && <p className="text-red-500 text-sm mt-1">{errors.numeroPaginas}</p>}
                        </div>

                        <div>
                            <label htmlFor="numeroCapitulos" className="block text-sm font-semibold text-gray-700 mb-1">
                                Número de Capítulos
                            </label>
                            <input
                                type="number"
                                id="numeroCapitulos"
                                name="numeroCapitulos"
                                value={formData.numeroCapitulos}
                                onChange={handleChange}
                                min="0"
                                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
                            />
                            {errors.numeroCapitulos && <p className="text-red-500 text-sm mt-1">{errors.numeroCapitulos}</p>}
                        </div>
                    </div>

                    {/* URL da Capa */}
                    <div>
                        <label htmlFor="capa" className="block text-sm font-semibold text-gray-700 mb-1">
                            URL da Capa
                        </label>
                        <input
                            type="url"
                            id="capa"
                            name="capa"
                            value={formData.capa}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
                            placeholder="https://exemplo.com/capa.jpg"
                        />
                    </div>

                    {/* Tipo de Registro */}
                    <div>
                        <label htmlFor="tipoRegistro" className="block text-sm font-semibold text-gray-700 mb-1">
                            Tipo de Registro *
                        </label>
                        <select
                            id="tipoRegistro"
                            name="tipoRegistro"
                            value={formData.tipoRegistro}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
                        >
                            <option value="PAGINA">Por páginas</option>
                            <option value="CAPITULO">Por capítulos</option>
                        </select>
                    </div>

                    {/* Descrição */}
                    <div>
                        <label htmlFor="descricao" className="block text-sm font-semibold text-gray-700 mb-1">
                            Descrição
                        </label>
                        <textarea
                            id="descricao"
                            name="descricao"
                            value={formData.descricao}
                            onChange={handleChange}
                            rows={4}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent resize-none"
                            placeholder="Adicione uma descrição ou resumo do livro..."
                        />
                    </div>


                    {/* Buttons */}
                    <div className="flex gap-4 pt-4">
                        <button
                            type="button"
                            onClick={onClose}
                            className="flex-1 px-6 py-3 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors font-semibold"
                        >
                            Cancelar
                        </button>
                        <button
                            type="submit"
                            className="flex-1 px-6 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors font-semibold"
                        >
                            Adicionar Livro
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}