import { getAdvertById } from "@/app/lib/getAdvertById";
import Image from "next/legacy/image";

interface AdvertProps {
  params: {
    id: string;
  };
}

const Advert: React.FC<AdvertProps> = async ({ params }) => {
  const { id } = params;

  // Check if id is valid
  if (!id) {
    console.error("ID is undefined or null");
    return <div>Error: ID is invalid</div>;
  }

  try {
    const res = await getAdvertById(id);
    const jsonAdvert = await (res as Response).json();
    console.log("advert", jsonAdvert);

    return (
      <div>
        <div>
          <div className="font-bold px-4  py-4 ">{jsonAdvert.title} TL</div>
        </div>
        <div className="flex flex-col sm:flex-row gap-2 justify-between px-4 w-full">
          <div className="flex justify-center  w-full h-full">
            <div className="w-full flex flex-col">
              <div className="relative sm:h-[60dvh] h-[40dvh]  w-full">
                <Image
                  src="https://picsum.photos/400"
                  alt="logo"
                  layout="fill"
                  objectFit="cover"
                  className="rounded-xl drop-shadow-lg"
                />
              </div>
            </div>
          </div>
          <div className="flex flex-col sm:justify-center sm:flex-row w-full">
            <div className="border-2 rounded-lg p-2">
              <div className="font-bold  py-2 ">{jsonAdvert.price} TL</div>
              <div className="border-b-2 py-2 font-bold">
                {" "}
                {jsonAdvert.address}
              </div>
              <div className="border-b-2 py-2">Area: {jsonAdvert.area} m²</div>
              <div className="border-b-2 py-2">Rooms: {jsonAdvert.room}</div>
              <div className="border-b-2 py-2">Floor: {jsonAdvert.floor}</div>
              <div className="border-b-2 py-2">
                Total Floors: {jsonAdvert.totalFloor}
              </div>
              <div className="border-b-2 py-2">
                Heating: {jsonAdvert.heating}
              </div>
              <div className="border-b-2 py-2">
                Balcony: {jsonAdvert.balcony ? "Yes" : "No"}
              </div>
              <div className="border-b-2 py-2">
                Elevator: {jsonAdvert.elevator ? "Yes" : "No"}
              </div>
              <div className="border-b-2 py-2">
                From Home Owner: {jsonAdvert.fromHomeOwner ? "Yes" : "No"}
              </div>
              <div className="border-b-2 py-2">
                From Agency: {jsonAdvert.fromAgency ? "Yes" : "No"}
              </div>
              <div className="border-b-2 py-2">
                Furnished: {jsonAdvert.furnished ? "Yes" : "No"}
              </div>
              <div className="border-b-2 py-2">
                Credit: {jsonAdvert.credit ? "Yes" : "No"}
              </div>
              <div className="border-b-2 py-2">
                Swap: {jsonAdvert.swap ? "Yes" : "No"}
              </div>
              <div className="border-b-2 py-2">
                Advert Type: {jsonAdvert.advertType}
              </div>
              <div className="border-b-2 py-2">
                Create Date:{" "}
                {new Date(jsonAdvert.createDate).toLocaleDateString()}
              </div>
              <div className=" py-2">
                Update Date:{" "}
                {jsonAdvert.updateDate
                  ? new Date(jsonAdvert.updateDate).toLocaleDateString()
                  : "N/A"}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  } catch (error) {
    console.error("Error fetching advert:", error);
    return <div>Error fetching advert: {(error as Error).message}</div>;
  }
};

export default Advert;
