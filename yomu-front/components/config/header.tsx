import { useState } from 'react';
import { ChevronLeft } from 'lucide-react';

interface HeaderProps {
  onBack: () => void;
}

export function Header({ onBack }: HeaderProps) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div className="flex items-center border-b border-gray-100 bg-gradient-to-r from-gray-50 to-white p-5">
      <button 
        className={`flex items-center rounded-full border-none bg-transparent p-2 transition-transform duration-300 ${
          isHovered ? 'scale-110' : 'scale-100'
        }`}
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
        onClick={onBack}
      >
        <ChevronLeft size={24} className="text-gray-800" />
      </button>
      <h1 className="m-0 mr-8 flex-1 text-center text-xl font-semibold tracking-wide text-gray-800">
        Configurações
      </h1>
    </div>
  );
}