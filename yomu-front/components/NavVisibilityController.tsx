"use client";

import { usePathname } from "next/navigation";
import { MobileNavBar } from "@/components/navBar/mobile";

export function NavVisibilityController() {
  const pathname = usePathname();

  // Esconde navbar em /cadastro e /login
  const hideNav =
    pathname?.includes("cadastro") || pathname?.includes("login");

  return !hideNav ? <MobileNavBar /> : null;
}
