import Link from "next/link"
import { Dumbbell, Salad, Ruler, ChevronRight, type LucideIcon } from "lucide-react"

type ModuleCard = {
  title: string
  subtitle: string
  icon: LucideIcon
  gradient: string
  href: string
}

const modules: ModuleCard[] = [
  {
    title: "Treinos",
    subtitle: "Gerencie e execute suas rotinas",
    icon: Dumbbell,
    gradient: "from-indigo-500 to-blue-600",
    href: "/treinos",
  },
  {
    title: "Dieta & Nutrição",
    subtitle: "Acompanhe suas refeições diárias",
    icon: Salad,
    gradient: "from-blue-500 to-cyan-600",
    href: "/dieta",
  },
  {
    title: "Medidas Corporais",
    subtitle: "Registre seu progresso físico",
    icon: Ruler,
    gradient: "from-violet-500 to-indigo-600",
    href: "/medidas",
  },
]

export function ModulesGrid() {
  return (
    <section className="px-5 pt-6" aria-labelledby="acesso-rapido">
      <h2 id="acesso-rapido" className="mb-3 text-sm font-semibold text-slate-300">
        Acesso Rápido
      </h2>
      <div className="flex flex-col gap-3">
        {modules.map((mod) => (
          <button
            key={mod.title}
            type="button"
            className="group flex items-center gap-4 rounded-2xl bg-slate-800/50 p-4 text-left ring-1 ring-white/5 transition-colors active:bg-slate-800"
          >
            <div
              className={`flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-gradient-to-br ${mod.gradient} shadow-lg shadow-indigo-500/10`}
            >
              <mod.icon className="h-6 w-6 text-white" />
            </div>
            <div className="flex min-w-0 flex-1 flex-col">
              <span className="text-base font-semibold text-white">{mod.title}</span>
              <span className="truncate text-xs text-slate-400">{mod.subtitle}</span>
            </div>
            <ChevronRight className="h-5 w-5 shrink-0 text-slate-500 transition-transform group-active:translate-x-0.5" />
          </button>
        ))}
      </div>
    </section>
  )
}
