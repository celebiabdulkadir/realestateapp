import { cookies } from "next/headers";

export const getAllAdverts = async () => {
  try {
    const cookieStore = cookies();
    const token = cookieStore.get("token")?.value;
    console.log("token", token);
    const res = await fetch("http://localhost:8080/advert/advert", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
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
