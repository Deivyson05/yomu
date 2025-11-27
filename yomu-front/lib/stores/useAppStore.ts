import { create } from 'zustand'

interface AppState {
  // Data selecionada no calendário
  selectedDate: Date
  
  // Nome do usuário (pode vir de autenticação depois)
  userName: string
  
  // Controle de modais/overlays
  isSearchOpen: boolean
  isProfileOpen: boolean
  
  // Actions
  setSelectedDate: (date: Date) => void
  setUserName: (name: string) => void
  toggleSearch: () => void
  toggleProfile: () => void
  closeAllModals: () => void
  
  // Helper functions
  getFormattedDate: () => string
  isToday: (date: Date) => boolean
}

export const useAppStore = create<AppState>((set, get) => ({
  // Estado inicial
  selectedDate: new Date(),
  userName: 'Kel',
  isSearchOpen: false,
  isProfileOpen: false,
  
  // Setters
  setSelectedDate: (date) => set({ selectedDate: date }),
  setUserName: (name) => set({ userName: name }),
  
  // Toggle modals
  toggleSearch: () => set((state) => ({ 
    isSearchOpen: !state.isSearchOpen,
    isProfileOpen: false // fecha o outro
  })),
  
  toggleProfile: () => set((state) => ({ 
    isProfileOpen: !state.isProfileOpen,
    isSearchOpen: false // fecha o outro
  })),
  
  closeAllModals: () => set({ 
    isSearchOpen: false, 
    isProfileOpen: false 
  }),
  
  // Helpers
  getFormattedDate: () => {
    const date = get().selectedDate
    return date.toLocaleDateString('pt-BR', { 
      weekday: 'long', 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric' 
    })
  },
  
  isToday: (date) => {
    const today = new Date()
    return date.toDateString() === today.toDateString()
  }
}))