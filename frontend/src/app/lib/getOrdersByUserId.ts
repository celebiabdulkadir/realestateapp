export const getOrdersByUserId = async (id: string, token: string) => {
  try {
    if (!id) {
      throw new Error("Id is required.");
    }
    console.log("id", id);
    const res = await fetch(
      `http://localhost:8080/order/order/orderByUserId/${id}`,
      {
        method: "GET",
        cache: "no-store",
        headers: {
          "Content-Type": "application/json",
          AUTHORIZATION: `Bearer ${token}`,
        },
      }
    );

    console.log("getAllOrders", res);

    if (!res.ok) {
      throw new Error(`Error: ${res.status}`);
    }

    return res;
  } catch (error) {
    console.error("Error in getOrdersByUserId function:", error);
    throw error;
  }
};
