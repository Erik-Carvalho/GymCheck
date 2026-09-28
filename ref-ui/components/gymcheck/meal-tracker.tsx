"use client"

import type React from "react"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Plus, Trash2, UtensilsCrossed, Clock } from "lucide-react"

type Meal = {
  id: string
  name: string
  time: string
  description: string
}

const initialMeals: Meal[] = [
  {
    id: "1",
    name: "Café da Manhã",
    time: "07:30",
    description: "3 ovos mexidos, 2 fatias de pão integral, 1 banana",
  },
  {
    id: "2",
    name: "Almoço",
    time: "12:30",
    description: "200g de arroz, 150g de frango grelhado, salada verde",
  },
  {
    id: "3",
    name: "Lanche da Tarde",
    time: "16:00",
    description: "Whey protein, 30g de aveia, 1 maçã",
  },
]

const emptyForm = { name: "", time: "", description: "" }

export function MealTracker() {
  const [meals, setMeals] = useState<Meal[]>(initialMeals)
  const [form, setForm] = useState(emptyForm)

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    if (!form.name.trim()) return
    setMeals((prev) => [...prev, { id: crypto.randomUUID(), ...form }])
    setForm(emptyForm)
  }

  function handleRemove(id: string) {
    setMeals((prev) => prev.filter((m) => m.id !== id))
  }

  const sortedMeals = [...meals].sort((a, b) => a.time.localeCompare(b.time))

  return (
    <div className="min-h-screen bg-slate-950 text-white">
      <main className="relative mx-auto flex min-h-screen max-w-md flex-col bg-gradient-to-b from-slate-900 to-slate-950 pb-16">
        {/* Header Navigation */}
        <header className="sticky top-0 z-10 flex items-center gap-3 border-b border-white/5 bg-slate-900/80 px-5 py-4 backdrop-blur-md">
          <Link
            href="/"
            className="flex items-center gap-2 rounded-xl bg-slate-800/60 px-3 py-2 text-sm font-medium text-slate-200 ring-1 ring-white/5 transition-colors active:bg-slate-800"
          >
            <ArrowLeft className="h-4 w-4" />
            Voltar ao Menu Principal
          </Link>
        </header>

        {/* Page Header */}
        <section className="px-5 pt-6">
          <div className="flex items-start gap-3">
            <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-gradient-to-br from-indigo-500 to-blue-600 shadow-lg shadow-indigo-500/20">
              <UtensilsCrossed className="h-6 w-6 text-white" />
            </div>
            <div className="min-w-0">
              <h1 className="text-balance text-xl font-bold leading-tight text-white">Dieta &amp; Nutrição</h1>
              <span className="mt-1.5 inline-flex items-center rounded-full bg-indigo-500/15 px-2.5 py-0.5 text-xs font-medium text-indigo-300 ring-1 ring-inset ring-indigo-500/20">
                Acompanhe suas refeições diárias
              </span>
            </div>
          </div>
        </section>

        {/* Meal Log Form */}
        <section className="px-5 pt-6">
          <div className="rounded-2xl bg-slate-800/50 p-4 ring-1 ring-white/5">
            <h2 className="mb-4 text-sm font-semibold text-slate-300">Registrar Refeição</h2>
            <form onSubmit={handleSubmit} className="flex flex-col gap-3">
              <div className="grid grid-cols-[1fr_auto] gap-3">
                <div className="flex flex-col gap-1.5">
                  <label htmlFor="meal-name" className="text-xs font-medium text-slate-400">
                    Nome da Refeição
                  </label>
                  <input
                    id="meal-name"
                    type="text"
                    placeholder="Ex: Café da Manhã, Almoço..."
                    value={form.name}
                    onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))}
                    className="w-full rounded-xl border-0 bg-slate-900/70 px-3.5 py-2.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
                <div className="flex flex-col gap-1.5">
                  <label htmlFor="meal-time" className="text-xs font-medium text-slate-400">
                    Horário
                  </label>
                  <input
                    id="meal-time"
                    type="time"
                    value={form.time}
                    onChange={(e) => setForm((f) => ({ ...f, time: e.target.value }))}
                    className="rounded-xl border-0 bg-slate-900/70 px-3.5 py-2.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 [color-scheme:dark] focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
              </div>

              <div className="flex flex-col gap-1.5">
                <label htmlFor="meal-desc" className="text-xs font-medium text-slate-400">
                  Alimentos / Descrição
                </label>
                <textarea
                  id="meal-desc"
                  rows={3}
                  placeholder="Ex: 3 ovos, 200g de arroz..."
                  value={form.description}
                  onChange={(e) => setForm((f) => ({ ...f, description: e.target.value }))}
                  className="w-full resize-none rounded-xl border-0 bg-slate-900/70 px-3.5 py-2.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                />
              </div>

              <button
                type="submit"
                className="flex items-center justify-center gap-2 rounded-xl bg-gradient-to-r from-indigo-500 to-blue-600 px-4 py-3 text-sm font-semibold text-white shadow-lg shadow-indigo-500/20 transition-opacity active:opacity-90"
              >
                <Plus className="h-4 w-4" />
                Salvar Refeição
              </button>
            </form>
          </div>
        </section>

        {/* Daily Meal Timeline */}
        <section className="px-5 pt-6" aria-labelledby="refeicoes-do-dia">
          <div className="mb-3 flex items-center justify-between">
            <h2 id="refeicoes-do-dia" className="text-sm font-semibold text-slate-300">
              Refeições do Dia
            </h2>
            <span className="rounded-full bg-slate-800/70 px-2.5 py-0.5 text-xs font-medium text-slate-400 ring-1 ring-white/5">
              {sortedMeals.length} {sortedMeals.length === 1 ? "refeição" : "refeições"}
            </span>
          </div>

          {sortedMeals.length === 0 ? (
            <div className="flex flex-col items-center gap-2 rounded-2xl bg-slate-800/40 px-4 py-10 text-center ring-1 ring-white/5">
              <UtensilsCrossed className="h-8 w-8 text-slate-600" />
              <p className="text-sm text-slate-400">Nenhuma refeição registrada ainda.</p>
              <p className="text-xs text-slate-500">Use o formulário acima para registrar sua primeira refeição.</p>
            </div>
          ) : (
            <ol className="relative flex flex-col gap-4 border-l border-white/10 pl-6">
              {sortedMeals.map((meal) => (
                <li key={meal.id} className="relative">
                  <span
                    aria-hidden="true"
                    className="absolute -left-[27px] top-1.5 flex h-3.5 w-3.5 items-center justify-center rounded-full bg-gradient-to-br from-indigo-500 to-blue-600 ring-4 ring-slate-950"
                  />
                  <div className="rounded-2xl bg-slate-800/50 p-4 ring-1 ring-white/5">
                    <div className="flex items-start justify-between gap-3">
                      <div className="min-w-0 flex-1">
                        <div className="flex items-center gap-2">
                          <p className="truncate text-base font-semibold text-white">{meal.name}</p>
                          {meal.time && (
                            <span className="inline-flex shrink-0 items-center gap-1 rounded-full bg-indigo-500/15 px-2 py-0.5 text-xs font-medium text-indigo-300 ring-1 ring-inset ring-indigo-500/20">
                              <Clock className="h-3 w-3" />
                              {meal.time}
                            </span>
                          )}
                        </div>
                        {meal.description && (
                          <p className="mt-1.5 text-pretty text-sm leading-relaxed text-slate-400">
                            {meal.description}
                          </p>
                        )}
                      </div>
                      <button
                        type="button"
                        onClick={() => handleRemove(meal.id)}
                        aria-label={`Remover ${meal.name}`}
                        className="flex h-9 w-9 shrink-0 items-center justify-center rounded-lg bg-red-500/10 text-red-400 transition-colors active:bg-red-500/20"
                      >
                        <Trash2 className="h-4 w-4" />
                      </button>
                    </div>
                  </div>
                </li>
              ))}
            </ol>
          )}
        </section>
      </main>
    </div>
  )
}
