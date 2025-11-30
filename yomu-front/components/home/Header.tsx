'use client'

import { useAppStore } from '@/lib/stores/useAppStore'

export function Header() {
  const { userName, toggleSearch, toggleProfile } = useAppStore()

  return (
    <header className="fixed top-0 left-0 right-0 z-50 bg-white/80 backdrop-blur-md border-b border-gray-200/50 py-4 sm:py-4">
      <div className="w-full flex justify-between items-center px-4 sm:px-6 max-w-6xl mx-auto">
        <h1 className="text-xl sm:text-2xl lg:text-3xl font-bold text-gray-900">
          Olá, {userName}!
        </h1>

        <div className="flex gap-2 sm:gap-3 lg:gap-4 lg:ml-auto">
          <button 
            onClick={toggleSearch}
            className="p-1.5 sm:p-2 rounded-full bg-green-800 hover:bg-green-700 active:bg-green-600 transition-colors cursor-pointer"
            aria-label="Buscar"
          >
            <svg 
              width="18" 
              height="18" 
              className="sm:w-5 sm:h-5 lg:w-6 lg:h-6"
              viewBox="0 0 24 24" 
              fill="none" 
              stroke="currentColor" 
              strokeWidth="2"
            >
              <circle cx="11" cy="11" r="8"/>
              <path d="m21 21-4.3-4.3"/>
            </svg>
          </button>
          
          <button 
            onClick={toggleProfile}
            className="p-1.5 sm:p-2 rounded-full bg-green-800 hover:bg-green-700 active:bg-green-600 transition-colors cursor-pointer"
            aria-label="Perfil"
          >
            <svg 
              width="18" 
              height="18" 
              className="sm:w-5 sm:h-5 lg:w-6 lg:h-6"
              viewBox="0 0 24 24" 
              fill="none" 
              stroke="currentColor" 
              strokeWidth="2"
            >
              <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/>
              <circle cx="12" cy="7" r="4"/>
            </svg>
          </button>
        </div>
      </div>
    </header>
  )
}