import { signOut } from "next-auth/react";

interface FormValues {
  userId: number;
  title: string;
  description: string;
  address: string;
  price: number;
  area: number;
  room: number;
  floor: number;
  totalFloor: number;
  heating: string;
  balcony: boolean;
  elevator: boolean;
  fromHomeOwner: boolean;
  fromAgency: boolean;
  furnished: boolean;
  credit: boolean;
  swap: boolean;
  advertType: string;
}
export const createAdvert = async (formData: FormValues, token: string) => {
  try {
    if (!token) {
      throw new Error("Id is required.");
    }
    const res = await fetch(`http://localhost:8080/advert/advert`, {
      method: "POST",
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
        AUTHORIZATION: `Bearer ${token}`,
      },
      body: JSON.stringify(formData),
    });

    console.log("createadvertResponse", res.json());
    if (res.status === 401) {
      // If the API returns 401, sign the user out
      signOut({ callbackUrl: "/login" });
    }
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
