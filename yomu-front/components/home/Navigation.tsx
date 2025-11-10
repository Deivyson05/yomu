'use client'

import { useState } from 'react'

export function Navigation() {
  const [activePage, setActivePage] = useState('home')

  return (
    <nav className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-200 py-2 sm:py-3">
      <div className="flex justify-around items-center max-w-md mx-auto px-2 sm:px-4">
        {/* icone Home */}
        <button 
          className={`flex flex-col items-center p-1 sm:p-2 cursor-pointer relative select-none ${
            activePage === 'home' 
            ? 'text-green-600' 
            : 'text-gray-600'
          }`}
          onClick={() => setActivePage('home')}
        >
          <div className={`p-1.5 sm:p-2 rounded-full transition-all duration-200 ${
            activePage === 'home' 
              ? 'bg-green-100 text-white' 
              : 'bg-white hover:bg-green-200 active:bg-green-300'
          }`}>
            <img 
              src="/icons/home.png" 
              alt="Home" 
              className="w-5 h-5 sm:w-6 sm:h-6 filter brightness-0"
            />
          </div>
          <span className="text-[10px] xs:text-xs font-medium mt-0.5 sm:mt-1">Home</span>
        </button>

        {/* icone Ranking */}
        <button 
          className={`flex flex-col items-center p-1 sm:p-2 cursor-pointer relative select-none ${
            activePage === 'livros' ? 'text-green-600' : 'text-gray-600'
          }`}
          onClick={() => setActivePage('livros')}
        >
          <div className={`p-1.5 sm:p-2 rounded-full transition-all duration-200 ${
            activePage === 'livros' 
              ? 'bg-green-100 text-white' 
              : 'bg-white hover:bg-green-200 active:bg-green-300'
          }`}>
            <img 
              src="/icons/throphy.png" 
              alt="Ranking" 
              className="w-5 h-5 sm:w-6 sm:h-6 filter brightness-0"
            />
          </div>
          <span className="text-[10px] xs:text-xs font-medium mt-0.5 sm:mt-1">Ranking</span>
        </button>

        {/* icone Sua Estante */}
        <button 
          className={`flex flex-col items-center p-1 sm:p-2 cursor-pointer select-none ${
            activePage === 'Sua Estante' 
            ? 'text-green-600' 
            : 'text-gray-600'
          }`}
          onClick={() => setActivePage('Sua Estante')}
        >
          <div className={`p-1.5 sm:p-2 rounded-full transition-all duration-200 ${
            activePage === 'Sua Estante' 
              ? 'bg-green-100 text-white' 
              : 'bg-white hover:bg-green-200 active:bg-green-300'
          }`}>
            <img 
              src="/icons/books.png" 
              alt="Sua Estante" 
              className="w-5 h-5 sm:w-6 sm:h-6 filter brightness-0"
            />
          </div>
          <span className="text-[10px] xs:text-xs font-medium mt-0.5 sm:mt-1">Sua Estante</span>
        </button>

        {/* icone Perfil */}
        <button 
          className={`flex flex-col items-center p-1 sm:p-2 cursor-pointer relative select-none ${
            activePage === 'perfil' 
            ? 'text-green-600' 
            : 'text-gray-600'
          }`}
          onClick={() => setActivePage('perfil')}
        >
          <div className={`p-1.5 sm:p-2 rounded-full transition-all duration-200 ${
            activePage === 'perfil' 
              ? 'bg-green-100 text-white' 
              : 'bg-white hover:bg-green-200 active:bg-green-300'
          }`}>
            <img 
              src="/icons/person.png" 
              alt="Perfil" 
              className="w-5 h-5 sm:w-6 sm:h-6 filter brightness-0"
            />
          </div>
          <span className="text-[10px] xs:text-xs font-medium mt-0.5 sm:mt-1">Perfil</span>
        </button>
      </div>
    </nav>
  )
}