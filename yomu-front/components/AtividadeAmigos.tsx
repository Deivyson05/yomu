'use client'

import { useRef } from 'react'

export function AtividadeAmigos() {
  const friends = [
    { 
      name: 'Beatriz', 
      achievement: 'Alcançou 10 horas de leitura!',
      action: 'Olhar perfil'
    },
    { 
      name: 'João', 
      achievement: 'Terminou "O Cortiço"!',
      action: 'Olhar perfil'
    }
  ]


  // drag to scroll
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
    <div className="mt-8">
      <h2 className="text-lg font-semibold mb-4 text-black">Atividade dos seus amigos</h2>
      
      <div 
        ref={containerRef}
            onMouseDown={handleMouseDown}
        className="flex gap-2 sm:gap-3 overflow-x-auto pb-2 cursor-pointer select-none [&::-webkit-scrollbar]:hidden [-ms-overflow-style:none] [scrollbar-width:none]">
            {friends.map((friend, index) => (
            <div key={index} className="bg-white rounded-2xl p-4 shadow-sm min-w-64 flex-shrink-0">
                <h3 className="font-semibold text-gray-900 text-base mb-2">
                {friend.name}
                </h3>
                
                <p className="text-gray-600 text-sm mb-3">
                {friend.achievement}
                </p>
                
                <div className="border-t border-gray-200 my-3"></div>
                
                <button className="text-green-600 font-medium text-sm hover:text-green-700 transition-colors cursor-pointer">
                {friend.action}
                </button>
            </div>
        ))}
      </div>
    </div>
  )
}