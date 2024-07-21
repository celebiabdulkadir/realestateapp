"use client";

import * as React from "react";
import { yupResolver } from "@hookform/resolvers/yup";
import { Button, Form, Input, InputNumber } from "antd";
import { Controller, set, useForm } from "react-hook-form";
import { setCookievalue } from "@/app/lib/actions";
import { useRouter } from "next/navigation";

import * as yup from "yup";
import { register } from "@/auth/register";
import { TruckFilled } from "@ant-design/icons";
import { enqueueSnackbar } from "notistack";

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

export default function Page() {
  const router = useRouter();
  const schema = yup.object().shape({
    name: yup.string().trim().required("Name is required"),
    surname: yup.string().trim().required("Surname is required"),
    username: yup.string().trim().required("Username is required"),
    password: yup
      .string()
      .trim()
      .required("Password is required")
      .min(8, "Password lenght must be greater than 8"),

    repeatPassword: yup
      .string()
      .trim()
      .required("Repeat password is required")
      .oneOf([yup.ref("password")], "Passwords must match")
      .min(8, "form_validations.password_length"),
    email: yup.string().email().required("Email is required"),
    phoneNumber: yup
      .number()
      .typeError("Phone number must be a number")
      .required("Phone number is required"),
    address: yup.string().required("Address is required"),
  });

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<RegisterFormValues>({
    resolver: yupResolver(schema),
  });

  const onSubmit = async (formData: RegisterFormValues) => {
    try {
      const response = (await register(formData)) as Response;
      console.log("response", response);
      if (response.status === 200) {
        enqueueSnackbar("Register success", { variant: "success" });
        router.push("/login");
      } else if (response.status === 400) {
        enqueueSnackbar("Bad request", { variant: "error" });
      } else if (response.status === 409) {
        enqueueSnackbar("User already exists", { variant: "error" });
      } else {
        enqueueSnackbar("Register failed", { variant: "error" });
      }
    } catch (error: any) {
      if (error.status === 409) {
        console.log("error", error.status);
        enqueueSnackbar("User already exists", { variant: "error" });
      } else {
        enqueueSnackbar("Register failed", { variant: "error" });
      }

      if (error.status === 400) {
        enqueueSnackbar("Bad request", { variant: "error" });
      }
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-200 dark:bg-gray-300">
      <div className=" p-4 w-full max-w-[500px] bg-white rounded-lg drop-shadow-lg border-2 border-white shadow-lg shadow-[#ccc]">
        <div className="text-center flex flex-col justify-center items-center mb-5">
          <div className="text-gray-900  text-3xl font-medium mb-3">
            {"Register"}
          </div>
        </div>
        <Form onFinish={handleSubmit(onSubmit)} layout="vertical">
          <div className="w-full">
            <div className="flex flex-col sm:flex-row  sm:justify-between">
              <div className="flex flex-col">
                <Controller
                  name="name"
                  control={control}
                  render={({ field }) => (
                    <Form.Item
                      label={"Name"}
                      help={errors.name?.message}
                      validateStatus={errors.name ? "error" : ""}
                    >
                      <Input
                        className=" dark:bg-gray-500 "
                        placeholder={"Name"}
                        {...field}
                      />
                    </Form.Item>
                  )}
                />
              </div>
              <div className="flex flex-col">
                <Controller
                  name="surname"
                  control={control}
                  render={({ field }) => (
                    <Form.Item
                      label={"Surname"}
                      help={errors.surname?.message}
                      validateStatus={errors.surname ? "error" : ""}
                    >
                      <Input
                        className=" dark:bg-gray-500 "
                        placeholder={"Surname"}
                        {...field}
                      />
                    </Form.Item>
                  )}
                />
              </div>
            </div>
            <div className="flex flex-col sm:flex-row sm:justify-between">
              <div className="flex flex-col">
                <Controller
                  name="username"
                  control={control}
                  render={({ field }) => (
                    <Form.Item
                      label={"Username"}
                      help={errors.username?.message}
                      validateStatus={errors.username ? "error" : ""}
                    >
                      <Input
                        className=" dark:bg-gray-500 "
                        placeholder={"Username"}
                        {...field}
                      />
                    </Form.Item>
                  )}
                />
              </div>
              <div className="flex flex-col">
                <Controller
                  name="email"
                  control={control}
                  render={({ field }) => (
                    <Form.Item
                      label={"Email"}
                      help={errors.email?.message}
                      validateStatus={errors.email ? "error" : ""}
                    >
                      <Input
                        className=" dark:bg-gray-500 "
                        placeholder={"Email"}
                        {...field}
                      />
                    </Form.Item>
                  )}
                />
              </div>
            </div>
            <div className="flex flex-col">
              <Controller
                name="phoneNumber"
                control={control}
                render={({ field }) => (
                  <Form.Item
                    label={"Phone Number"}
                    help={errors.phoneNumber?.message}
                    validateStatus={errors.phoneNumber ? "error" : ""}
                  >
                    <Input
                      addonBefore={"+90"}
                      placeholder={"5555555555"}
                      {...field}
                    />
                  </Form.Item>
                )}
              />
            </div>
            <div className="flex flex-col">
              <Controller
                name="address"
                control={control}
                render={({ field }) => (
                  <Form.Item
                    label={"Address"}
                    help={errors.address?.message}
                    validateStatus={errors.address ? "error" : ""}
                  >
                    <Input
                      className="w-full"
                      addonBefore={<TruckFilled />}
                      placeholder={"Address"}
                      {...field}
                    />
                  </Form.Item>
                )}
              />
            </div>
            <div className="flex flex-col">
              <Controller
                name="password"
                control={control}
                render={({ field }) => (
                  <Form.Item
                    label={"Password"}
                    help={errors.password?.message}
                    validateStatus={errors.password ? "error" : ""}
                  >
                    <Input.Password
                      className=" dark:bg-gray-500 "
                      placeholder={"Password"}
                      {...field}
                    />
                  </Form.Item>
                )}
              />
            </div>
            <div className="flex flex-col">
              <Controller
                name="repeatPassword"
                control={control}
                render={({ field }) => (
                  <Form.Item
                    label={"Repeat Password"}
                    help={errors.repeatPassword?.message}
                    validateStatus={errors.repeatPassword ? "error" : ""}
                  >
                    <Input.Password
                      placeholder={"Repeat Password"}
                      {...field}
                    />
                  </Form.Item>
                )}
              />
            </div>

            <div className="flex w-full justify-between gap-4">
              <Form.Item className="w-full">
                <Button className="w-full" type="primary" htmlType="submit">
                  Register
                </Button>
              </Form.Item>
            </div>
            <div className="w-full flex flex-col gap-4">
              <p>You have already an account ?</p>
              <Button
                className="w-full"
                type="primary"
                onClick={() => router.push("/login")}
              >
                Login
              </Button>
            </div>
          </div>
        </Form>
      </div>
    </div>
  );
}
