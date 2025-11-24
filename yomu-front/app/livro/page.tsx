'use client'
import { useState } from 'react';
import { MobileNavBar } from '@/components/navBar/mobile';
import { BookDetails } from '@/components/livro/book-details';
import { ActionButtons } from '@/components/livro/action-buttons';

export default function LivroPage() {
    // Dados mockados do livro (virá da API/params)
    const book = {
        id: 1,
        titulo: "1984",
        autor: "George Orwell",
        numeroPaginas: 416,
        numeroCapitulos: 23,
        capa: "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=400&h=600&fit=crop",
        descricao: "1984 é um romance distópico que retrata um futuro totalitário onde o governo controla todos os aspectos da vida. A história acompanha Winston Smith, um funcionário do Ministério da Verdade, que começa a questionar o regime opressor do Partido e seu líder, o Grande Irmão.",
        tipoRegistro: "LEITURA",
        finalizado: false,
        createdAt: "2024-01-15"
    };

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
            <BookDetails book={book} />
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
