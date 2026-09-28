"use client"

import type React from "react"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Plus, Pencil, Trash2, Dumbbell } from "lucide-react"

type Exercise = {
  id: string
  name: string
  sets: string
  reps: string
  load: string
}

const initialExercises: Exercise[] = [
  { id: "1", name: "Supino Reto", sets: "4", reps: "10", load: "60" },
  { id: "2", name: "Supino Inclinado", sets: "3", reps: "12", load: "24" },
  { id: "3", name: "Tríceps Corda", sets: "4", reps: "15", load: "30" },
]

const emptyForm = { name: "", sets: "", reps: "", load: "" }

export function WorkoutBuilder() {
  const [exercises, setExercises] = useState<Exercise[]>(initialExercises)
  const [form, setForm] = useState(emptyForm)
  const [editingId, setEditingId] = useState<string | null>(null)

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    if (!form.name.trim()) return

    if (editingId) {
      setExercises((prev) => prev.map((ex) => (ex.id === editingId ? { ...ex, ...form } : ex)))
      setEditingId(null)
    } else {
      setExercises((prev) => [...prev, { id: crypto.randomUUID(), ...form }])
    }
    setForm(emptyForm)
  }

  function handleEdit(ex: Exercise) {
    setEditingId(ex.id)
    setForm({ name: ex.name, sets: ex.sets, reps: ex.reps, load: ex.load })
  }

  function handleRemove(id: string) {
    setExercises((prev) => prev.filter((ex) => ex.id !== id))
    if (editingId === id) {
      setEditingId(null)
      setForm(emptyForm)
    }
  }

  function handleCancelEdit() {
    setEditingId(null)
    setForm(emptyForm)
  }

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

        {/* Workout Header */}
        <section className="px-5 pt-6">
          <div className="flex items-start gap-3">
            <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-gradient-to-br from-indigo-500 to-blue-600 shadow-lg shadow-indigo-500/20">
              <Dumbbell className="h-6 w-6 text-white" />
            </div>
            <div className="min-w-0">
              <h1 className="text-balance text-xl font-bold leading-tight text-white">Treino A - Peito e Tríceps</h1>
              <span className="mt-1.5 inline-flex items-center rounded-full bg-indigo-500/15 px-2.5 py-0.5 text-xs font-medium text-indigo-300 ring-1 ring-inset ring-indigo-500/20">
                Hipertrofia · Foco superior
              </span>
            </div>
          </div>
        </section>

        {/* Add Exercise Form */}
        <section className="px-5 pt-6">
          <div className="rounded-2xl bg-slate-800/50 p-4 ring-1 ring-white/5">
            <h2 className="mb-4 text-sm font-semibold text-slate-300">
              {editingId ? "Editar Exercício" : "Formulário de Exercício"}
            </h2>
            <form onSubmit={handleSubmit} className="flex flex-col gap-3">
              <div className="flex flex-col gap-1.5">
                <label htmlFor="name" className="text-xs font-medium text-slate-400">
                  Nome do Exercício
                </label>
                <input
                  id="name"
                  type="text"
                  inputMode="text"
                  placeholder="Ex: Supino Reto, Leg Press..."
                  value={form.name}
                  onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))}
                  className="w-full rounded-xl border-0 bg-slate-900/70 px-3.5 py-2.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                />
              </div>

              <div className="grid grid-cols-3 gap-3">
                <div className="flex flex-col gap-1.5">
                  <label htmlFor="sets" className="text-xs font-medium text-slate-400">
                    Séries
                  </label>
                  <input
                    id="sets"
                    type="number"
                    inputMode="numeric"
                    min="0"
                    placeholder="4"
                    value={form.sets}
                    onChange={(e) => setForm((f) => ({ ...f, sets: e.target.value }))}
                    className="w-full rounded-xl border-0 bg-slate-900/70 px-3 py-2.5 text-center text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
                <div className="flex flex-col gap-1.5">
                  <label htmlFor="reps" className="text-xs font-medium text-slate-400">
                    Repetições
                  </label>
                  <input
                    id="reps"
                    type="number"
                    inputMode="numeric"
                    min="0"
                    placeholder="12"
                    value={form.reps}
                    onChange={(e) => setForm((f) => ({ ...f, reps: e.target.value }))}
                    className="w-full rounded-xl border-0 bg-slate-900/70 px-3 py-2.5 text-center text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
                <div className="flex flex-col gap-1.5">
                  <label htmlFor="load" className="text-xs font-medium text-slate-400">
                    Carga (kg)
                  </label>
                  <input
                    id="load"
                    type="number"
                    inputMode="decimal"
                    min="0"
                    step="0.5"
                    placeholder="60"
                    value={form.load}
                    onChange={(e) => setForm((f) => ({ ...f, load: e.target.value }))}
                    className="w-full rounded-xl border-0 bg-slate-900/70 px-3 py-2.5 text-center text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
              </div>

              <div className="flex gap-2">
                {editingId && (
                  <button
                    type="button"
                    onClick={handleCancelEdit}
                    className="flex-1 rounded-xl bg-slate-700/60 px-4 py-3 text-sm font-semibold text-slate-200 transition-colors active:bg-slate-700"
                  >
                    Cancelar
                  </button>
                )}
                <button
                  type="submit"
                  className="flex flex-[2] items-center justify-center gap-2 rounded-xl bg-gradient-to-r from-indigo-500 to-blue-600 px-4 py-3 text-sm font-semibold text-white shadow-lg shadow-indigo-500/20 transition-opacity active:opacity-90"
                >
                  <Plus className="h-4 w-4" />
                  {editingId ? "Salvar Alterações" : "Adicionar ao Treino"}
                </button>
              </div>
            </form>
          </div>
        </section>

        {/* Exercise List */}
        <section className="px-5 pt-6" aria-labelledby="exercicios-montados">
          <div className="mb-3 flex items-center justify-between">
            <h2 id="exercicios-montados" className="text-sm font-semibold text-slate-300">
              Exercícios Montados
            </h2>
            <span className="rounded-full bg-slate-800/70 px-2.5 py-0.5 text-xs font-medium text-slate-400 ring-1 ring-white/5">
              {exercises.length} {exercises.length === 1 ? "exercício" : "exercícios"}
            </span>
          </div>

          {exercises.length === 0 ? (
            <div className="flex flex-col items-center gap-2 rounded-2xl bg-slate-800/40 px-4 py-10 text-center ring-1 ring-white/5">
              <Dumbbell className="h-8 w-8 text-slate-600" />
              <p className="text-sm text-slate-400">Nenhum exercício adicionado ainda.</p>
              <p className="text-xs text-slate-500">Use o formulário acima para montar seu treino.</p>
            </div>
          ) : (
            <ul className="flex flex-col gap-2.5">
              {exercises.map((ex) => (
                <li key={ex.id} className="rounded-2xl bg-slate-800/50 p-4 ring-1 ring-white/5">
                  <div className="flex items-start justify-between gap-3">
                    <div className="min-w-0 flex-1">
                      <p className="truncate text-base font-semibold text-white">{ex.name}</p>
                      <div className="mt-2.5 flex items-center gap-2">
                        <Stat label="Séries" value={ex.sets || "-"} />
                        <Stat label="Reps" value={ex.reps || "-"} />
                        <Stat label="Carga" value={ex.load ? `${ex.load} kg` : "-"} />
                      </div>
                    </div>
                    <div className="flex shrink-0 items-center gap-1.5">
                      <button
                        type="button"
                        onClick={() => handleEdit(ex)}
                        aria-label={`Editar ${ex.name}`}
                        className="flex h-9 w-9 items-center justify-center rounded-lg bg-slate-700/50 text-slate-300 transition-colors active:bg-slate-700"
                      >
                        <Pencil className="h-4 w-4" />
                      </button>
                      <button
                        type="button"
                        onClick={() => handleRemove(ex.id)}
                        aria-label={`Remover ${ex.name}`}
                        className="flex h-9 w-9 items-center justify-center rounded-lg bg-red-500/10 text-red-400 transition-colors active:bg-red-500/20"
                      >
                        <Trash2 className="h-4 w-4" />
                      </button>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </section>
      </main>
    </div>
  )
}

function Stat({ label, value }: { label: string; value: string }) {
  return (
    <div className="flex flex-col items-center rounded-lg bg-slate-900/60 px-3 py-1.5 ring-1 ring-white/5">
      <span className="text-[10px] font-medium uppercase tracking-wide text-slate-500">{label}</span>
      <span className="text-sm font-semibold text-white">{value}</span>
    </div>
  )
}
