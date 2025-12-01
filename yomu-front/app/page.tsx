'use client'
import Image from "next/image";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function Home() {
  const router = useRouter();
  useEffect(() => {
    setTimeout(() => {
      router.push("/onboarding");
    }, 3000);
  })
  return (
    <div className="flex items-center justify-center h-screen w-screen">
      <img src="/logo.png" alt="logo"/>
    </div>
  );
}
