import { redirect } from "next/navigation";

export const getAvailableAdvertRight = async (id: string, token: string) => {
  try {
    if (!id) {
      throw new Error("Id is required.");
    }
    console.log("id", id);
    const res = await fetch(
      `http://localhost:8080/order/order/availableAdvertRights/${id}`,
      {
        method: "GET",
        cache: "no-store",
        headers: {
          "Content-Type": "application/json",
          AUTHORIZATION: `Bearer ${token}`,
        },
      }
    );

    console.log("getAvailableAdvertRight", res);

    if (!res.ok) {
      throw new Error(`Error: ${res.status}`);
    } else if (res.status === 401) {
      redirect("/login");
      throw new Error("Unauthorized");
    }

    const data = await res.json();
    return data;
  } catch (error) {
    console.error("Error in getAvailableAdvertRight function:", error);
    throw error;
  }
};
