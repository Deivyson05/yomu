'use client'
import React, { useState } from 'react'
import styles from './page.module.css'
import rankingsData from './rankings.json'

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
  const rest = data.slice(3)

  const handleToggle = (newMode: 'weekly' | 'monthly') => {
    if (mode !== newMode) {
      setAnimating(true)
      setTimeout(() => {
        setMode(newMode)
        setAnimating(false)
      }, 300)
    }
  }

  return (
    <main className={`${styles.page}`}>
      <div
        className={`${styles.card} ${
          animating ? styles.fadeEnter : styles.fadeEnterActive
        }`}
      >
        <h2 className={styles.title}>Ranking Geral</h2>

        <div className={styles.toggle}>
          <button
            className={mode === 'weekly' ? styles.active : ''}
            onClick={() => handleToggle('weekly')}
          >
            Semanal
          </button>
          <button
            className={mode === 'monthly' ? styles.active : ''}
            onClick={() => handleToggle('monthly')}
          >
            Mensal
          </button>
        </div>

        <div className={styles.podium}>
          <div className={styles.podiumCol}>
            <div className={styles.avatarWrap}>
              <img
                src={top3[1]?.avatar}
                alt={top3[1]?.name}
                className={styles.avatar}
              />
            </div>
            <div className={styles.name}>{top3[1]?.name}</div>
            <div className={styles.points}>{top3[1]?.points} pts</div>
            <div className={`${styles.podiumBlock} ${styles.second}`}>2</div>
          </div>

          <div className={styles.podiumCol}>
            <div className={styles.avatarWrapLarge}>
              <img
                src={top3[0]?.avatar}
                alt={top3[0]?.name}
                className={styles.avatarLarge}
              />
            </div>
            <div className={styles.name}>{top3[0]?.name}</div>
            <div className={styles.points}>{top3[0]?.points} pts</div>
            <div className={`${styles.podiumBlock} ${styles.first}`}>1</div>
          </div>

          <div className={styles.podiumCol}>
            <div className={styles.avatarWrap}>
              <img
                src={top3[2]?.avatar}
                alt={top3[2]?.name}
                className={styles.avatar}
              />
            </div>
            <div className={styles.name}>{top3[2]?.name}</div>
            <div className={styles.points}>{top3[2]?.points} pts</div>
            <div className={`${styles.podiumBlock} ${styles.third}`}>3</div>
          </div>
        </div>

        <div className={styles.listCard}>
          {rest.map((u, idx) => (
            <div key={u.id} className={styles.listItem}>
              <div className={styles.rankCircle}>{idx + 4}</div>
              <img src={u.avatar} alt={u.name} className={styles.itemAvatar} />
              <div className={styles.itemInfo}>
                <div className={styles.itemName}>{u.name}</div>
                <div className={styles.itemPoints}>{u.points} pontos</div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </main>
  )
}