'use client'

import { useState } from 'react';
import { User, Lock, Bell, Globe, HelpCircle, Home, Trophy, BookOpen } from 'lucide-react';
import { Header } from '@/components/config/header';
import { ProfileSection } from '@/components/config/profileSection';
import { MenuItemButton } from '@/components/config/menuItemButton';
import { LogoutButton } from '@/components/config/logoutButton';
import { MenuItem, NavItem } from '@/components/config/types';

export default function ConfigScreen() {
  const [activeNav, setActiveNav] = useState('profile');

  const menuItems: MenuItem[] = [
    {
      icon: <User size={24} />,
      title: "Conta",
      subtitle: "Segurança, trocar número e etc.",
      color: "#4A5F4F"
    },
    {
      icon: <Lock size={24} />,
      title: "Privacidade",
      subtitle: "Segurança, trocar número e etc.",
      color: "#4A5F4F"
    },
    {
      icon: <Bell size={24} />,
      title: "Notificações",
      subtitle: "Segurança, trocar número e etc.",
      color: "#4A5F4F"
    },
    {
      icon: <Globe size={24} />,
      title: "Linguagem",
      subtitle: "Segurança, trocar número e etc.",
      color: "#4A5F4F"
    },
    {
      icon: <HelpCircle size={24} />,
      title: "Ajuda",
      subtitle: "Segurança, trocar número e etc.",
      color: "#4A5F4F"
    }
  ];

  return (
    <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-[#C4A69D] to-[#D4B5AA] font-sans">
      <div className="w-full max-w-md overflow-hidden bg-white shadow-2xl">
        <Header onBack={() => console.log('Voltar')} />
        <ProfileSection />
        
        <div className="py-2">
          {menuItems.map((item, index) => (
            <MenuItemButton 
              key={index}
              item={item}
              index={index}
              isLast={index === menuItems.length - 1}
            />
          ))}
          <LogoutButton />
        </div>
      </div>
    </div>
  );
}