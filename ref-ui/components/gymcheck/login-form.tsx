"use client"

import type React from "react"

import { useState } from "react"
import Link from "next/link"
import { Dumbbell, Mail, Lock, Eye, EyeOff, AlertCircle, CheckCircle2, LogIn } from "lucide-react"

export function LoginForm() {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [showPassword, setShowPassword] = useState(false)
  const [remember, setRemember] = useState(false)
  const [error, setError] = useState<string | null>(null)

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    if (!email.trim() || !password.trim()) {
      setError("Usuário ou senha inválidos")
      return
    }
    setError(null)
    // Placeholder: fluxo de autenticação seria conectado aqui.
  }

  return (
    <div className="min-h-screen bg-slate-950 text-white">
      <main className="relative mx-auto flex min-h-screen max-w-md flex-col justify-center bg-gradient-to-b from-slate-900 to-slate-950 px-6 py-10">
        {/* Ambient glow */}
        <div
          aria-hidden="true"
          className="pointer-events-none absolute inset-x-0 top-0 -z-0 mx-auto h-64 w-64 rounded-full bg-indigo-600/20 blur-3xl"
        />

        {/* Branding */}
        <section className="relative flex flex-col items-center text-center">
          <div className="flex h-16 w-16 items-center justify-center rounded-2xl bg-gradient-to-br from-indigo-500 to-blue-600 shadow-lg shadow-indigo-500/30">
            <Dumbbell className="h-8 w-8 text-white" />
          </div>
          <h1 className="mt-4 text-2xl font-bold tracking-tight text-white">GymCheck</h1>
          <p className="mt-1 text-sm text-slate-400">Seu parceiro de treinos e evolução</p>
        </section>

        {/* Alert Area */}
        {error && (
          <div
            role="alert"
            className="relative mt-6 flex items-center gap-2.5 rounded-xl bg-red-500/10 px-4 py-3 text-sm font-medium text-red-300 ring-1 ring-inset ring-red-500/20"
          >
            <AlertCircle className="h-4 w-4 shrink-0" />
            {error}
          </div>
        )}

        {/* Login Form */}
        <form onSubmit={handleSubmit} className="relative mt-8 flex flex-col gap-4">
          <div className="flex flex-col gap-1.5">
            <label htmlFor="login-email" className="text-xs font-medium text-slate-400">
              E-mail ou Usuário
            </label>
            <div className="relative">
              <Mail className="pointer-events-none absolute left-3.5 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500" />
              <input
                id="login-email"
                type="text"
                autoComplete="username"
                placeholder="seu@email.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full rounded-xl border-0 bg-slate-900/70 py-3 pl-10 pr-3.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
            </div>
          </div>

          <div className="flex flex-col gap-1.5">
            <label htmlFor="login-password" className="text-xs font-medium text-slate-400">
              Senha
            </label>
            <div className="relative">
              <Lock className="pointer-events-none absolute left-3.5 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500" />
              <input
                id="login-password"
                type={showPassword ? "text" : "password"}
                autoComplete="current-password"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full rounded-xl border-0 bg-slate-900/70 py-3 pl-10 pr-11 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
              <button
                type="button"
                onClick={() => setShowPassword((v) => !v)}
                aria-label={showPassword ? "Ocultar senha" : "Mostrar senha"}
                className="absolute right-2 top-1/2 flex h-8 w-8 -translate-y-1/2 items-center justify-center rounded-lg text-slate-400 transition-colors active:bg-slate-800"
              >
                {showPassword ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
              </button>
            </div>
          </div>

          {/* Remember + forgot */}
          <div className="flex items-center justify-between">
            <label htmlFor="login-remember" className="flex cursor-pointer items-center gap-2 text-sm text-slate-300">
              <input
                id="login-remember"
                type="checkbox"
                checked={remember}
                onChange={(e) => setRemember(e.target.checked)}
                className="h-4 w-4 rounded border-white/10 bg-slate-900/70 text-indigo-500 accent-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
              Lembrar de mim
            </label>
            <Link href="#" className="text-sm font-medium text-indigo-400 transition-colors active:text-indigo-300">
              Esqueceu a senha?
            </Link>
          </div>

          <button
            type="submit"
            className="mt-2 flex items-center justify-center gap-2 rounded-xl bg-gradient-to-r from-indigo-500 to-blue-600 px-4 py-3.5 text-sm font-semibold text-white shadow-lg shadow-indigo-500/25 transition-opacity active:opacity-90"
          >
            <LogIn className="h-4 w-4" />
            Entrar na Conta
          </button>
        </form>

        {/* Footer / secondary action */}
        <p className="relative mt-8 text-center text-sm text-slate-400">
          Ainda não tem uma conta?{" "}
          <Link href="#" className="font-semibold text-indigo-400 transition-colors active:text-indigo-300">
            Cadastre-se
          </Link>
        </p>
      </main>
    </div>
  )
}
