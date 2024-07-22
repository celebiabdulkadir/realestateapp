"use client";

import React, { useEffect } from "react";

import { Avatar, Button, Layout, Popover, theme } from "antd";
import Link from "next/link";
import Image from "next/image";
import { UserOutlined } from "@ant-design/icons";
import { deleteCookie } from "@/app/lib/actions";
import { useRouter } from "next/navigation";
import { getTokenFromCookie } from "@/utils";
import { useSession } from "next-auth/react";
import { signOut } from "next-auth/react";
import { enqueueSnackbar } from "notistack";

const Header = () => {
  const { data: session, status } = useSession();
  const router = useRouter();
  const [popoverVisible, setPopoverVisible] = React.useState(false);

  const logoutUser = async () => {
    await signOut();
    enqueueSnackbar("Logout successful", { variant: "success" });

    // Do something
  };

  const content = (
    <div className="flex flex-col w-full">
      <Link href={"/packages"} type="text">
        My Packages
      </Link>

      <Link href={"/login"} type="text">
        Logout
      </Link>
    </div>
  );

  const {
    token: { colorBgContainer },
  } = theme.useToken();
  return (
    <Layout.Header
      className="sticky flex  justify-between  items-center top-0 bg-white z-50"
      style={{
        padding: 0,
        background: colorBgContainer,
      }}
    >
      <Link href={"/"} className="text-left" type="text">
        <Image src="/house.png" alt="logo" width={40} height={20} />
      </Link>
      <div className="flex gap-2 items-center mr-4  ">
        <>
          {!session ? (
            <Button
              onClick={() => router.push("/login")}
              type="primary"
              className="text-left "
            >
              Login
            </Button>
          ) : (
            <>
              <div className="block sm:hidden">
                <Popover
                  content={content}
                  trigger="click"
                  open={popoverVisible}
                  onOpenChange={setPopoverVisible}
                >
                  <Avatar
                    className="cursor-pointer"
                    // size="small"
                    icon={<UserOutlined />}
                  />
                </Popover>
              </div>

              <div className="hidden sm:block">
                <Button
                  onClick={() => router.push("/packages")}
                  type="primary"
                  className="text-left  "
                >
                  Packages
                </Button>
              </div>
              <div className="hidden sm:block">
                <Button
                  onClick={logoutUser}
                  type="primary"
                  className="text-left hidden sm:block"
                >
                  Logout
                </Button>
              </div>
            </>
          )}
        </>
      </div>
    </Layout.Header>
  );
};

export default Header;
