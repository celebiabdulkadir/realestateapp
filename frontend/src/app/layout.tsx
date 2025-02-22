import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import React, { Suspense } from "react";
import { AntdRegistry } from "@ant-design/nextjs-registry";
import { ConfigProvider, Layout } from "antd";
import theme from "@/theme/themeConfig";
import Footer from "@/components/layout/Footer";
import Header from "@/components/layout/Header";
import Loading from "./loading";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/authOptions";
import { CustomSessionProvider } from "@/providers/CustomSessionProvider";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Create Next App",
  description: "Generated by create next app",
};

const RootLayout = async ({ children }: React.PropsWithChildren) => {
  const session = await getServerSession(authOptions);

  return (
    <html lang="en">
      <body>
        <ConfigProvider theme={theme}>
          <CustomSessionProvider initialSession={session}>
            <Suspense fallback={<Loading />}>
              <Layout className="overflow-y-auto min-h-dvh relative">
                <AntdRegistry>
                  <Header />
                  <div className="min-h-screen">{children}</div>
                  <Footer />
                </AntdRegistry>
              </Layout>
            </Suspense>
          </CustomSessionProvider>
        </ConfigProvider>
      </body>
    </html>
  );
};

export default RootLayout;
