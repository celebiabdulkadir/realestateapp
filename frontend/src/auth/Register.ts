interface RegisterFormValues {
  name: string;
  surname: string;
  email: string;
  phoneNumber: number;
  address: string;
  username: string;
  password: string;
  repeatPassword: string;
}
export const register = async (formData: RegisterFormValues) => {
  try {
    const payload = {
      username: formData.username,
      password: formData.password,
      name: formData.name,
      email: formData.email,
      phoneNumber: String(formData.phoneNumber),
      address: formData.address,
      surname: formData.surname,
    };

    const res = await fetch("http://localhost:8080/auth/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    });

    console.log("registerpayload", res);

    return res;
  } catch (error) {
    return error;
  }
};
