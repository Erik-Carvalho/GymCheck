"use client"

import type React from "react"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Plus, Trash2, Ruler, TrendingDown, TrendingUp } from "lucide-react"

type Measurement = {
  id: string
  date: string
  weight: string
  waist: string
  arm: string
}

function today() {
  return new Date().toISOString().slice(0, 10)
}

const initialMeasurements: Measurement[] = [
  { id: "1", date: "2026-09-01", weight: "82.4", waist: "88", arm: "38" },
  { id: "2", date: "2026-09-11", weight: "81.1", waist: "86.5", arm: "38.5" },
  { id: "3", date: "2026-09-21", weight: "80.2", waist: "85", arm: "39" },
]

function emptyForm() {
  return { date: today(), weight: "", waist: "", arm: "" }
}

function formatDate(iso: string) {
  const [y, m, d] = iso.split("-")
  if (!y || !m || !d) return iso
  return `${d}/${m}/${y.slice(2)}`
}

export function MeasurementTracker() {
  const [measurements, setMeasurements] = useState<Measurement[]>(initialMeasurements)
  const [form, setForm] = useState(emptyForm)

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    if (!form.date) return
    setMeasurements((prev) => [...prev, { id: crypto.randomUUID(), ...form }])
    setForm(emptyForm())
  }

  function handleRemove(id: string) {
    setMeasurements((prev) => prev.filter((m) => m.id !== id))
  }

  const sorted = [...measurements].sort((a, b) => b.date.localeCompare(a.date))

  const latest = sorted[0]
  const previous = sorted[1]
  const weightDelta =
    latest && previous && latest.weight && previous.weight
      ? Number(latest.weight) - Number(previous.weight)
      : null

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
              <Ruler className="h-6 w-6 text-white" />
            </div>
            <div className="min-w-0">
              <h1 className="text-balance text-xl font-bold leading-tight text-white">Medidas Corporais</h1>
              <span className="mt-1.5 inline-flex items-center rounded-full bg-indigo-500/15 px-2.5 py-0.5 text-xs font-medium text-indigo-300 ring-1 ring-inset ring-indigo-500/20">
                Registre seu progresso físico
              </span>
            </div>
          </div>
        </section>

        {/* New Measurement Form */}
        <section className="px-5 pt-6">
          <div className="rounded-2xl bg-slate-800/50 p-4 ring-1 ring-white/5">
            <h2 className="mb-4 text-sm font-semibold text-slate-300">Nova Medição</h2>
            <form onSubmit={handleSubmit} className="flex flex-col gap-3">
              <div className="flex flex-col gap-1.5">
                <label htmlFor="measure-date" className="text-xs font-medium text-slate-400">
                  Data
                </label>
                <input
                  id="measure-date"
                  type="date"
                  value={form.date}
                  onChange={(e) => setForm((f) => ({ ...f, date: e.target.value }))}
                  className="rounded-xl border-0 bg-slate-900/70 px-3.5 py-2.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 [color-scheme:dark] focus:outline-none focus:ring-2 focus:ring-indigo-500"
                />
              </div>

              <div className="grid grid-cols-3 gap-3">
                <div className="flex flex-col gap-1.5">
                  <label htmlFor="measure-weight" className="text-xs font-medium text-slate-400">
                    Peso (kg)
                  </label>
                  <input
                    id="measure-weight"
                    type="number"
                    inputMode="decimal"
                    step="0.1"
                    min="0"
                    placeholder="80.0"
                    value={form.weight}
                    onChange={(e) => setForm((f) => ({ ...f, weight: e.target.value }))}
                    className="w-full rounded-xl border-0 bg-slate-900/70 px-3 py-2.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
                <div className="flex flex-col gap-1.5">
                  <label htmlFor="measure-waist" className="text-xs font-medium text-slate-400">
                    Cintura (cm)
                  </label>
                  <input
                    id="measure-waist"
                    type="number"
                    inputMode="decimal"
                    step="0.1"
                    min="0"
                    placeholder="85"
                    value={form.waist}
                    onChange={(e) => setForm((f) => ({ ...f, waist: e.target.value }))}
                    className="w-full rounded-xl border-0 bg-slate-900/70 px-3 py-2.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
                <div className="flex flex-col gap-1.5">
                  <label htmlFor="measure-arm" className="text-xs font-medium text-slate-400">
                    Braço (cm)
                  </label>
                  <input
                    id="measure-arm"
                    type="number"
                    inputMode="decimal"
                    step="0.1"
                    min="0"
                    placeholder="39"
                    value={form.arm}
                    onChange={(e) => setForm((f) => ({ ...f, arm: e.target.value }))}
                    className="w-full rounded-xl border-0 bg-slate-900/70 px-3 py-2.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
              </div>

              <button
                type="submit"
                className="flex items-center justify-center gap-2 rounded-xl bg-gradient-to-r from-indigo-500 to-blue-600 px-4 py-3 text-sm font-semibold text-white shadow-lg shadow-indigo-500/20 transition-opacity active:opacity-90"
              >
                <Plus className="h-4 w-4" />
                Registrar Medida
              </button>
            </form>
          </div>
        </section>

        {/* Latest snapshot */}
        {latest && (
          <section className="px-5 pt-6">
            <div className="grid grid-cols-3 gap-3">
              <div className="rounded-2xl bg-slate-800/50 p-3 text-center ring-1 ring-white/5">
                <p className="text-xs font-medium text-slate-400">Peso</p>
                <p className="mt-1 text-lg font-bold text-white">{latest.weight || "—"}</p>
                <p className="text-[10px] text-slate-500">kg</p>
                {weightDelta !== null && weightDelta !== 0 && (
                  <span
                    className={`mt-1 inline-flex items-center gap-0.5 text-[10px] font-medium ${
                      weightDelta < 0 ? "text-emerald-400" : "text-amber-400"
                    }`}
                  >
                    {weightDelta < 0 ? (
                      <TrendingDown className="h-3 w-3" />
                    ) : (
                      <TrendingUp className="h-3 w-3" />
                    )}
                    {Math.abs(weightDelta).toFixed(1)} kg
                  </span>
                )}
              </div>
              <div className="rounded-2xl bg-slate-800/50 p-3 text-center ring-1 ring-white/5">
                <p className="text-xs font-medium text-slate-400">Cintura</p>
                <p className="mt-1 text-lg font-bold text-white">{latest.waist || "—"}</p>
                <p className="text-[10px] text-slate-500">cm</p>
              </div>
              <div className="rounded-2xl bg-slate-800/50 p-3 text-center ring-1 ring-white/5">
                <p className="text-xs font-medium text-slate-400">Braço</p>
                <p className="mt-1 text-lg font-bold text-white">{latest.arm || "—"}</p>
                <p className="text-[10px] text-slate-500">cm</p>
              </div>
            </div>
          </section>
        )}

        {/* History */}
        <section className="px-5 pt-6" aria-labelledby="historico-evolucao">
          <div className="mb-3 flex items-center justify-between">
            <h2 id="historico-evolucao" className="text-sm font-semibold text-slate-300">
              Histórico de Evolução
            </h2>
            <span className="rounded-full bg-slate-800/70 px-2.5 py-0.5 text-xs font-medium text-slate-400 ring-1 ring-white/5">
              {sorted.length} {sorted.length === 1 ? "registro" : "registros"}
            </span>
          </div>

          {sorted.length === 0 ? (
            <div className="flex flex-col items-center gap-2 rounded-2xl bg-slate-800/40 px-4 py-10 text-center ring-1 ring-white/5">
              <Ruler className="h-8 w-8 text-slate-600" />
              <p className="text-sm text-slate-400">Nenhuma medição registrada ainda.</p>
              <p className="text-xs text-slate-500">Use o formulário acima para registrar sua primeira medida.</p>
            </div>
          ) : (
            <div className="overflow-hidden rounded-2xl ring-1 ring-white/5">
              <table className="w-full border-collapse text-sm">
                <thead>
                  <tr className="bg-slate-800/70 text-left text-xs font-semibold text-slate-400">
                    <th scope="col" className="px-4 py-3">
                      Data
                    </th>
                    <th scope="col" className="px-2 py-3 text-right">
                      Peso
                    </th>
                    <th scope="col" className="px-2 py-3 text-right">
                      Cintura
                    </th>
                    <th scope="col" className="px-2 py-3 text-right">
                      Braço
                    </th>
                    <th scope="col" className="px-3 py-3 text-right">
                      <span className="sr-only">Ações</span>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {sorted.map((m) => (
                    <tr
                      key={m.id}
                      className="border-t border-white/5 bg-slate-800/30 text-slate-200"
                    >
                      <td className="px-4 py-3 font-medium">{formatDate(m.date)}</td>
                      <td className="px-2 py-3 text-right tabular-nums">{m.weight || "—"}</td>
                      <td className="px-2 py-3 text-right tabular-nums">{m.waist || "—"}</td>
                      <td className="px-2 py-3 text-right tabular-nums">{m.arm || "—"}</td>
                      <td className="px-3 py-3 text-right">
                        <button
                          type="button"
                          onClick={() => handleRemove(m.id)}
                          aria-label={`Remover medição de ${formatDate(m.date)}`}
                          className="inline-flex h-8 w-8 items-center justify-center rounded-lg bg-red-500/10 text-red-400 transition-colors active:bg-red-500/20"
                        >
                          <Trash2 className="h-3.5 w-3.5" />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </section>
      </main>
    </div>
  )
}
