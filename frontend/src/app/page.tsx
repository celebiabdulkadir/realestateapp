import { Tooltip } from "antd";
import { getCookieValue } from "./lib/actions";
import { getAllAdverts } from "./lib/getAllAdverts";
import AdvertList from "@/components/advert/Advertlist";
import CreateAdvertButton from "@/components/advert/CreateAdvertButton";
import { getServerSession } from "next-auth/next";
import { authOptions } from "./api/auth/[...nextauth]/route";
export default async function Home() {
  const session = await getServerSession(authOptions);
  console.log("session", session);
  const res = await getAllAdverts();
  const adverts = await (res as Response).json();
  return (
    <main className="flex min-h-screen flex-col   items-center gap-4  ">
      <div className="py-4 flex justify-between w-full px-4">
        <h1>Home page</h1>
        <CreateAdvertButton />
      </div>
      <div className="h-full">
        <AdvertList adverts={adverts} />
      </div>
    </main>
  );
}
