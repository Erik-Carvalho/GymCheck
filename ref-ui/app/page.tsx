import { Header } from "@/components/gymcheck/header"
import { SummaryCards } from "@/components/gymcheck/summary-cards"
import { ModulesGrid } from "@/components/gymcheck/modules-grid"
import { BottomNav } from "@/components/gymcheck/bottom-nav"

export default function Page() {
  return (
    <div className="min-h-screen bg-slate-950 text-white">
      <main className="relative mx-auto flex min-h-screen max-w-md flex-col bg-gradient-to-b from-slate-900 to-slate-950 pb-28">
        <Header />
        <SummaryCards />
        <ModulesGrid />
      </main>
      <BottomNav />
    </div>
  )
}
