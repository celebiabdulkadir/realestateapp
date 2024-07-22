import { signOut } from "next-auth/react";

interface FormValues {
  userId: number;
  packageId: number;
  packageQuantity: number;
  packagePrice: number;
  packageAdvertQuantity: number;
}
export const buyPackage = async (formData: FormValues, token: string) => {
  try {
    if (!token) {
      throw new Error("Id is required.");
    }
    const res = await fetch(`http://localhost:8080/order/order`, {
      method: "POST",
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
        AUTHORIZATION: `Bearer ${token}`,
      },
      body: JSON.stringify(formData),
    });

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
