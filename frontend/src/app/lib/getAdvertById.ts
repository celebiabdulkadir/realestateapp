export const getAdvertById = async (id: string) => {
  try {
    if (!id) {
      throw new Error("Id is required.");
      return;
    }
    console.log("id", id);
    const res = await fetch(`http://localhost:8080/advert/advert/${id}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
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
