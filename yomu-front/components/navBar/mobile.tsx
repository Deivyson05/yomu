import { useState, useEffect } from 'react';
import { House, User, LibraryBig, Medal } from "lucide-react";

interface NavItem {
  href: string;
  icon: React.ComponentType<{ size?: number; className?: string }>;
  label: string;
}

export function MobileNavBar() {
  const [activePath, setActivePath] = useState('/home');
  const [hoveredPath, setHoveredPath] = useState<string | null>(null);

  useEffect(() => {
    // Detecta a rota atual
    setActivePath(window.location.pathname);
  }, []);

  const navItems: NavItem[] = [
    { href: '/home', icon: House, label: 'Início' },
    { href: '/biblioteca', icon: LibraryBig, label: 'Biblioteca' },
    { href: '/rankeada', icon: Medal, label: 'Rankeada' },
    { href: '/profile', icon: User, label: 'Perfil' }
  ];

  return (
    <nav className="fixed bottom-0 flex w-screen justify-between border-t-2 bg-white px-8 py-4">
      {navItems.map((item) => {
        const Icon = item.icon;
        const isActive = activePath === item.href;
        const isHovered = hoveredPath === item.href;

        return (
          <a
            key={item.href}
            href={item.href}
            className={`relative flex flex-col items-center gap-1 transition-all duration-300 ${
              isHovered ? '-translate-y-2' : 'translate-y-0'
            }`}
            onMouseEnter={() => setHoveredPath(item.href)}
            onMouseLeave={() => setHoveredPath(null)}
            onClick={() => setActivePath(item.href)}
          >
            <div className="relative flex items-center justify-center">
              <Icon
                size={32}
                className={`transition-all duration-300 ${
                  isActive ? 'text-blue-600' : 'text-gray-400'
                } ${isHovered ? 'scale-110' : 'scale-100'}`}
              />
              {isActive && (
                <div className="absolute -bottom-2 h-1 w-1 animate-pulse rounded-full bg-blue-600" />
              )}
            </div>
            <span
              className={`text-xs font-medium transition-all duration-300 ${
                isActive ? 'text-blue-600 opacity-100' : 'text-gray-400 opacity-0'
              } ${isHovered ? 'opacity-100' : ''}`}
            >
              {item.label}
            </span>
          </a>
        );
      })}
    </nav>
  );
}