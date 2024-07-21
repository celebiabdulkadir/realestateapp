"use client";

import React from "react";
import { Button } from "antd";
import { DeleteOutlined, EditOutlined } from "@ant-design/icons";
import { useSession } from "next-auth/react";
import Image from "next/legacy/image";
import Link from "next/link";

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
  onDelete: () => void;
  onEdit: () => void;
}
interface CustomUser {
  userId: number;
  username: string;
  name: string;
  email: string;
  phoneNumber: number;
  address: string;
}

const AdvertCard: React.FC<AdvertCardProps> = ({
  advert,
  onDelete,
  onEdit,
}) => {
  const { data: session } = useSession();
  return (
    <div className="flex flex-col rounded-lg border-2 p-2 ">
      <div className="relative h-[20svh] sm:h-[25vh] lg:h-[30svh] w-full">
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
        <div className="flex justify-end">
          <Button icon={<DeleteOutlined />} onClick={onDelete}></Button>
          <Button icon={<EditOutlined />} onClick={onEdit}></Button>
        </div>
      ) : null}
    </div>
  );
};

export default AdvertCard;
