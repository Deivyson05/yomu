import { UserStar, Medal, Trophy, Crown } from "lucide-react";

interface Props {
    level: number;
    rank: number;
    seguidores: number;
}

export function Statistics({ level, rank, seguidores }: Props) {

    const styles = {
        article: "flex bg-[#324C39] text-white rounded-xl items-center",
        div: "flex flex-col flex-1 items-center h-30 p-2 justify-center",
        span: "text-xs text-center",
        separator: "bg-gray-200 w-[1px] h-10"
    }

    return (
        <article className={styles.article}>
            <div className={`${styles.div}`}>
                <Medal/>
                <span className={styles.span}>Level</span>
                <strong>{level}</strong>
            </div>
            <div className={styles.separator}></div>
            <div className={`${styles.div}`}>
                <Crown/>
                <span className={styles.span}>Rank Regional</span>
                <strong>#{rank}</strong>
            </div>
            <div className={styles.separator}></div>
            <div className={`${styles.div}`}>
                <UserStar/>
                <span className={styles.span}>Seguidores</span>
                <strong>{seguidores}</strong>
            </div>
        </article>
    )
}