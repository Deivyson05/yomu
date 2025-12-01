'use client'
import { useState, useEffect } from "react";
import { getPerfil, getQuantAmigos, getRankingUsuario, url } from "@/api/api";
import { getSessionData } from "@/core/sStorage";
import { Progress } from "@/components/ui/progress";
import { Star, BookOpen, Users, Award, Settings } from "lucide-react";
import { Statistics } from "@/components/statistics";
import useSWR from "swr";
import { Skeleton } from "@/components/ui/skeleton";
import { useRouter } from "next/navigation";

export default function Profile() {
  const { data: user, error, isLoading } = useSWR("usuario", getPerfil);
  const { data: quantAmigos, error: errorAmigos, isLoading: isLoadingAmigos } = useSWR("quantAmigos", getQuantAmigos);
  const { data: rankingUser, error: errorRanking, isLoading: isLoadingRanking } = useSWR("rankingUser", getRankingUsuario);

  const badges = [
    {
      id: 1,
      img: "/images/badge1.png",
      nome: "Livros lidos",
    },
    {
      id: 2,
      img: "/images/badge2.png",
      nome: "Amigos",
    },
    {
      id: 3,
      img: "/images/badge3.png",
      nome: "Ranking",
    }
  ];
  const router = useRouter();
  return (
    <main className="min-h-screen bg-[#324C39] flex flex-col items-center text-white p-4">
      <header className="w-full flex justify-end pb-20">
        <Settings onClick={() => router.push("/config")}/>
      </header>
      {/* Card principal */}
      <section className="bg-[#F5F7F4] text-[#324C39] w-full max-w-sm rounded-3xl shadow-md p-6 mt-4">
        {/* Cabeçalho com avatar */}
        <div className="flex flex-col items-center -mt-20">
          {
            isLoading ? (
              <>
                <Skeleton className="w-24 h-24 rounded-full object-cover border-4 border-[#324C39] shadow-md" />

                <Skeleton className="mt-3 h-4 w-20" />
              </>
            ) : (
              <>
                <img
                  src={user.fotoPerfil}
                  alt="Foto de perfil"
                  className="w-24 h-24 rounded-full object-cover border-4 border-[#324C39] bg-[#324C39] shadow-md"
                />

                <h2 className="mt-3 text-lg font-semibold">{user.nomeUsuario}</h2>
              </>
            )
          }

        </div>

        {/* Barra de progresso */}
        {
          isLoading ? (
            <div className="mt-4">
              <Progress value={0} />
              <Skeleton className="mt-3 h-4 w-20" />
            </div>
          ) : (
            <div className="mt-4">
              <Progress value={(user.xpTotal / 200) * 100} />
              <p className="text-center text-sm mt-1">
                {user.xpTotal} / 200 XP
              </p>
            </div>
          )
        }

        {/* Estatísticas */}

        {
          isLoading || isLoadingAmigos || isLoadingRanking ? (
            <div className="mt-4">
              <Skeleton className="mt-3 h-4 w-20" />
            </div>
          ) : (
            <Statistics
              rank={rankingUser}
              level={user.nivelAtual}
              seguidores={quantAmigos}
            />
          )
        }

        {/* Badges */}
        <div className="mt-6">
          <h3 className="font-semibold mb-2">Badges</h3>
          <div className="grid grid-cols-4 gap-3 justify-items-center">
            {badges.map((badge) => (
              <img
                key={badge.id}
                src={badge.img}
                alt={badge.nome}
                className="w-12 h-12 rounded-full object-cover border border-gray-300 shadow-sm hover:scale-110 hover:shadow-lg transition-transform duration-200"
              />
            ))}
          </div>
        </div>
      </section>

      {/* Barra inferior */}
      <footer className="fixed bottom-0 bg-[#324C39] w-full max-w-sm flex justify-around py-3 text-white rounded-t-2xl">
        <BookOpen size={22} />
        <Award size={22} />
        <Star size={22} />
        <Users size={22} />
      </footer>
    </main>
  );
}