import { NextResponse } from "next/server"

export function GET() {
  return NextResponse.json({
    name: "GymCheck",
    short_name: "GymCheck",
    description: "Acompanhe seus treinos, dieta e medidas corporais em um só lugar.",
    start_url: "/",
    display: "standalone",
    orientation: "portrait",
    background_color: "#020617",
    theme_color: "#020617",
    icons: [
      { src: "/icon.svg", sizes: "any", type: "image/svg+xml", purpose: "any" },
      { src: "/apple-icon.png", sizes: "180x180", type: "image/png" },
    ],
  })
}
