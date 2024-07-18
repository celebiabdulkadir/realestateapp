"use client";

import React from "react";

import { Avatar, Button, Layout, Popover, theme } from "antd";
import Link from "next/link";
import Image from "next/image";
import { UserOutlined } from "@ant-design/icons";
import { deleteCookie } from "@/app/lib/actions";

const Header = ({ token }: { token: any }) => {
  const [popoverVisible, setPopoverVisible] = React.useState(false);
  const logoutUser = () => {
    deleteCookie("token");
    // Do something
  };
  const content = (
    <div className="flex flex-col w-full">
      <span></span>
      <Link href={"/profile"} className="text-left" type="text">
        Profile
      </Link>

      <Link href={"/packages"} onClick={logoutUser} type="text">
        My Packages
      </Link>
      <Link href={"/"} onClick={logoutUser} type="text">
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
      <div className="flex gap-2 items-center mr-4">
        <>
          {!token ? (
            <Button type="primary" className="text-left">
              <Link href={"/login"}>Login</Link>
            </Button>
          ) : (
            <>
              <Popover
                className="merzigo-popover"
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

              <Button onClick={logoutUser} type="primary" className="text-left">
                <Link href={"/"}>Logout</Link>
              </Button>
            </>
          )}
        </>
      </div>
    </Layout.Header>
  );
};

export default Header;
