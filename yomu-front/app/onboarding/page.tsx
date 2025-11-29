'use client';
import { useState } from 'react';
import {
    Carousel,
    CarouselContent,
    CarouselItem,
    CarouselNext,
    CarouselPrevious,
    CarouselApi
} from "@/components/ui/carousel"



export default function Onboarding() {
    const [step, setStep] = useState(0);

    const [api, setApi] = useState<CarouselApi | null>(null)

    const onboarding = [
        "Descubra novos livros e compartilhe suas leituras.",
        "Tenha seus livros sempre à mão.",
        "Ganhe pontos e suba no ranking dos leitores!"
    ]

    const handleNext = () => {
        if (!api) {
            console.error("erro ao conectar com a api");
            return;
        }
        api.scrollNext() // avança o slide
        if (step < onboarding.length - 1) {
            setStep(step + 1)
        } else {
            window.location.href = '/login';
        }
    }

    const handleSkip = () => {
        window.location.href = '/';
    }

    return (
        <main className={`flex flex-col h-screen`}>
            <header className='text-white fixed flex justify-between top-0 z-10 w-screen p-8'>
                <strong className='text-shadow-lg text-xl'>Yomu</strong>
                <a href="/login" className='text-shadow-lg font-bold text-xl'>Pular</a>
            </header>
            <Carousel setApi={setApi} className='w-full h-full'
                opts={
                    {
                        watchDrag: false
                    }
                }
            >
                <CarouselContent>
                    <CarouselItem className="bg-[#598C58] h-screen flex items-center justify-center">
                        <img src="/images/tela1.png" alt="imagem da tela 1" />
                    </CarouselItem>
                    <CarouselItem className="bg-[#8E6456] h-screen flex items-center justify-center">
                        <img src="/images/tela2.png" alt="imagem da tela 2" />
                    </CarouselItem>
                    <CarouselItem className="bg-[#F7B047] h-screen flex items-center justify-center">
                        <img src="/images/tela3.png" alt="imagem da tela 3" />
                    </CarouselItem>
                </CarouselContent>
            </Carousel>
            <section className='bg-white fixed flex flex-col w-screen p-8 bottom-0 z-10 gap-4 rounded-t-lg'>
                <p className='text-[#2E1C15] text-center text-xl font-bold'>
                    {onboarding[step]}
                </p>
                <button onClick={handleNext} className='p-2 bg-[#2E1C15] text-white rounded-full font-bold text-xl'>
                    Próximo
                </button>
            </section>
        </main>
    )
}

