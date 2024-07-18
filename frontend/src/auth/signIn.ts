export const signIn = async (formData: FormData) => {
  const payload: { [key: string]: FormDataEntryValue } = {};

  for (const [key, value] of formData.entries()) {
    if (key === "email") {
      payload["username"] = value;
    } else if (key === "password") {
      payload[key] = value;
    }
  }

  console.log(payload);

  const res = await fetch("http://localhost:8080/auth/auth/token", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (res.ok) {
    return await res.text();
  } else {
    throw await res.json();
  }
};
