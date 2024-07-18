import { cookies } from "next/headers";
import Button from "@/components/Button";
import { getAllAdverts } from "./lib/getAllAdverts";
import AdvertList from "@/components/Advertlist";

export default async function Home() {
  const adverts = await getAllAdverts();

  const jsonedadverts = await (adverts as Response).json();
  console.log("adverts", adverts);
  const cookieStore = cookies();
  const token = cookieStore.get("token");

  return (
    <main className="flex min-h-screen flex-col  items-center  p-24">
      <div>Home page</div>
      <div className="h-full">
        <AdvertList adverts={jsonedadverts} />
      </div>
    </main>
  );
}
