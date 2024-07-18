"use client";

import { FC, useState } from "react";
import { Pagination } from "antd";
interface Advert {
  id: string;
  title: string;
  description: string;
}

interface AdvertListProps {
  adverts: Advert[];
}
const AdvertList: FC<AdvertListProps> = ({ adverts }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(5); // or any other number you want

  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentAdverts = adverts.slice(startIndex, endIndex);

  return (
    <div className="flex flex-col justify-between h-full">
      <div className="h-full">
        {currentAdverts.map((advert: Advert) => (
          <div key={advert.id}>
            <div>{advert.title}</div>
            <div>{advert.description}</div>
          </div>
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
