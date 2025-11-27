'use client'
import { useState, useEffect } from 'react';
import { MobileNavBar } from '@/components/navBar/mobile';
import { BookDetails } from '@/components/livro/book-details';
import { ActionButtons } from '@/components/livro/action-buttons';
import { getLivroId } from '@/api/livros';
import { getData } from '@/core/lStorage';

export default function LivroPage() {
    // Dados mockados do livro (virá da API/params)
    const [book, setBook] = useState({
        id: 1,
        titulo: 'Livro 1',
        autor: 'Autor 1',
        numeroPaginas: 100,
        numeroCapitulos: 10,
        capa: 'https://via.placeholder.com/150',
        descricao: 'Descrição do livro 1',
        tipoRegistro: 'LEITURA',
        finalizado: false,
        createdAt: '2023-09-01T00:00:00.000Z'
    });
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(() => {
        async function fetchLivro() {
            const livro = await getLivroId(getData('livro'));
            setBook(livro)
            setIsLoaded(true);
        }
        fetchLivro();
    }, []);

    const handleProgressClick = () => {
        console.log('Navegar para Registrar Progresso');
        // navigate('/livro/progresso')
    };

    const handleTrilhaClick = () => {
        console.log('Navegar para Trilha');
        // navigate('/livro/trilha')
    };

    return (
        <div className="min-h-screen bg-gray-50">
            <BookDetails book={book} isLoaded={isLoaded} />
            <ActionButtons 
                onProgressClick={handleProgressClick}
                onTrilhaClick={handleTrilhaClick}
            />
            
            <div className="md:hidden">
                <MobileNavBar />
            </div>
        </div>
    );
}
