"use client";
import { Layout, theme } from "antd";

const Footer = () => {
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  return (
    <Layout.Footer
      style={{
        background: colorBgContainer,
      }}
      className="!py-4 mt-5 shadow-md w-full flex flex-col md:flex-row justify-between items-center"
    >
      <span>
        {/* eslint-disable react/jsx-no-target-blank */}
        <a
          className="!text-primary font-bold md:text-lg"
          href="https://www.fmss.com.tr/tr"
          target="_blank"
        >
          Real Estate
        </a>
        <span className="!text-gray-500">|</span>{" "}
      </span>{" "}
      <span>All rights reserved &copy; {new Date().getFullYear()}</span>
    </Layout.Footer>
  );
};

export default Footer;
