import { Bell, Settings } from "lucide-react"

export function Header() {
  return (
    <header className="flex items-center justify-between px-5 pt-6 pb-2">
      <div className="flex items-center gap-3">
        <div className="relative">
          <div className="flex h-12 w-12 items-center justify-center overflow-hidden rounded-full bg-gradient-to-br from-indigo-500 to-blue-600 text-base font-bold text-white ring-2 ring-indigo-500/30">
            AT
          </div>
          <span className="absolute -bottom-0.5 -right-0.5 h-3.5 w-3.5 rounded-full border-2 border-slate-950 bg-emerald-500" />
        </div>
        <div className="flex flex-col">
          <span className="text-sm text-slate-400">Olá, Atleta!</span>
          <span className="text-lg font-bold tracking-tight text-white">GymCheck</span>
        </div>
      </div>

      <div className="flex items-center gap-2">
        <button
          type="button"
          aria-label="Notificações"
          className="relative flex h-10 w-10 items-center justify-center rounded-xl bg-slate-800/60 text-slate-300 ring-1 ring-white/5 transition-colors active:bg-slate-700"
        >
          <Bell className="h-5 w-5" />
          <span className="absolute right-2.5 top-2.5 h-2 w-2 rounded-full bg-indigo-400" />
        </button>
        <button
          type="button"
          aria-label="Configurações"
          className="flex h-10 w-10 items-center justify-center rounded-xl bg-slate-800/60 text-slate-300 ring-1 ring-white/5 transition-colors active:bg-slate-700"
        >
          <Settings className="h-5 w-5" />
        </button>
      </div>
    </header>
  )
}
