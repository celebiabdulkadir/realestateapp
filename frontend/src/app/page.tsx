import { Tooltip } from "antd";
import { getCookieValue } from "./lib/actions";
import { getAllAdverts } from "./lib/getAllAdverts";
import AdvertList from "@/components/advert/Advertlist";
import CreateAdvertButton from "@/components/advert/CreateAdvertButton";
import { getServerSession } from "next-auth/next";
import { authOptions } from "./api/auth/[...nextauth]/authOptions";
import { CustomUser } from "@/interfaces";
import { debug } from "console";
export default async function Home() {
  const session = await getServerSession(authOptions);
  const currentUser = session?.user as CustomUser;
  const res = await getAllAdverts();
  console.log(res);
  const adverts = await (res as Response).json();
  return (
    <main className="flex min-h-screen flex-col   items-center gap-2  ">
      <h1 className="font-bold text-center pt-4">Adverts</h1>
      <div className="py-4 flex justify-end w-full px-4">
        <CreateAdvertButton />
      </div>
      <div className="h-full">
        <AdvertList adverts={adverts} />
      </div>
    </main>
  );
}
