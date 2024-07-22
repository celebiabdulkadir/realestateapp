"use client";

import * as React from "react";
import { signOut, useSession } from "next-auth/react";
import { Button, Form, Input, InputNumber, Modal, Radio, Select } from "antd";
import { Controller, set, useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { useRouter } from "next/navigation";
import Loading from "@/app/loading"; // Ensure you have a Loading component
import { CustomUser } from "@/interfaces";
import { getOrdersByUserId } from "@/app/lib/getOrdersByUserId";
import { enqueueSnackbar } from "notistack";
import { buyPackage } from "@/app/lib/buyPackage";
import { getAllPackages } from "@/app/lib/getAllPackages";

interface FormValues {
  userId: number;
  packageId: number;
  packageQuantity: number;
  packagePrice: number;
  packageAdvertQuantity: number;
}

interface Package {
  id: number;
  name: string;
  price: number;
  advertQuantity: number;
}

type FormValuesWitoutUserId = Omit<
  FormValues,
  "userId" | "packageId" | "packagePrice" | "packageAdvertQuantity"
>;

export default function PurchasePackage({
  isModalOpen,
  handleCancel,
}: {
  isModalOpen: boolean;
  handleCancel: () => void;
}) {
  const [loading, setLoading] = React.useState(false);
  const [packageInfo, setPackageInfo] = React.useState({} as Package);

  const router = useRouter();
  const { data: session } = useSession();

  const schema = yup.object().shape({
    packageQuantity: yup
      .number()
      .required("Package Advert Quantity is required"),
  });
  React.useEffect(() => {
    if (!isModalOpen) {
      return;
    }
    // Fetch package info here
    getPackageInfo();
  }, [isModalOpen]);

  const getPackageInfo = async () => {
    const token = (session?.user as CustomUser).token;
    // Fetch package info here
    const res = await getAllPackages(token);
    const packages = await (res as Response).json();
    setPackageInfo(packages ? packages[0] : ({} as Package));
  };
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValuesWitoutUserId>({
    resolver: yupResolver(schema),
  });

  const handleOk = () => {
    handleCancel();
  };
  const onSubmit = async (formData: FormValuesWitoutUserId) => {
    const token = (session?.user as CustomUser).token;
    const userId = Number((session?.user as CustomUser).userId);

    const packageId = packageInfo?.id;
    const packagePrice = packageInfo?.price;
    const packageAdvertQuantity = packageInfo?.advertQuantity;
    const dataWithUserId = {
      ...formData,
      userId,
      packageId,
      packagePrice,
      packageAdvertQuantity,
    }; // Add userId here
    // Ensure you have a valid session and token
    if (!token) {
      alert("You need to be logged in to create an advert.");
      return;
    }

    setLoading(true);
    try {
      const res = (await buyPackage(dataWithUserId, token)) as Response;
      if (res.status === 200) {
        handleOk();
        enqueueSnackbar("Purchase Successfull !", {
          variant: "success",
        });
        router.push("/packages");
        await getOrdersByUserId(String(userId), token);
      } else if (res.status === 400) {
        enqueueSnackbar("Bad request", {
          variant: "error",
        });
      } else if (res.status === 401) {
        enqueueSnackbar("Unauthorized", {
          variant: "error",
        });
        await signOut();
      } else if (res.status === 404) {
        enqueueSnackbar("You dont have advert right to publish", {
          variant: "error",
        });
      } else {
        enqueueSnackbar("Failed to create advert", {
          variant: "error",
        });
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
    <Modal
      title="Buy Package"
      open={isModalOpen}
      onCancel={handleCancel}
      okButtonProps={{
        onClick: handleSubmit(onSubmit),
      }}
      cancelButtonProps={{
        onClick: handleCancel,
      }}
      className="overflow-auto"
      okText="Buy" // Use handleSubmit here
    >
      <div className="pb-2 pt-4">
        <span className="font-bold">Package Name :</span>{" "}
        <span>{packageInfo?.name}</span>
      </div>
      <div className="pb-2">
        <span className="font-bold">Advert Quantity :</span>{" "}
        <span>{packageInfo?.advertQuantity}</span>
      </div>
      <div className="pb-2">
        <span className="font-bold">Price :</span>{" "}
        <span>{packageInfo?.price} TL</span>
      </div>
      <Form onFinish={handleSubmit(onSubmit)} layout="vertical">
        <div className="w-full">
          <div className="flex flex-col">
            <Controller
              name="packageQuantity"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.packageQuantity?.message}
                  validateStatus={errors.packageQuantity ? "error" : ""}
                  label="Package Quantity"
                >
                  <Select {...field}>
                    <Select.Option value={1}>1</Select.Option>
                    <Select.Option value={5}>5</Select.Option>
                    <Select.Option value={10}>10</Select.Option>
                  </Select>
                </Form.Item>
              )}
            />
          </div>
        </div>
      </Form>
    </Modal>
  );
}
