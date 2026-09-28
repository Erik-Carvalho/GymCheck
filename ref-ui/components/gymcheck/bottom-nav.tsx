"use client"

import Link from "next/link"
import { usePathname } from "next/navigation"
import { Home, Dumbbell, Salad, Ruler, type LucideIcon } from "lucide-react"

type NavItem = {
  label: string
  icon: LucideIcon
  href: string
}

const items: NavItem[] = [
  { label: "Início", icon: Home, href: "/" },
  { label: "Treinos", icon: Dumbbell, href: "/treinos" },
  { label: "Dieta", icon: Salad, href: "/treinos" },
  { label: "Medidas", icon: Ruler, href: "/treinos" },
]

export function BottomNav() {
  const pathname = usePathname()

  return (
    <nav
      aria-label="Navegação Inferior"
      className="fixed inset-x-0 bottom-0 z-50 mx-auto max-w-md border-t border-white/5 bg-slate-900/80 px-2 pb-[env(safe-area-inset-bottom)] backdrop-blur-xl"
    >
      <ul className="flex items-center justify-around">
        {items.map((item) => {
          const isActive = item.href === "/" ? pathname === "/" : pathname.startsWith(item.href)
          return (
            <li key={item.label} className="flex-1">
              <Link
                href={item.href}
                aria-current={isActive ? "page" : undefined}
                className="flex w-full flex-col items-center gap-1 py-2.5"
              >
                <span
                  className={`flex h-9 w-14 items-center justify-center rounded-full transition-colors ${
                    isActive ? "bg-indigo-500/15 text-indigo-400" : "text-slate-500"
                  }`}
                >
                  <item.icon className="h-5 w-5" />
                </span>
                <span
                  className={`text-[11px] font-medium transition-colors ${
                    isActive ? "text-indigo-400" : "text-slate-500"
                  }`}
                >
                  {item.label}
                </span>
              </Link>
            </li>
          )
        })}
      </ul>
    </nav>
  )
}
