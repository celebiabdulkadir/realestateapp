"use server";
import { signIn } from "@/auth/signIn";
import { redirect } from "next/dist/server/api-utils";
import { cookies } from "next/headers";

export async function authenticate(_currentState: unknown, formData: FormData) {
  try {
    const res = await signIn(formData);

    console.log("singin response", res);
    const cookieStore = cookies();
    cookieStore.set("token", res);
    // Return a redirect object instead of calling redirect function
  } catch (error) {
    console.error("Error in authenticate function:", error);

    if (error) {
      switch ((error as { type: string }).type) {
        case "CredentialsSignin":
          return "Invalid credentials.";
        default:
          return "Something went wrong.";
      }
    }
    throw error;
  }
}

export async function deleteCookie(name: string) {
  const cookieStore = cookies();
  cookieStore.delete(name);
}
