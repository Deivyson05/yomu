'use client'
import React, { useState } from 'react'
import styles from './page.module.css'
import rankingsData from './rankings.json'
import useSWR from 'swr'
import { getRanking } from '@/api/ranking'
import { url } from '@/api/api'
import { Loading } from '@/components/loading'

// Definindo o tipo para cada usuário
interface User {
  id: number
  name: string
  avatar: string
  points: number
}

// Definindo o tipo para rankingsData
interface RankingsData {
  weekly: User[]
  monthly: User[]
}

export default function Page() {
  const [mode, setMode] = useState<'weekly' | 'monthly'>('weekly')
  const [animating, setAnimating] = useState(false)

  const data: User[] = (rankingsData as RankingsData)[mode]
  const top3 = data.slice(0, 3)


  const { data: response, error, isLoading } = useSWR("ranking", getRanking);

  return (
    <main className={`${styles.page}`}>
      {
        isLoading ? (
          <div className="w-full h-180 flex items-center justify-center">
            <Loading />
          </div>
        ) : error ? (
          <div className="w-full h-180 flex items-center justify-center">
            <h1>Erro ao carregar o ranking</h1>
          </div>
        ) : (
          <div
            className={`${styles.card} ${animating ? styles.fadeEnter : styles.fadeEnterActive}`}>

            <h2 className={styles.title}>Ranking Geral</h2>

            <div className={styles.podium}>
              <div className={styles.podiumCol}>
                <div className={styles.avatarWrap}>
                  <img
                    src={`${url}${response.ranking[1]?.foto_perfil}`}
                    alt={response.ranking[1]?.nome_usuario}
                    className={styles.avatar}
                    onError={(e: React.SyntheticEvent<HTMLImageElement, Event>) => {
                      e.currentTarget.src = "https://i.pinimg.com/736x/2f/15/f2/2f15f2e8c688b3120d3d26467b06330c.jpg"
                    }}
                  />
                </div>
                <div className={styles.name}>{response.ranking[1]?.nome_usuario}</div>
                <div className={styles.points}>{response.ranking[1]?.xp} pts</div>
                <div className={`${styles.podiumBlock} ${styles.second}`}>2</div>
              </div>

              <div className={styles.podiumCol}>
                <div className={styles.avatarWrapLarge}>
                  <img
                    src={`${url}${response.ranking[0]?.foto_perfil}`}
                    alt={response.ranking[0]?.nome_usuario}
                    className={styles.avatarLarge}
                    onError={(e: React.SyntheticEvent<HTMLImageElement, Event>) => {
                      e.currentTarget.src = "https://i.pinimg.com/736x/2f/15/f2/2f15f2e8c688b3120d3d26467b06330c.jpg"
                    }}
                  />
                </div>
                <div className={styles.name}>{response.ranking[0]?.nome_usuario}</div>
                <div className={styles.points}>{response.ranking[0]?.xp} pts</div>
                <div className={`${styles.podiumBlock} ${styles.first}`}>1</div>
              </div>

              <div className={styles.podiumCol}>
                <div className={styles.avatarWrap}>
                  <img
                    src={`${url}${response.ranking[2]?.foto_perfil}`}
                    alt={response.ranking[2]?.nome_usuarioe}
                    className={styles.avatar}
                    onError={(e: React.SyntheticEvent<HTMLImageElement, Event>) => {
                      e.currentTarget.src = "https://i.pinimg.com/736x/2f/15/f2/2f15f2e8c688b3120d3d26467b06330c.jpg"
                    }}
                  />
                </div>
                <div className={styles.name}>{response.ranking[2]?.nome_usuario}</div>
                <div className={styles.points}>{response.ranking[2]?.xp} pts</div>
                <div className={`${styles.podiumBlock} ${styles.third}`}>3</div>
              </div>
            </div>

            <div className={styles.listCard}>
              {response.ranking.map((u: any, idx: number) => (
                <div key={u.id} className={styles.listItem}>
                  <div className={styles.rankCircle}>{idx + 1}</div>
                  <img src={`${url}${u.foto_perfil}`} alt={u.name} className={styles.itemAvatar}
                    onError={(e: React.SyntheticEvent<HTMLImageElement, Event>) => {
                      e.currentTarget.src = "https://i.pinimg.com/736x/2f/15/f2/2f15f2e8c688b3120d3d26467b06330c.jpg"
                    }}
                  />
                  <div className={styles.itemInfo}>
                    <div className={styles.itemName}>{u.nome_usuario}</div>
                    <div className={styles.itemPoints}>{u.xp} pontos</div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )
      }
    </main>
  )
}