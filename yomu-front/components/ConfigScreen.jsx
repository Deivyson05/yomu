import React, { useState } from 'react';
import { ChevronLeft, ChevronRight, User, Lock, Bell, Globe, HelpCircle, LogOut, Home, Trophy, BookOpen, Camera } from 'lucide-react';

export default function ConfigScreen() {
  const [activeNav, setActiveNav] = useState('profile');
  const [hoveredItem, setHoveredItem] = useState(null);
  const [hoveredNav, setHoveredNav] = useState(null);

  const menuItems = [
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

  const navItems = [
    { id: 'home', icon: Home, label: 'Início' },
    { id: 'trophy', icon: Trophy, label: 'Conquistas' },
    { id: 'books', icon: BookOpen, label: 'Biblioteca' },
    { id: 'profile', icon: User, label: 'Perfil' }
  ];

  return (
    <div style={{
      minHeight: '100vh',
      background: 'linear-gradient(135deg, #C4A69D 0%, #D4B5AA 100%)',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      padding: '20px',
      fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif'
    }}>
      <div style={{
        width: '100%',
        maxWidth: '400px',
        background: 'white',
        borderRadius: '24px',
        boxShadow: '0 20px 60px rgba(0,0,0,0.15)',
        overflow: 'hidden'
      }}>
        {/* Header */}
        <div style={{
          padding: '20px',
          display: 'flex',
          alignItems: 'center',
          borderBottom: '1px solid #f0f0f0',
          background: 'linear-gradient(to right, #fafafa, #ffffff)'
        }}>
          <button 
            style={{
              background: 'none',
              border: 'none',
              padding: '8px',
              cursor: 'pointer',
              display: 'flex',
              alignItems: 'center',
              borderRadius: '50%',
              transition: 'all 0.3s ease',
              transform: hoveredItem === 'back' ? 'scale(1.1)' : 'scale(1)'
            }}
            onMouseEnter={() => setHoveredItem('back')}
            onMouseLeave={() => setHoveredItem(null)}
          >
            <ChevronLeft size={24} color="#333" />
          </button>
          <h1 style={{
            flex: 1,
            textAlign: 'center',
            fontSize: '20px',
            fontWeight: '600',
            color: '#333',
            margin: 0,
            marginRight: '32px',
            letterSpacing: '0.3px'
          }}>
            Configurações
          </h1>
        </div>

        {/* Profile Section */}
        <div style={{
          padding: '30px 20px',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          borderBottom: '1px solid #f0f0f0',
          background: 'linear-gradient(135deg, #fafafa 0%, #ffffff 100%)'
        }}>
          <div style={{
            position: 'relative',
            marginBottom: '15px'
          }}>
            <div 
              style={{
                width: '100px',
                height: '100px',
                borderRadius: '50%',
                background: '#E8E8E8',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                overflow: 'hidden',
                border: '4px solid #fff',
                boxShadow: hoveredItem === 'avatar' 
                  ? '0 8px 24px rgba(0,0,0,0.2)' 
                  : '0 4px 12px rgba(0,0,0,0.1)',
                transition: 'all 0.3s ease',
                transform: hoveredItem === 'avatar' ? 'scale(1.05)' : 'scale(1)',
                cursor: 'pointer'
              }}
              onMouseEnter={() => setHoveredItem('avatar')}
              onMouseLeave={() => setHoveredItem(null)}
            >
              <img 
                src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 400 400'%3E%3Ccircle cx='200' cy='200' r='200' fill='%23E8E8E8'/%3E%3C/svg%3E"
                alt="Profile"
                style={{
                  width: '100%',
                  height: '100%',
                  objectFit: 'cover'
                }}
              />
            </div>
            <button
              style={{
                position: 'absolute',
                bottom: '0',
                right: '0',
                width: '32px',
                height: '32px',
                borderRadius: '50%',
                background: '#4A5F4F',
                border: '3px solid white',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                cursor: 'pointer',
                transition: 'all 0.3s ease',
                transform: hoveredItem === 'camera' ? 'scale(1.15) rotate(10deg)' : 'scale(1)',
                boxShadow: hoveredItem === 'camera' 
                  ? '0 4px 12px rgba(74, 95, 79, 0.4)' 
                  : '0 2px 6px rgba(0,0,0,0.15)'
              }}
              onMouseEnter={() => setHoveredItem('camera')}
              onMouseLeave={() => setHoveredItem(null)}
            >
              <Camera size={16} color="white" />
            </button>
          </div>
          <h2 style={{
            fontSize: '18px',
            fontWeight: '600',
            color: '#333',
            margin: 0,
            marginBottom: '5px',
            letterSpacing: '0.3px'
          }}>
            Kethyllyn R.
          </h2>
          <p style={{
            fontSize: '14px',
            color: '#666',
            margin: 0,
            letterSpacing: '0.2px'
          }}>
            KethyllynRaquel@g.com
          </p>
        </div>

        {/* Menu Items */}
        <div style={{
          padding: '10px 0'
        }}>
          {menuItems.map((item, index) => (
            <button
              key={index}
              style={{
                width: '100%',
                display: 'flex',
                alignItems: 'center',
                padding: '16px 20px',
                background: hoveredItem === `menu-${index}` ? '#f9f9f9' : 'white',
                border: 'none',
                cursor: 'pointer',
                transition: 'all 0.3s ease',
                borderBottom: index < menuItems.length - 1 ? '1px solid #f5f5f5' : 'none',
                transform: hoveredItem === `menu-${index}` ? 'translateX(5px)' : 'translateX(0)',
                boxShadow: hoveredItem === `menu-${index}` 
                  ? 'inset 4px 0 0 0 #4A5F4F' 
                  : 'none'
              }}
              onMouseEnter={() => setHoveredItem(`menu-${index}`)}
              onMouseLeave={() => setHoveredItem(null)}
            >
              <div style={{
                width: '40px',
                height: '40px',
                borderRadius: '8px',
                background: item.color,
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                marginRight: '15px',
                color: 'white',
                transition: 'all 0.3s ease',
                transform: hoveredItem === `menu-${index}` ? 'scale(1.1) rotate(5deg)' : 'scale(1)',
                boxShadow: hoveredItem === `menu-${index}` 
                  ? '0 4px 12px rgba(74, 95, 79, 0.3)' 
                  : 'none'
              }}>
                {item.icon}
              </div>
              <div style={{
                flex: 1,
                textAlign: 'left'
              }}>
                <div style={{
                  fontSize: '16px',
                  fontWeight: '600',
                  color: '#333',
                  marginBottom: '3px',
                  transition: 'color 0.3s ease'
                }}>
                  {item.title}
                </div>
                <div style={{
                  fontSize: '12px',
                  color: '#999'
                }}>
                  {item.subtitle}
                </div>
              </div>
              <ChevronRight 
                size={20} 
                color={hoveredItem === `menu-${index}` ? '#4A5F4F' : '#ccc'}
                style={{
                  transition: 'all 0.3s ease',
                  transform: hoveredItem === `menu-${index}` ? 'translateX(5px)' : 'translateX(0)'
                }}
              />
            </button>
          ))}

          {/* Logout Button */}
          <button
            style={{
              width: '100%',
              display: 'flex',
              alignItems: 'center',
              padding: '16px 20px',
              background: hoveredItem === 'logout' ? '#fff0f0' : 'white',
              border: 'none',
              cursor: 'pointer',
              transition: 'all 0.3s ease',
              transform: hoveredItem === 'logout' ? 'translateX(5px)' : 'translateX(0)',
              boxShadow: hoveredItem === 'logout' 
                ? 'inset 4px 0 0 0 #DC3545' 
                : 'none'
            }}
            onMouseEnter={() => setHoveredItem('logout')}
            onMouseLeave={() => setHoveredItem(null)}
          >
            <div style={{
              width: '40px',
              height: '40px',
              borderRadius: '8px',
              background: '#DC3545',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              marginRight: '15px',
              color: 'white',
              transition: 'all 0.3s ease',
              transform: hoveredItem === 'logout' ? 'scale(1.1) rotate(-5deg)' : 'scale(1)',
              boxShadow: hoveredItem === 'logout' 
                ? '0 4px 12px rgba(220, 53, 69, 0.3)' 
                : 'none'
            }}>
              <LogOut size={24} />
            </div>
            <div style={{
              flex: 1,
              textAlign: 'left'
            }}>
              <div style={{
                fontSize: '16px',
                fontWeight: '600',
                color: '#DC3545',
                marginBottom: '3px',
                transition: 'all 0.3s ease'
              }}>
                Sair da conta
              </div>
              <div style={{
                fontSize: '12px',
                color: '#999'
              }}>
                Segurança, trocar número e etc.
              </div>
            </div>
            <ChevronRight 
              size={20} 
              color="#DC3545"
              style={{
                transition: 'all 0.3s ease',
                transform: hoveredItem === 'logout' ? 'translateX(5px)' : 'translateX(0)'
              }}
            />
          </button>
        </div>

        {/* Bottom Navigation */}
        <div style={{
          display: 'flex',
          justifyContent: 'space-around',
          alignItems: 'center',
          padding: '15px 0',
          borderTop: '1px solid #f0f0f0',
          background: '#fafafa'
        }}>
          {navItems.map((nav) => {
            const Icon = nav.icon;
            const isActive = activeNav === nav.id;
            const isHovered = hoveredNav === nav.id;
            
            return (
              <button 
                key={nav.id}
                style={{
                  background: 'none',
                  border: 'none',
                  cursor: 'pointer',
                  padding: '10px 20px',
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center',
                  gap: '5px',
                  transition: 'all 0.3s ease',
                  transform: isHovered ? 'translateY(-3px)' : 'translateY(0)',
                  position: 'relative'
                }}
                onClick={() => setActiveNav(nav.id)}
                onMouseEnter={() => setHoveredNav(nav.id)}
                onMouseLeave={() => setHoveredNav(null)}
              >
                <div style={{
                  position: 'relative',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center'
                }}>
                  <Icon 
                    size={24} 
                    color={isActive ? '#4A5F4F' : '#999'}
                    style={{
                      transition: 'all 0.3s ease',
                      transform: isHovered ? 'scale(1.2)' : 'scale(1)'
                    }}
                  />
                  {isActive && (
                    <div style={{
                      position: 'absolute',
                      bottom: '-8px',
                      width: '4px',
                      height: '4px',
                      borderRadius: '50%',
                      background: '#4A5F4F',
                      animation: 'pulse 2s infinite'
                    }} />
                  )}
                </div>
                <span style={{
                  fontSize: '11px',
                  color: isActive ? '#4A5F4F' : '#999',
                  fontWeight: isActive ? '600' : '400',
                  transition: 'all 0.3s ease',
                  opacity: isHovered || isActive ? 1 : 0.7
                }}>
                  {nav.label}
                </span>
              </button>
            );
          })}
        </div>
      </div>
    </div>
  );
}
