"use client";

import { FC, useEffect, useState } from "react";
import { Button, Card, Pagination } from "antd";
import Meta from "antd/es/card/Meta";
import Link from "next/link";
import { getTokenFromCookie } from "@/utils";
import AdvertCard from "./AdvertCard";
import { set } from "react-hook-form";
interface Advert {
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
}

interface AdvertListProps {
  adverts: Advert[];
}
const AdvertList: FC<AdvertListProps> = ({ adverts }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10); // or any other number you want

  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentAdverts = adverts.slice(startIndex, endIndex);

  return (
    <div className="flex flex-col w-full  h-full">
      <div className="h-full w-full flex flex-wrap gap-4 ">
        {currentAdverts.map((advert: Advert) => (
          <AdvertCard
            advert={advert}
            key={advert.id}
            onDelete={() => {
              console.log("delete");
            }}
            onEdit={() => {
              console.log("edit");
            }}
          />
        ))}
      </div>
      <div className="h-full">
        <Pagination
          defaultCurrent={1}
          total={adverts.length}
          pageSize={itemsPerPage}
          current={currentPage}
          onChange={setCurrentPage}
        />
      </div>
    </div>
  );
};

export default AdvertList;
