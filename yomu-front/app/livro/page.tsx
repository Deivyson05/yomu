'use client'
import { useState, useEffect, useRef } from 'react';
import { MobileNavBar } from '@/components/navBar/mobile';
import { BookDetails } from '@/components/livro/book-details';
import { ActionButtons } from '@/components/livro/action-buttons';
import { getLivroId } from '@/api/livros';
import { getData } from '@/core/lStorage';
import { Button } from '@/components/ui/button';
import {
    Dialog,
    DialogClose,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog"
import { Pause, Play, Square } from 'lucide-react';
import { useRouter } from 'next/navigation';
import { postProgresso } from '@/api/progresso';

export default function LivroPage() {
    const router = useRouter();
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

    const [seconds, setSeconds] = useState(0);
    const [minutes, setMinutes] = useState(0);
    const [isRunning, setIsRunning] = useState(false);
    const intervalRef = useRef<NodeJS.Timeout | null>(null);

    const [pagAtual, setPagAtual] = useState(0);

    const handlePagAtual = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPagAtual(parseInt(event.target.value));
    }


    // useEffect para gerenciar o timer
    useEffect(() => {
        if (isRunning) {
            intervalRef.current = setInterval(() => {
                setSeconds(prevSeconds => {
                    if (prevSeconds === 59) {
                        setMinutes(prevMinutes => prevMinutes + 1);
                        return 0;
                    }
                    return prevSeconds + 1;
                });
            }, 1000);
        } else {
            if (intervalRef.current) {
                clearInterval(intervalRef.current);
            }
        }

        // Cleanup: limpar o intervalo quando o componente desmontar
        return () => {
            if (intervalRef.current) {
                clearInterval(intervalRef.current);
            }
        };
    }, [isRunning]);

    const handleStart = () => {
        setIsRunning(true);
    };

    const handlePause = () => {
        setIsRunning(false);
    };

    const handleStop = async () => {
        setIsRunning(false);
        setSeconds(0);
        setMinutes(0);
        // Aqui você pode adicionar lógica para salvar o tempo de leitura
        const response = await postProgresso(book.id, {
            quantidade: pagAtual,
            tipoProgresso: book.tipoRegistro,
            xpGerado: (minutes * 10 + Math.trunc(seconds / 10))
        })
        console.log('Tempo de leitura:', minutes, 'minutos e', seconds, 'segundos');
    };

    const formatTime = (value: number) => {
        return value < 10 ? `0${value}` : value;
    };

    return (
        <div className="min-h-screen bg-gray-50 p-6">
            <BookDetails book={book} isLoaded={isLoaded} />

            <Dialog>
                <DialogTrigger asChild>
                    <Button className='p-6 w-full'>Iniciar Leitura</Button>
                </DialogTrigger>
                <DialogContent>
                    <DialogHeader>
                        <h2 className="text-xl font-semibold">Iniciar Leitura</h2>
                    </DialogHeader>

                    <div className='flex w-full justify-center py-8'>
                        <strong className='text-5xl font-mono'>
                            {formatTime(minutes)}:{formatTime(seconds)}
                        </strong>
                    </div>

                    <DialogDescription className='flex w-full justify-center gap-4'>
                        <button
                            className='bg-blue-500 hover:bg-blue-600 p-4 rounded-full text-white transition-colors'
                            onClick={isRunning ? handlePause : handleStart}
                        >
                            {isRunning ? <Pause size={24} /> : <Play size={24} />}
                        </button>
                        <Dialog>
                            <DialogTrigger asChild>
                                <button
                                    className='bg-red-500 hover:bg-red-600 p-4 rounded-full text-white transition-colors'
                                    // onClick={handleStop}
                                >
                                    <Square size={24} />
                                </button>
                            </DialogTrigger>
                            <DialogContent>
                                <DialogHeader>
                                    <DialogTitle>Parar leitura?</DialogTitle>
                                </DialogHeader>
                                <DialogFooter>
                                    <DialogClose asChild>
                                        <Button>Cancelar</Button>
                                    </DialogClose>
                                    <DialogClose asChild>
                                        <Button className="bg-red-500 hover:bg-red-600"
                                            onClick={() => {
                                                handleStop();
                                                //reccarregar página
                                            }}
                                        >Parar leitura</Button>
                                    </DialogClose>
                                    <label htmlFor="stopInput">
                                        Página atual
                                        <input type="number" id='stopInput' className='w-full p-2 rounded-lg bg-gray-100 text-gray-700 border-2 border-gray-400' onChange={handlePagAtual} />
                                    </label>
                                </DialogFooter>
                            </DialogContent>
                        </Dialog>
                    </DialogDescription>
                </DialogContent>
            </Dialog>


            <ActionButtons
                onProgressClick={handleProgressClick}
                onTrilhaClick={handleTrilhaClick}
            />

        </div>
    );
}