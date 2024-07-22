import { cookies } from "next/headers";

export const getAllPackages = async (token: string) => {
  try {
    const res = await fetch("http://localhost:8080/order/packages", {
      method: "GET",
      cache: "no-store",
      headers: {
        "Content-Type": "application/json",
        AUTHORIZATION: `Bearer ${token}`,
      },
    });

    console.log("res", res);

    return res;
  } catch (error) {
    console.error("Error in getAllAdverts function:", error);

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
};
