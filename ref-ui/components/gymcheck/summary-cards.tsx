import { Dumbbell, Flame, Scale, type LucideIcon } from "lucide-react"

type SummaryCard = {
  label: string
  value: string
  unit: string
  icon: LucideIcon
  accent: string
  iconBg: string
}

const cards: SummaryCard[] = [
  {
    label: "Treino do Dia",
    value: "Peito",
    unit: "& Tríceps",
    icon: Dumbbell,
    accent: "text-indigo-400",
    iconBg: "bg-indigo-500/15 text-indigo-400",
  },
  {
    label: "Dieta/Calorias",
    value: "1.840",
    unit: "kcal restantes",
    icon: Flame,
    accent: "text-blue-400",
    iconBg: "bg-blue-500/15 text-blue-400",
  },
  {
    label: "Última Medida",
    value: "78,4",
    unit: "kg",
    icon: Scale,
    accent: "text-emerald-400",
    iconBg: "bg-emerald-500/15 text-emerald-400",
  },
]

export function SummaryCards() {
  return (
    <section className="px-5 pt-4" aria-labelledby="resumo-do-dia">
      <h2 id="resumo-do-dia" className="mb-3 text-sm font-semibold text-slate-300">
        Resumo do Dia
      </h2>
      <div className="grid grid-cols-3 gap-3">
        {cards.map((card) => (
          <div
            key={card.label}
            className="flex flex-col gap-2 rounded-2xl bg-slate-800/50 p-3 ring-1 ring-white/5"
          >
            <div className={`flex h-8 w-8 items-center justify-center rounded-lg ${card.iconBg}`}>
              <card.icon className="h-4 w-4" />
            </div>
            <div className="flex flex-col">
              <span className="text-[11px] leading-tight text-slate-400">{card.label}</span>
              <span className="mt-0.5 text-base font-bold leading-tight text-white">{card.value}</span>
              <span className="text-[10px] leading-tight text-slate-500">{card.unit}</span>
            </div>
          </div>
        ))}
      </div>
    </section>
  )
}
