export const deleteAdvertById = async (id: string, token: string) => {
  try {
    if (!id) {
      throw new Error("Id is required.");
      return;
    }
    console.log("id", id);
    const res = await fetch(`http://localhost:8080/advert/advert/${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        AUTHORIZATION: `Bearer ${token}`,
      },
    });

    console.log("delete res", res);

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
