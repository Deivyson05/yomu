'use client'

import { getContinuarLeitura } from '@/api/livros'
import { useRef } from 'react'
import useSWR from 'swr'

interface Prog {
  id: number;
  titulo: string;
  autor: string;
  progresso: number;
}

export function Progresso() {
  const { data: progresso, error, isLoading } = useSWR<Prog[]>('user-progresso', getContinuarLeitura);

  const containerRef = useRef<HTMLDivElement>(null)

  const handleMouseDown = (e: React.MouseEvent) => {
    const container = containerRef.current
    if (!container) return

    const startX = e.pageX
    const scrollLeft = container.scrollLeft

    const handleMouseMove = (e: MouseEvent) => {
      const x = e.pageX
      const walk = (x - startX) * 1 // velocidade do scroll
      container.scrollLeft = scrollLeft - walk
    }

    const handleMouseUp = () => {
      document.removeEventListener('mousemove', handleMouseMove)
      document.removeEventListener('mouseup', handleMouseUp)
      document.body.style.cursor = 'default'
    }

    document.addEventListener('mousemove', handleMouseMove)
    document.addEventListener('mouseup', handleMouseUp)
    document.body.style.cursor = 'grabbing'
  }

  return (
    <div className="mt-6">
      <h2 className="text-lg sm:text-xl font-semibold mb-3 sm:mb-4 text-black">Continue lendo</h2>

      {
        isLoading ? "" : (
          <div
            ref={containerRef}
            onMouseDown={handleMouseDown}
            className="flex gap-3 sm:gap-4 overflow-x-auto cursor-pointer select-none [&::-webkit-scrollbar]:hidden [-ms-overflow-style:none] [scrollbar-width:none]"
            style={{ userSelect: 'none' }}
          >
            {progresso?.map((book: any, index: number) => (
              <div
                key={index}
                className="bg-white rounded-xl sm:rounded-2xl p-3 sm:p-4 shadow-sm min-w-40 sm:min-w-48 md:min-w-56 flex-shrink-0"
              >
                <h3 className="font-medium text-gray-900 mb-2 text-sm sm:text-base">
                  {book.titulo}
                </h3>

                <div className="flex items-end justify-between">
                  <span className="text-xl sm:text-2xl font-bold text-gray-900">
                    {book.progresso}%
                  </span>

                  <div className="w-12 sm:w-16 bg-gray-200 rounded-full h-1 sm:h-1.5 mb-0.5 sm:mb-1">
                    <div
                      className="bg-green-600 h-1 sm:h-1.5 rounded-full transition-all duration-300"
                      style={{ width: `${book.progresso}%` }}
                    ></div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )
      }
    </div >
  )
}