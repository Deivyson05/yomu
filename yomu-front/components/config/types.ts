export interface MenuItem {
  icon: React.ReactNode;
  title: string;
  subtitle: string;
  color: string;
}

export interface NavItem {
  id: string;
  icon: React.ComponentType<{ size?: number; className?: string }>;
  label: string;
}