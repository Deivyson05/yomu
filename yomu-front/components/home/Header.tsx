'use client'

import { useRef } from 'react'
import { useAppStore } from '@/lib/stores/useAppStore'

export function Calendar() {
  const { selectedDate, setSelectedDate } = useAppStore()
  const containerRef = useRef<HTMLDivElement>(null)

  // Gerar dias do calendário dinamicamente
  const generateCalendarDays = () => {
    const days = []
    const today = new Date()
    
    // Gerar 14 dias a partir de hoje
    for (let i = -3; i < 11; i++) {
      const date = new Date(today)
      date.setDate(today.getDate() + i)
      
      days.push({
        day: date.toLocaleDateString('pt-BR', { weekday: 'short' }),
        date: date.getDate().toString(),
        fullDate: date
      })
    }
    
    return days
  }

  const calendarDays = generateCalendarDays()

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

  // const handleDateClick = (date: Date) => {
  //   setSelectedDate(date)
  // }

  // const isSelected = (date: Date) => {
  //   return date.toDateString() === selectedDate.toDateString()
  // }

  const isToday = (date: Date) => {
    const today = new Date()
    return date.toDateString() === today.toDateString()
  }

  return (
    <div className="w-full mb-6">
      <h2 className="text-lg sm:text-xl font-semibold mb-3 sm:mb-4 text-black">
        Calendário
      </h2>
      
      <div 
        ref={containerRef}
        onMouseDown={handleMouseDown}
        className="flex gap-2 sm:gap-3 overflow-x-auto pb-2 cursor-pointer select-none [&::-webkit-scrollbar]:hidden [-ms-overflow-style:none] [scrollbar-width:none] px-4"
      >
        {calendarDays.map((item, index) => {
          const today = isToday(item.fullDate)
          
          return (
            <div 
              key={index}
              className={`flex flex-col items-center justify-center rounded-xl sm:rounded-2xl border-2 shadow-sm p-2 sm:p-3 min-w-12 sm:min-w-16 flex-shrink-0 transition-all ${
                today
                  ? 'bg-green-800 text-white border-green-800' 
                  : 'bg-white text-gray-900 border-green-800'  
              }`}
            >
              <span className={`text-xs sm:text-sm font-medium ${
                today ? 'text-white' : 'text-gray-600'
              }`}>
                {item.day}
              </span>

              <span className={`text-base sm:text-lg font-bold mt-0.5 sm:mt-1 ${
                today ? 'text-white' : 'text-gray-900' 
              }`}>
                {item.date}
              </span>
            </div>
          )
        })}
      </div>
    </div>
  )
}