interface FormValues {
  name: string;
  surname: string;
  email: string;
  phoneNumber: number;
  address: string;
  username: string;
  password: string;
  repeatPassword: string;
}
export const register = async (formData: FormValues) => {
  const payload = {
    username: formData.username,
    password: formData.password,
    name: formData.name,
    email: formData.email,
    phoneNumber: formData.phoneNumber,
    address: formData.address,
  };

  console.log(payload);

  const res = await fetch("http://localhost:8080/auth/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  return res;
};
