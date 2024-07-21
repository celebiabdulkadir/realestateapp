"use server";
import { signIn } from "@/auth/signIn";
import { redirect } from "next/navigation";
import { cookies } from "next/headers";

export async function deleteCookie(name: string) {
  const cookieStore = cookies();
  cookieStore.delete(name);
}

export async function setCookievalue(name: string, value: string) {
  const cookieStore = cookies();
  cookieStore.set(name, value);
}

export async function getCookieValue(name: string) {
  const cookieStore = cookies();
  return cookieStore.get(name)?.value;
}
