"use client";

import * as React from "react";
import { signOut, useSession } from "next-auth/react";
import {
  Button,
  Form,
  Input,
  InputNumber,
  Modal,
  Radio,
  Select,
  Tag,
} from "antd";
import { enqueueSnackbar } from "notistack";
import { Controller, set, useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { useRouter } from "next/navigation";
import { createAdvert } from "@/app/lib/createAdvert";
import Loading from "@/app/loading"; // Ensure you have a Loading component
import { CustomUser } from "@/interfaces";
import { ToastNotification } from "@/providers/CustomNotificationProvider";
import {
  AreaChartOutlined,
  CheckCircleOutlined,
  CloseCircleOutlined,
  MoneyCollectFilled,
  TruckFilled,
} from "@ant-design/icons";
import { editAdvert } from "@/app/lib/editAdvert";
import { getAdvertById } from "@/app/lib/getAdvertById";
import { getAllAdverts } from "@/app/lib/getAllAdverts";

interface FormValues {
  userId: number;
  title: string;
  description: string;
  address: string;
  price: number;
  area: number;
  room: number;
  floor: number;
  totalFloor: number;
  heating: string;
  balcony: boolean;
  elevator: boolean;
  fromHomeOwner: boolean;
  fromAgency: boolean;
  furnished: boolean;
  credit: boolean;
  swap: boolean;
  advertType: string;
  advertStatus: string;
}
type FormValuesWitoutUserId = Omit<FormValues, "userId">;

export default function EditAdvert({
  isModalOpen,
  handleCancel,
  advertId,
}: {
  isModalOpen: boolean;
  handleCancel: () => void;
  advertId: string;
}) {
  const [loading, setLoading] = React.useState(false);
  const [isNotificationOpen, setIsNotificationOpen] = React.useState(false);
  const router = useRouter();
  const { data: session } = useSession();
  React.useEffect(() => {
    getAdvertByIdDetail(advertId);
  }, [advertId]);

  const getAdvertByIdDetail = async (id: string) => {
    const res = await getAdvertById(id);
    const jsonAdvert = await (res as Response).json();
    setValue("title", jsonAdvert.title);
    setValue("description", jsonAdvert.description);
    setValue("address", jsonAdvert.address);
    setValue("price", jsonAdvert.price);
    setValue("area", jsonAdvert.area);
    setValue("room", jsonAdvert.room);
    setValue("floor", jsonAdvert.floor);
    setValue("totalFloor", jsonAdvert.totalFloor);
    setValue("heating", jsonAdvert.heating);
    setValue("balcony", jsonAdvert.balcony);
    setValue("elevator", jsonAdvert.elevator);
    setValue("fromHomeOwner", jsonAdvert.fromHomeOwner);
    setValue("fromAgency", jsonAdvert.fromAgency);
    setValue("furnished", jsonAdvert.furnished);
    setValue("credit", jsonAdvert.credit);
    setValue("swap", jsonAdvert.swap);
    setValue("advertType", jsonAdvert.advertType);
    setValue("advertStatus", jsonAdvert.advertStatus);
  };

  const schema = yup.object().shape({
    title: yup.string().trim().required("Title is required"),
    description: yup.string().trim().required("Description is required"),
    address: yup
      .string()
      .required("Address is required")
      .min(10, "Address must be at least 10 characters")
      .max(100, "Address can be at most 100 characters"),
    price: yup.number().required("Price is required"),
    area: yup.number().required("Area is required"),
    room: yup.number().required("Room is required"),
    floor: yup.number().required("Floor is required"),
    totalFloor: yup.number().required("Total Floor is required"),
    heating: yup.string().required("Heating is required"),
    balcony: yup.boolean().required("Balcony is required"),
    elevator: yup.boolean().required("Elevator is required"),
    fromHomeOwner: yup.boolean().required("From Home Owner is required"),
    fromAgency: yup.boolean().required("From Agency is required"),
    furnished: yup.boolean().required("Furnished is required"),
    credit: yup.boolean().required("Credit is required"),
    swap: yup.boolean().required("Swap is required"),
    advertType: yup.string().required("Advert Type is required"),
    advertStatus: yup.string().required("Advert Status is required"),
  });

  const {
    control,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<FormValuesWitoutUserId>({
    resolver: yupResolver(schema),
  });

  const handleOk = () => {
    handleCancel();
  };
  const onSubmit = async (formData: FormValuesWitoutUserId) => {
    const token = (session?.user as CustomUser).token;
    const userId = Number((session?.user as CustomUser).userId); // Ensure you have a valid session and token
    const dataWithUserId = { ...formData, userId }; // Add userId here
    if (!token) {
      alert("You need to be logged in to create an advert.");
      return;
    }

    setLoading(true);
    try {
      const res = (await editAdvert(
        dataWithUserId,
        token,
        advertId
      )) as Response;
      if (res.status === 200) {
        handleOk();
        await getAllAdverts();
        router.push("/");

        enqueueSnackbar("Advert Updated !", {
          variant: "success",
        });
      } else if (res.status === 401) {
        await signOut();
      } else if (res.status === 400) {
        enqueueSnackbar("Bad request", {
          variant: "error",
        });
      } else if (res.status === 409) {
        enqueueSnackbar("Advert already exists", {
          variant: "error",
        });
      } else {
        enqueueSnackbar("Error while updating advert", {
          variant: "error",
        });
      }
    } catch (error) {
      console.log(error);
      enqueueSnackbar("Error while updating advert", {
        variant: "error",
      });
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <>
      <Modal
        title="Create Advert"
        open={isModalOpen}
        onCancel={handleCancel}
        okButtonProps={{
          onClick: handleSubmit(onSubmit),
        }}
        cancelButtonProps={{
          onClick: handleCancel,
        }}
        className="overflow-auto"
        okText="Update" // Use handleSubmit here
      >
        <Form onFinish={handleSubmit(onSubmit)} layout="vertical">
          <div className="w-full "></div>
          <div className="flex flex-col">
            <Controller
              name="title"
              control={control}
              render={({ field }) => (
                <Form.Item
                  label={"Title"}
                  help={errors.title?.message}
                  validateStatus={errors.title ? "error" : ""}
                >
                  <Input
                    className="dark:bg-gray-500"
                    placeholder={"Title"}
                    {...field}
                  />
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="description"
              control={control}
              render={({ field }) => (
                <Form.Item
                  label={"Description"}
                  help={errors.description?.message}
                  validateStatus={errors.description ? "error" : ""}
                >
                  <Input
                    className="dark:bg-gray-500"
                    placeholder={"Description"}
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
                    addonBefore={<TruckFilled />}
                    placeholder={"Adress"}
                    {...field}
                  />
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="price"
              control={control}
              render={({ field }) => (
                <Form.Item
                  label={"Price"}
                  help={errors.price?.message}
                  validateStatus={errors.price ? "error" : ""}
                >
                  <InputNumber
                    addonBefore={<MoneyCollectFilled />}
                    className=" w-full"
                    placeholder={"Price"}
                    {...field}
                  />
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="area"
              control={control}
              render={({ field }) => (
                <Form.Item
                  label={"Area (m²)"}
                  help={errors.area?.message}
                  validateStatus={errors.area ? "error" : ""}
                >
                  <InputNumber
                    addonBefore={<AreaChartOutlined />}
                    className=" w-full"
                    placeholder={"Area"}
                    {...field}
                  />
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="room"
              control={control}
              render={({ field }) => (
                <Form.Item
                  label={"Room"}
                  help={errors.room?.message}
                  validateStatus={errors.room ? "error" : ""}
                >
                  <InputNumber
                    addonBefore={<AreaChartOutlined />}
                    className=" w-full"
                    placeholder={"Room"}
                    {...field}
                  />
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="floor"
              control={control}
              render={({ field }) => (
                <Form.Item
                  label={"Floor"}
                  help={errors.floor?.message}
                  validateStatus={errors.floor ? "error" : ""}
                >
                  <InputNumber
                    addonBefore={<AreaChartOutlined />}
                    className=" w-full"
                    placeholder={"Floor"}
                    {...field}
                  />
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="totalFloor"
              control={control}
              render={({ field }) => (
                <Form.Item
                  label={"Total Floor"}
                  help={errors.totalFloor?.message}
                  validateStatus={errors.totalFloor ? "error" : ""}
                >
                  <InputNumber
                    addonBefore={<AreaChartOutlined />}
                    className=" w-full"
                    placeholder={"Total Floor"}
                    {...field}
                  />
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="heating"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.heating?.message}
                  validateStatus={errors.heating ? "error" : ""}
                  label="Heating"
                >
                  <Select {...field}>
                    <Select.Option value="NATURAL_GAS">
                      NATURAL_GAS
                    </Select.Option>
                    <Select.Option value="COMBI">COMBI</Select.Option>
                    <Select.Option value="CENTRAL">CENTRAL</Select.Option>
                    <Select.Option value="ELECTRIC">ELECTRIC</Select.Option>
                    <Select.Option value="SOBA">SOBA</Select.Option>
                    <Select.Option value="OTHER">OTHER</Select.Option>
                  </Select>
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="advertType"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.advertType?.message}
                  validateStatus={errors.advertType ? "error" : ""}
                  label="Advert Type"
                >
                  <Select {...field}>
                    <Select.Option value="SALE">SALE</Select.Option>
                    <Select.Option value="RENT">RENT</Select.Option>
                  </Select>
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="balcony"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.balcony?.message}
                  validateStatus={errors.balcony ? "error" : ""}
                  label="Balcony"
                >
                  <Radio.Group
                    {...field}
                    value={field.value ? "true" : "false"}
                  >
                    <Radio value="true"> Yes </Radio>
                    <Radio value="false"> No </Radio>
                  </Radio.Group>
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="elevator"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.elevator?.message}
                  validateStatus={errors.elevator ? "error" : ""}
                  label="Elevator"
                >
                  <Radio.Group
                    {...field}
                    value={field.value ? "true" : "false"}
                  >
                    <Radio value="true"> Yes </Radio>
                    <Radio value="false"> No </Radio>
                  </Radio.Group>
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="fromHomeOwner"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.fromHomeOwner?.message}
                  validateStatus={errors.fromHomeOwner ? "error" : ""}
                  label="From Home Owner"
                >
                  <Radio.Group
                    {...field}
                    value={field.value ? "true" : "false"}
                  >
                    <Radio value="true"> Yes </Radio>
                    <Radio value="false"> No </Radio>
                  </Radio.Group>
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="fromAgency"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.fromAgency?.message}
                  validateStatus={errors.fromAgency ? "error" : ""}
                  label="From Agency"
                >
                  <Radio.Group
                    {...field}
                    value={field.value ? "true" : "false"}
                  >
                    <Radio value="true"> Yes </Radio>
                    <Radio value="false"> No </Radio>
                  </Radio.Group>
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="furnished"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.furnished?.message}
                  validateStatus={errors.furnished ? "error" : ""}
                  label="Furnished"
                >
                  <Radio.Group
                    {...field}
                    value={field.value ? "true" : "false"}
                  >
                    <Radio value="true"> Yes </Radio>
                    <Radio value="false"> No </Radio>
                  </Radio.Group>
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="credit"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.credit?.message}
                  validateStatus={errors.credit ? "error" : ""}
                  label="Credit"
                >
                  <Radio.Group
                    {...field}
                    value={field.value ? "true" : "false"}
                  >
                    <Radio value="true"> Yes </Radio>
                    <Radio value="false"> No </Radio>
                  </Radio.Group>
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="swap"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.swap?.message}
                  validateStatus={errors.swap ? "error" : ""}
                  label="Swap"
                >
                  <Radio.Group
                    {...field}
                    value={field.value ? "true" : "false"}
                  >
                    <Radio value="true"> Yes </Radio>
                    <Radio value="false"> No </Radio>
                  </Radio.Group>
                </Form.Item>
              )}
            />
          </div>
          <div className="flex flex-col">
            <Controller
              name="advertStatus"
              control={control}
              render={({ field }) => (
                <Form.Item
                  help={errors.advertStatus?.message}
                  validateStatus={errors.advertStatus ? "error" : ""}
                  label="Advert Status"
                >
                  <Select {...field}>
                    <Select.Option value="ACTIVE">
                      <Tag icon={<CheckCircleOutlined />} color="success">
                        ACTIVE
                      </Tag>
                    </Select.Option>
                    <Select.Option value="PASSIVE">
                      <Tag icon={<CloseCircleOutlined />} color="error">
                        PASSIVE
                      </Tag>
                    </Select.Option>
                  </Select>
                </Form.Item>
              )}
            />
          </div>
          {/* <div className="flex w-full justify-between gap-4">
          <Form.Item className="w-full">
            <Button className="w-full" type="primary" htmlType="submit">
              Create
            </Button>
          </Form.Item>
        </div> */}
        </Form>
      </Modal>
    </>
  );
}
