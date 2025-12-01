'use client'
import { getSessionData } from '@/core/sStorage'
import { useEffect, useState } from 'react';

export function Header() {
  const [nomeUsuario, setNomeUsuario] = useState('');
  useEffect(() => {
    setNomeUsuario(getSessionData('user').nomeUsuario);
  })

  return (
    <header className="fixed top-0 left-0 right-0 z-50 bg-white/80 backdrop-blur-md border-b border-gray-200/50 py-4 sm:py-4">
      <div className="w-full flex justify-between items-center px-4 sm:px-6 max-w-6xl mx-auto">
        <h1 className="text-xl sm:text-2xl lg:text-3xl font-bold text-gray-900">
          Olá, {nomeUsuario}!
        </h1>
      </div>
    </header>
  );
}