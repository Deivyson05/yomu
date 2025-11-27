import { useState } from 'react';
import { Camera } from 'lucide-react';

export function ProfileSection() {
  const [hoveredItem, setHoveredItem] = useState<string | null>(null);

  return (
    <div className="flex flex-col items-center border-b border-gray-100 bg-gradient-to-br from-gray-50 to-white p-8 px-5">
      <div className="relative mb-4">
        <div 
          className={`flex h-24 w-24 items-center justify-center overflow-hidden rounded-full border-4 border-white bg-gray-200 transition-all duration-300 ${
            hoveredItem === 'avatar' ? 'scale-105 shadow-xl' : 'shadow-md'
          }`}
          onMouseEnter={() => setHoveredItem('avatar')}
          onMouseLeave={() => setHoveredItem(null)}
        >
          <img 
            src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 400 400'%3E%3Ccircle cx='200' cy='200' r='200' fill='%23E8E8E8'/%3E%3C/svg%3E"
            alt="Profile"
            className="h-full w-full object-cover"
          />
        </div>
        <button
          className={`absolute bottom-0 right-0 flex h-8 w-8 items-center justify-center rounded-full border-3 border-white bg-[#4A5F4F] transition-all duration-300 ${
            hoveredItem === 'camera' ? 'scale-115 rotate-10 shadow-lg' : 'shadow-sm'
          }`}
          style={{
            boxShadow: hoveredItem === 'camera' 
              ? '0 4px 12px rgba(74, 95, 79, 0.4)' 
              : '0 2px 6px rgba(0,0,0,0.15)'
          }}
          onMouseEnter={() => setHoveredItem('camera')}
          onMouseLeave={() => setHoveredItem(null)}
        >
          <Camera size={16} className="text-white" />
        </button>
      </div>
      <h2 className="m-0 mb-1 text-lg font-semibold tracking-wide text-gray-800">
        Kethyllyn R.
      </h2>
      <p className="m-0 text-sm tracking-wide text-gray-600">
        KethyllynRaquel@g.com
      </p>
    </div>
  );
}