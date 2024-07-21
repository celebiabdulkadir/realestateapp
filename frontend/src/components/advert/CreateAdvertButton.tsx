"use client";
import { useState } from "react";
import { PlusOutlined, PlusSquareOutlined } from "@ant-design/icons";
import { Button, Tooltip } from "antd";
import { useSession } from "next-auth/react";
import { set } from "react-hook-form";
import CreateAdvertModal from "@/components/advert/modal/CreateAdvert";

export default function CreateAdvertButton() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const { data: session } = useSession();
  const openCreateAdvertModal = () => {
    setIsModalOpen(true);
  };

  const closeCreateAdvertModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      {session && (
        <Tooltip title="Create Advert">
          <Button
            type="primary"
            icon={<PlusSquareOutlined />}
            onClick={openCreateAdvertModal}
          >
            Create Advert
          </Button>
        </Tooltip>
      )}
      {isModalOpen && (
        <CreateAdvertModal
          isModalOpen={isModalOpen}
          handleCancel={closeCreateAdvertModal}
        />
      )}
    </>
  );
}
