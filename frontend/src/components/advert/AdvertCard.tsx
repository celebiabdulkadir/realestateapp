"use client";

import React from "react";
import { Button, Tooltip, Popconfirm, Tag } from "antd";
import {
  CheckCircleOutlined,
  DeleteOutlined,
  EditOutlined,
  ExclamationCircleOutlined,
} from "@ant-design/icons";
import { useSession } from "next-auth/react";
import Image from "next/legacy/image";
import Link from "next/link";
import { CustomUser } from "@/interfaces";
import EditAdvert from "./modal/EditAdvert";
import { deleteAdvertById } from "@/app/lib/deleteAdvert";
import { getAllAdverts } from "@/app/lib/getAllAdverts";
import { useRouter } from "next/navigation";

interface AdvertCardProps {
  advert: {
    id: number;
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
    createDate: string;
    updateDate: string | null;
    advertStatus: string;
  };
}

const AdvertCard: React.FC<AdvertCardProps> = ({ advert }) => {
  const { data: session } = useSession();
  const router = useRouter();

  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [isPopconfirmVisible, setIsPopconfirmVisible] = React.useState(false);

  const openCreateAdvertModal = () => {
    setIsModalOpen(true);
  };
  const handleCancelPopconfirm = () => {
    setIsPopconfirmVisible(false);
  };

  const handleDelete = async () => {
    console.log("Delete");

    await deleteAdvertById(
      advert.id.toString(),
      session && session.user ? (session.user as CustomUser).token : ""
    );

    router.push("/");
    await getAllAdverts();

    setIsPopconfirmVisible(false);
    router.push("/");
  };
  const showPopconfirm = () => {
    setIsPopconfirmVisible(true);
  };

  const closeCreateAdvertModal = () => {
    setIsModalOpen(false);
  };
  return (
    <div className="flex flex-col rounded-lg border-2 p-2  w-72  ">
      <Popconfirm
        title="Title"
        description="Open Popconfirm with async logic"
        open={isPopconfirmVisible}
        onConfirm={handleDelete}
        onCancel={handleCancelPopconfirm}
      ></Popconfirm>
      <div className="relative h-[30svh] sm:h-[35vh]  w-full">
        <Link
          href={{
            pathname: `/advert/${advert?.id}`,
          }}
        >
          <Image
            src="https://picsum.photos/400"
            alt="logo"
            layout="fill"
            objectFit="cover"
            className="rounded-xl drop-shadow-lg"
          />
        </Link>
      </div>
      <h2>{advert.title}</h2>

      {session &&
      session.user &&
      (session.user as CustomUser).userId == advert.userId ? (
        <div className="flex gap-2 justify-end items-center">
          {advert.advertStatus === "ACTIVE" && (
            <Tag icon={<CheckCircleOutlined />} color="success">
              ACTIVE
            </Tag>
          )}
          {advert.advertStatus === "PASSIVE" && (
            <Tag icon={<ExclamationCircleOutlined />} color="warning">
              PASSIVE
            </Tag>
          )}

          {advert.advertStatus === "IN_REVIEW" && (
            <Tag icon={<DeleteOutlined />} color="default">
              IN_REVIEW
            </Tag>
          )}
          <Tooltip title="Delete">
            <Button
              size="small"
              onClick={showPopconfirm}
              danger
              icon={<DeleteOutlined />}
            ></Button>
          </Tooltip>

          <Tooltip title="Edit">
            <Button
              type="primary"
              size="small"
              icon={<EditOutlined />}
              onClick={openCreateAdvertModal}
            ></Button>
          </Tooltip>

          {isModalOpen && (
            <EditAdvert
              advertId={advert.id.toString()}
              isModalOpen={isModalOpen}
              handleCancel={closeCreateAdvertModal}
            ></EditAdvert>
          )}
        </div>
      ) : null}
    </div>
  );
};

export default AdvertCard;
