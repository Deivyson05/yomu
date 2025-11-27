'use client'

import { useRef } from 'react'

export function Calendar() {
  const calendarDays = [
    { day: 'Qui', date: '12' },
    { day: 'Sex', date: '13' },
    { day: 'Sab', date: '14' },
    { day: 'Dom', date: '15' },
    { day: 'Seg', date: '16' },
    { day: 'Ter', date: '17' },
    { day: 'Qua', date: '18' },
    { day: 'Qui', date: '19' },
    { day: 'Sex', date: '20' },
    { day: 'Sab', date: '21' },
    { day: 'Dom', date: '22' },
    { day: 'Seg', date: '23' },
    { day: 'Ter', date: '24' }
  ];

  const highlightedDay = '17';

  const containerRef = useRef<HTMLDivElement>(null)

  const handleMouseDown = (e: React.MouseEvent) => {
    const container = containerRef.current
    if (!container) return

    const startX = e.pageX
    const scrollLeft = container.scrollLeft

    const handleMouseMove = (e: MouseEvent) => {
      const x = e.pageX
      const walk = (x - startX) * 1
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
    <div className="w-full mb-6">
      <h2 className="text-lg sm:text-xl font-semibold mb-3 sm:mb-4 text-black">Calendário</h2>
      
      <div 
        ref={containerRef}
        onMouseDown={handleMouseDown}
        className="flex gap-2 sm:gap-3 overflow-x-auto pb-2 cursor-pointer select-none [&::-webkit-scrollbar]:hidden [-ms-overflow-style:none] [scrollbar-width:none] px-4"
      >
        {calendarDays.map((item, index) => (
          <div 
            key={index}
            className={`flex flex-col items-center justify-center rounded-xl sm:rounded-2xl border-2 shadow-sm p-2 sm:p-3 min-w-12 sm:min-w-16 flex-shrink-0 ${
              item.date === highlightedDay 
                ? 'bg-green-800 text-white border-green-800' 
                : 'bg-white text-gray-900 border-green-800'  
            }`}
          >
            {/* dia da semana */}
            <span className={`text-xs sm:text-sm font-medium ${
              item.date === highlightedDay 
                ? 'text-white' 
                : 'text-gray-600'
            }`}>
              {item.day}
            </span>

            {/* data */}
            <span className={`text-base sm:text-lg font-bold mt-0.5 sm:mt-1 ${
              item.date === highlightedDay 
                ? 'text-white' 
                : 'text-gray-900' 
            }`}>
              {item.date}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}