"use client"

import type React from "react"

import { useState } from "react"
import Link from "next/link"
import { Dumbbell, User, Mail, Lock, Eye, EyeOff, AlertCircle, CheckCircle2, UserPlus } from "lucide-react"

export function RegisterForm() {
  const [name, setName] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [confirmPassword, setConfirmPassword] = useState("")
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirm, setShowConfirm] = useState(false)
  const [acceptedTerms, setAcceptedTerms] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState(false)

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault()

    if (!name.trim() || !email.trim() || !password.trim() || !confirmPassword.trim()) {
      setSuccess(false)
      setError("Preencha todos os campos para continuar")
      return
    }
    if (password.length < 6) {
      setSuccess(false)
      setError("A senha deve ter no mínimo 6 caracteres")
      return
    }
    if (password !== confirmPassword) {
      setSuccess(false)
      setError("As senhas não coincidem")
      return
    }
    if (!acceptedTerms) {
      setSuccess(false)
      setError("Você precisa aceitar os Termos de Uso para continuar")
      return
    }

    setError(null)
    setSuccess(true)
    // Placeholder: fluxo de criação de conta seria conectado aqui.
  }

  return (
    <div className="min-h-screen bg-slate-950 text-white">
      <main className="relative mx-auto flex min-h-screen max-w-md flex-col bg-gradient-to-b from-slate-900 to-slate-950 px-6 pb-10">
        {/* Ambient glow */}
        <div
          aria-hidden="true"
          className="pointer-events-none absolute inset-x-0 top-0 -z-0 mx-auto h-64 w-64 rounded-full bg-indigo-600/20 blur-3xl"
        />

        {/* Header Navigation */}
        <header className="relative flex items-center pt-6">
          <Link
            href="/login"
            className="flex items-center gap-1.5 text-sm font-medium text-slate-300 transition-colors active:text-white"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 20 20"
              fill="currentColor"
              className="h-4 w-4"
              aria-hidden="true"
            >
              <path
                fillRule="evenodd"
                d="M12.79 5.23a.75.75 0 0 1 0 1.06L9.06 10l3.73 3.71a.75.75 0 1 1-1.06 1.06l-4.25-4.24a.75.75 0 0 1 0-1.06l4.25-4.24a.75.75 0 0 1 1.06 0Z"
                clipRule="evenodd"
              />
            </svg>
            Voltar ao Login
          </Link>
        </header>

        {/* Branding + Title */}
        <section className="relative mt-8 flex flex-col items-center text-center">
          <div className="flex h-16 w-16 items-center justify-center rounded-2xl bg-gradient-to-br from-indigo-500 to-blue-600 shadow-lg shadow-indigo-500/30">
            <Dumbbell className="h-8 w-8 text-white" />
          </div>
          <h1 className="mt-4 text-balance text-2xl font-bold tracking-tight text-white">Crie sua conta no GymCheck</h1>
          <p className="mt-1 text-pretty text-sm text-slate-400">
            Comece a acompanhar seus treinos e evolução hoje mesmo
          </p>
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
        {success && (
          <div
            role="status"
            className="relative mt-6 flex items-center gap-2.5 rounded-xl bg-emerald-500/10 px-4 py-3 text-sm font-medium text-emerald-300 ring-1 ring-inset ring-emerald-500/20"
          >
            <CheckCircle2 className="h-4 w-4 shrink-0" />
            Conta criada com sucesso!
          </div>
        )}

        {/* Registration Form */}
        <form onSubmit={handleSubmit} className="relative mt-8 flex flex-col gap-4">
          <div className="flex flex-col gap-1.5">
            <label htmlFor="register-name" className="text-xs font-medium text-slate-400">
              Nome Completo
            </label>
            <div className="relative">
              <User className="pointer-events-none absolute left-3.5 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500" />
              <input
                id="register-name"
                type="text"
                autoComplete="name"
                placeholder="Ex: João Silva"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full rounded-xl border-0 bg-slate-900/70 py-3 pl-10 pr-3.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
            </div>
          </div>

          <div className="flex flex-col gap-1.5">
            <label htmlFor="register-email" className="text-xs font-medium text-slate-400">
              E-mail
            </label>
            <div className="relative">
              <Mail className="pointer-events-none absolute left-3.5 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500" />
              <input
                id="register-email"
                type="email"
                autoComplete="email"
                placeholder="seu@email.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full rounded-xl border-0 bg-slate-900/70 py-3 pl-10 pr-3.5 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
            </div>
          </div>

          <div className="flex flex-col gap-1.5">
            <label htmlFor="register-password" className="text-xs font-medium text-slate-400">
              Senha
            </label>
            <div className="relative">
              <Lock className="pointer-events-none absolute left-3.5 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500" />
              <input
                id="register-password"
                type={showPassword ? "text" : "password"}
                autoComplete="new-password"
                placeholder="Mínimo de 6 caracteres"
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

          <div className="flex flex-col gap-1.5">
            <label htmlFor="register-confirm" className="text-xs font-medium text-slate-400">
              Confirmar Senha
            </label>
            <div className="relative">
              <Lock className="pointer-events-none absolute left-3.5 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500" />
              <input
                id="register-confirm"
                type={showConfirm ? "text" : "password"}
                autoComplete="new-password"
                placeholder="Repita sua senha"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                className="w-full rounded-xl border-0 bg-slate-900/70 py-3 pl-10 pr-11 text-sm text-white shadow-inner ring-1 ring-inset ring-white/10 placeholder:text-slate-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              />
              <button
                type="button"
                onClick={() => setShowConfirm((v) => !v)}
                aria-label={showConfirm ? "Ocultar senha" : "Mostrar senha"}
                className="absolute right-2 top-1/2 flex h-8 w-8 -translate-y-1/2 items-center justify-center rounded-lg text-slate-400 transition-colors active:bg-slate-800"
              >
                {showConfirm ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
              </button>
            </div>
          </div>

          {/* Terms */}
          <label htmlFor="register-terms" className="flex cursor-pointer items-start gap-2.5 text-sm text-slate-300">
            <input
              id="register-terms"
              type="checkbox"
              checked={acceptedTerms}
              onChange={(e) => setAcceptedTerms(e.target.checked)}
              className="mt-0.5 h-4 w-4 shrink-0 rounded border-white/10 bg-slate-900/70 text-indigo-500 accent-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
            />
            <span className="text-pretty leading-snug">
              Li e aceito os{" "}
              <Link href="#" className="font-medium text-indigo-400 transition-colors active:text-indigo-300">
                Termos de Uso
              </Link>{" "}
              e{" "}
              <Link href="#" className="font-medium text-indigo-400 transition-colors active:text-indigo-300">
                Política de Privacidade
              </Link>
            </span>
          </label>

          <button
            type="submit"
            className="mt-2 flex items-center justify-center gap-2 rounded-xl bg-gradient-to-r from-indigo-500 to-blue-600 px-4 py-3.5 text-sm font-semibold text-white shadow-lg shadow-indigo-500/25 transition-opacity active:opacity-90"
          >
            <UserPlus className="h-4 w-4" />
            Criar Minha Conta
          </button>
        </form>

        {/* Footer action */}
        <p className="relative mt-8 text-center text-sm text-slate-400">
          Já possui uma conta?{" "}
          <Link href="/login" className="font-semibold text-indigo-400 transition-colors active:text-indigo-300">
            Fazer Login
          </Link>
        </p>
      </main>
    </div>
  )
}
