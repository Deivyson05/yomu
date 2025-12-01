'use client'

import { useState } from 'react';
import { ChevronRight, LogOut } from 'lucide-react';
import { setSessionData } from '@/core/sStorage';
import { useRouter } from 'next/navigation';

export function LogoutButton() {
  const router = useRouter();
  const [isHovered, setIsHovered] = useState(false);

  return (
    <button
      className={`flex w-full items-center border-none bg-white p-4 px-5 transition-all duration-300 ${
        isHovered ? 'translate-x-1 bg-red-50' : 'translate-x-0'
      }`}
      style={{
        boxShadow: isHovered ? 'inset 4px 0 0 0 #DC3545' : 'none'
      }}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      onClick={() => {
        setSessionData('user', null);
        router.push('/login');
      }}
    >
      <div 
        className={`mr-4 flex h-10 w-10 items-center justify-center rounded-lg bg-red-600 text-white transition-all duration-300 ${
          isHovered ? 'scale-110 -rotate-6' : 'scale-100'
        }`}
        style={{
          boxShadow: isHovered ? '0 4px 12px rgba(220, 53, 69, 0.3)' : 'none'
        }}
      >
        <LogOut size={24} />
      </div>
      <div className="flex-1 text-left">
        <div className="mb-1 text-base font-semibold text-red-600">
          Sair da conta
        </div>
        <div className="text-xs text-gray-400">
          Segurança, trocar número e etc.
        </div>
      </div>
      <ChevronRight 
        size={20} 
        className={`text-red-600 transition-all duration-300 ${
          isHovered ? 'translate-x-1' : ''
        }`}
      />
    </button>
  );
}