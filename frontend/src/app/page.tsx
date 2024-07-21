import { getCookieValue } from "./lib/actions";
import { getAllAdverts } from "./lib/getAllAdverts";
import AdvertList from "@/components/Advertlist";

export default async function Home() {
  const res = await getAllAdverts();
  const adverts = await (res as Response).json();
  return (
    <main className="flex min-h-screen flex-col   items-center  ">
      <div>Home page</div>
      <div className="h-full">
        <AdvertList adverts={adverts} />
      </div>
    </main>
  );
}
