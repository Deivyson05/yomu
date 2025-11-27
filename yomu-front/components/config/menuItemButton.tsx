import { useState } from 'react';
import { ChevronRight } from 'lucide-react';
import { MenuItem } from './types';

interface MenuItemButtonProps {
  item: MenuItem;
  index: number;
  isLast: boolean;
}

export function MenuItemButton({ item, index, isLast }: MenuItemButtonProps) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <button
      className={`flex w-full items-center border-none bg-white p-4 px-5 transition-all duration-300 ${
        isHovered ? 'translate-x-1 bg-gray-50' : 'translate-x-0'
      } ${!isLast ? 'border-b border-gray-50' : ''}`}
      style={{
        boxShadow: isHovered ? 'inset 4px 0 0 0 #4A5F4F' : 'none'
      }}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div 
        className={`mr-4 flex h-10 w-10 items-center justify-center rounded-lg text-white transition-all duration-300 ${
          isHovered ? 'scale-110 rotate-6' : 'scale-100'
        }`}
        style={{
          backgroundColor: item.color,
          boxShadow: isHovered ? '0 4px 12px rgba(74, 95, 79, 0.3)' : 'none'
        }}
      >
        {item.icon}
      </div>
      <div className="flex-1 text-left">
        <div className="mb-1 text-base font-semibold text-gray-800">
          {item.title}
        </div>
        <div className="text-xs text-gray-400">
          {item.subtitle}
        </div>
      </div>
      <ChevronRight 
        size={20} 
        className={`transition-all duration-300 ${
          isHovered ? 'translate-x-1 text-[#4A5F4F]' : 'text-gray-300'
        }`}
      />
    </button>
  );
}