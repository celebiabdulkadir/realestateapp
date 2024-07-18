"use client";
import { cookies } from "next/headers";
import { deleteCookie } from "@/app/lib/actions";
export default function LoginButton() {
  const handleClick = () => {
    deleteCookie("token");
  };

  return (
    <button type="submit" onClick={handleClick}>
      Delete cookies
    </button>
  );
}
