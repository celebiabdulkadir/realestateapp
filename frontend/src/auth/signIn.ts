import { setCookievalue } from "@/app/lib/actions";

interface Inputs {
  username: string;
  password: string;
}

export const signIn = async (credentials: Inputs) => {
  try {
    const payload = {
      username: credentials.username,
      password: credentials.password,
    };

    console.log("payload", payload);

    const res = await fetch("http://localhost:8080/auth/auth/token", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    });

    console.log("res", res);

    return res;
  } catch (error) {
    console.log("Error in signIn function:", error);
  }
};
