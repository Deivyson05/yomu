import * as React from "react"
import { Signature, CalendarHeart, Phone, AtSign, KeyRound, BadgeQuestionMark, UserRound } from "lucide-react";

import { cn } from "@/lib/utils"

function Input({ className, type, name, id, ...props }: React.ComponentProps<"input">) {

  let icon;

  if (name == "nome") {
    icon = <Signature />;
  } else if (name == "nascimento") {
    icon = <CalendarHeart />;
  } else if (name == "telefone") {
    icon = <Phone />;
  } else if (name == "email") {
    icon = <AtSign />;
  } else if (name == "pass") {
    icon = <KeyRound />;
  } else if (name == "username") {
    icon = <UserRound />;
  } else {
    icon = <BadgeQuestionMark />;
  }

  const styleInput = type == "file" ? "hidden" : "";
  const styleDiv = type == "file" ? "flex items-center justify-center" : "";

  return (
    <div className={`flex h-14 rounded-md overflow-hidden shadow-sm w-full ${styleDiv}`}>
      {
        type == "file" ? (
          <label htmlFor={id} className="w-full bg-secondary h-full flex items-center justify-center">
            Carregar foto
          </label>
        ) : (
          <div className="h-full w-16 flex items-center justify-center bg-primary text-white text-center">
            {icon}
          </div>
        )
      }
      <input
        type={type}
        data-slot="input"
        id={id}
        className={cn(
          styleInput,
          "file:text-foreground placeholder:text-muted-foreground selection:bg-primary selection:text-primary-foreground dark:bg-input/30 border-input h-full w-full min-w-0 bg-transparent px-3 py-1 text-base transition-[color,box-shadow] outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium disabled:pointer-events-none disabled:cursor-not-allowed disabled:opacity-50 md:text-sm file:flex file:tlex-row file:items-center file:justify-center",
          "focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px]",
          "aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive bg-gray-200",
          className
        )}
        {...props}
      />


    </div>
  )
}

export { Input }
