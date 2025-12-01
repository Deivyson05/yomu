"use client"

import "./styles.css";
import React, { useState } from 'react';
import { Home, Trophy, BookOpen, User, Flame, BookOpenCheck, Bookmark, Flag, HelpCircle } from 'lucide-react';

export default function Index() {
  const [hoveredIcon, setHoveredIcon] = useState(null);
  const [activeTab, setActiveTab] = useState('home');

  const trailIcons = [
    { id: 1, icon: <BookOpenCheck size={30}/>, type: 'crown' },
    { id: 2, icon: <Bookmark size={30}/>, type: 'bookmark' },
    { id: 3, icon: <BookOpenCheck size={30}/>, type: 'book' },
    { id: 4, icon: <Bookmark size={30}/>, type: 'bookmark' },
    { id: 5, icon: <HelpCircle/>, type: 'question' },
    { id: 6, icon: <BookOpenCheck size={30}/>, type: 'book' },
    { id: 7, icon: <Bookmark size={30}/>, type: 'bookmark' },
    { id: 8, icon: <BookOpenCheck size={30}/>, type: 'book' },
    { id: 9, icon: <HelpCircle size={30}/>, type: 'question' },
    { id: 10, icon: <Bookmark size={30}/>, type: 'bookmark' },
    { id: 11, icon: <BookOpenCheck size={30}/>, type: 'book' },
    { id: 12, icon: <Flag size={30}/>, type: 'flag' }
  ];

  return (
    <div className="container">
      <div className="card">
        {/* Header */}
        <div className="header">
          <div className="header-top">
            <div className="title-wrapper">
              <h1 className="title">Trilha de Leitura</h1>
            </div>
            <div className="stats">
              <div className="stat-item">
                <span className="stat-icon">🏅</span>
                <span className="stat-number">45</span>
              </div>
            </div>
          </div>
        </div>

        {/* Trail Container */}
        <div className="trail-container">
          <div className="trail-path">
            {trailIcons.map((item, index) => (
              <div key={item.id} className="trail-item">
                <button
                  className={`trail-icon ${index === 4 ? 'active' : ''} ${index >= 4 ? 'green' : ''} text-white`}
                  aria-label={`Fase ${item.id}`}
                >
                  {item.icon}
                </button>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}