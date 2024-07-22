"use client";
import { useState } from "react";
import {
  MoneyCollectOutlined,
  PlusOutlined,
  PlusSquareOutlined,
} from "@ant-design/icons";
import { Button, Tooltip } from "antd";
import { useSession } from "next-auth/react";
import { set } from "react-hook-form";
import PurchasePackageModal from "@/components/packages/modal/PurchasePackage";

export default function PackagePurchaseButton() {
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
        <Tooltip title="Buy Package">
          <Button
            type="primary"
            icon={<MoneyCollectOutlined />}
            onClick={openCreateAdvertModal}
          >
            Buy Package
          </Button>
        </Tooltip>
      )}
      {isModalOpen && (
        <PurchasePackageModal
          isModalOpen={isModalOpen}
          handleCancel={closeCreateAdvertModal}
        />
      )}
    </>
  );
}
