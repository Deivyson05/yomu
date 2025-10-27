import { House, User, LibraryBig, Medal } from "lucide-react"
import { Link } from "lucide-react";

export function MobileNavBar() {
    const style = {
        nav: `
            flex justify-between p-8
            fixed bottom-0 w-screen
            bg-background border-t-2
        `,
        iconSize: 32 
    }
    return (
        <nav className={style.nav}>
            <a href="/home">
                <House size={style.iconSize}/>
            </a>
            <a href="/livros">
                <LibraryBig size={style.iconSize}/>
            </a>
            <a href="/hankeada">
                <Medal size={style.iconSize}/>
            </a>
            <a href="perfil">
                <User size={style.iconSize}/>
            </a>
        </nav>
    )
}