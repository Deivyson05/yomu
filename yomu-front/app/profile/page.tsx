'use client'
import { useState, useEffect } from "react";
import { getPerfil } from "@/api";
import { getSessionData } from "@/core/sStorage";
import { Progress } from "@/components/ui/progress";
import { Star, BookOpen, Users, Award, Settings } from "lucide-react";
import { Statistics } from "@/components/statistics";

export default function Profile() {
  const [perfil, setPerfil] = useState({
    nome: "Kethyllyn R.",
    xpAtual: 300,
    xpTotal: 800,
    level: 14,
    rank: 239,
    seguidores: 57,
    badges: [
      { id: 1, nome: "Badge 1", img: "/images/badge1.png" },
      { id: 2, nome: "Badge 2", img: "/images/badge2.png" },
      { id: 3, nome: "Badge 3", img: "/images/badge3.png" },
      { id: 4, nome: "Badge 4", img: "/images/badge4.png" },
    ],
    tarefas: [
      { id: 1, nome: "Leia 20 páginas", tempo: "20min", concluida: false },
    ],
  });

  useEffect(() => {
    const getUser = async () => {
      try {
        const response = await getPerfil();
        setPerfil(response);
      } catch (error) {
        console.error(error);
      }
    };
    getUser();
  }, []);

  return (
    <main className="min-h-screen bg-[#324C39] flex flex-col items-center text-white p-4">
      <header className="w-full flex justify-end pb-20">
        <Settings />
      </header>
      {/* Card principal */}
      <section className="bg-[#F5F7F4] text-[#324C39] w-full max-w-sm rounded-3xl shadow-md p-6 mt-4">
        {/* Cabeçalho com avatar */}
        <div className="flex flex-col items-center -mt-20">
          <img
            src="/images/perfil.png"
            alt="Foto de perfil"
            className="w-24 h-24 rounded-full object-cover border-4 border-[#324C39] bg-[#324C39] shadow-md"
          />
          <h2 className="mt-3 text-lg font-semibold">{perfil.nome}</h2>
        </div>

        {/* Barra de progresso */}
        <div className="mt-4">
          <Progress value={(perfil.xpAtual / perfil.xpTotal) * 100} />
          <p className="text-center text-sm mt-1">
            {perfil.xpAtual} / {perfil.xpTotal} XP
          </p>
        </div>

        {/* Estatísticas */}

        <Statistics
          rank={perfil.rank}
          level={perfil.level}
          seguidores={perfil.seguidores}
        />

        {/* Badges */}
        <div className="mt-6">
          <h3 className="font-semibold mb-2">Badges</h3>
          <div className="grid grid-cols-4 gap-3 justify-items-center">
            {perfil.badges.map((badge) => (
              <img
                key={badge.id}
                src={badge.img}
                alt={badge.nome}
                className="w-12 h-12 rounded-full object-cover border border-gray-300 shadow-sm hover:scale-110 hover:shadow-lg transition-transform duration-200"
              />
            ))}
          </div>
        </div>

        {/* Tarefas diárias */}
        <div className="mt-6">
          <h3 className="font-semibold mb-2">Tarefas Diárias</h3>
          {perfil.tarefas.map((tarefa) => (
            <div
              key={tarefa.id}
              className="bg-[#324C39] text-white rounded-lg px-3 py-2 flex justify-between items-center"
            >
              <span>{tarefa.nome}</span>
              <span className="text-sm opacity-80">{tarefa.tempo}</span>
            </div>
          ))}
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
