"use client";

import * as React from "react";

import { signIn } from "next-auth/react";
import { yupResolver } from "@hookform/resolvers/yup";
import { Button, Form, Input } from "antd";
import { Controller, set, useForm } from "react-hook-form";
import { setCookievalue } from "@/app/lib/actions";
import { useRouter } from "next/navigation";

import * as yup from "yup";
import Loading from "../loading";

interface FormValues {
  username: string;
  password: string;
}

export default function Page() {
  const [loading, setLoading] = React.useState(false);
  const router = useRouter();
  const schema = yup.object().shape({
    username: yup.string().trim().required("Username is required"),
    password: yup.string().required("Password is required"),
  });

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({
    resolver: yupResolver(schema),
  });

  const onSubmit = async (formData: FormValues) => {
    setLoading(true);
    try {
      const res = await signIn("credentials", {
        redirect: false,
        username: formData.username,
        password: formData.password,
      });

      if (res?.ok) {
        router.push("/");
      } else {
        alert("Login failed");
      }
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="flex items-center justify-center h-screen bg-gray-200 dark:bg-gray-300">
      <div className=" p-4 sm:w-full max-w-[400px] bg-white  rounded-lg drop-shadow-lg border-2 border-white shadow-lg shadow-[#ccc]">
        <div className="text-center flex flex-col justify-center items-center mb-5">
          <div className="text-gray-900  text-3xl font-medium mb-3">
            {"Login"}
          </div>
        </div>
        <Form onFinish={handleSubmit(onSubmit)} layout="vertical">
          <div className="w-full">
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

            <div className="flex w-full justify-between gap-4">
              <Form.Item className="w-full">
                <Button className="w-full" type="primary" htmlType="submit">
                  Login
                </Button>
              </Form.Item>
            </div>

            <div className="flex flex-col gap-2">
              <p>You don't an have account ?</p>
              <Button
                className="w-full"
                type="primary"
                onClick={() => router.push("/register")}
              >
                Register
              </Button>
            </div>
          </div>
        </Form>
      </div>
    </div>
  );
}
