'use client';
import { useRouter } from 'next/navigation';
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
    const router = useRouter();
    const [step, setStep] = useState(0);

    const [api, setApi] = useState<CarouselApi | null>(null)

    const onboardingData = [
    {
        text: "Descubra novos livros e compartilhe suas leituras.",
        image: "/images/tela1.png",
        color: "bg-[#598C58]"
    },
    {
        text: "Tenha seus livros sempre à mão.",
        image: "/images/tela2.png",
        color: "bg-[#8E6456]"
    },
    {
        text: "Ganhe pontos e suba no ranking dos leitores!",
        image: "/images/tela3.png",
        color: "bg-[#F7B047]"
    }
];

    const handleNext = () => {
        if (!api) return;

        if (step < onboardingData.length - 1) {
            api.scrollNext();
            setStep(step + 1); 
        } else {
            router.push('/login');
        }
    }

    const handleSkip = () => {
        router.push('/login');
    }

    return (
        <main className={`flex flex-col h-screen`}>
            <header className='text-white fixed flex justify-between top-0 z-10 w-screen p-8'>
                <strong className='text-shadow-lg text-xl'>Yomu</strong>
                <button onClick={handleSkip} className='text-shadow-lg font-bold text-xl'>Pular</button>
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
                <div className="flex justify-center gap-2 mb-4">
                    {onboardingData.map((_, i) => (
                        <div 
                            key={i} 
                            className={`h-2 rounded-full transition-all duration-300 ${i === step ? 'w-8 bg-[#2E1C15]' : 'w-2 bg-gray-300'}`} 
                        />
                    ))}
                </div>
                <p className='text-[#2E1C15] text-center text-xl font-bold'>
                    {onboardingData[step]?.text}
                </p>
                <button onClick={handleNext} className='p-2 bg-[#2E1C15] text-white rounded-full font-bold text-xl'>
                    Próximo
                </button>
            </section>
        </main>
    )
}

