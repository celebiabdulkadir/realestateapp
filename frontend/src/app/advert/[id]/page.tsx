import { getAdvertById } from "@/app/lib/getAdvertById";

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
        <div className="flex justify-between">
          {" "}
          <h1>{jsonAdvert.title}</h1>
          <p>Price: {jsonAdvert.price}</p>
        </div>
        <div>Address: {jsonAdvert.address}</div>
        <div>Area: {jsonAdvert.area} m²</div>
        <div>Rooms: {jsonAdvert.room}</div>
        <div>Floor: {jsonAdvert.floor}</div>
        <div>Total Floors: {jsonAdvert.totalFloor}</div>
        <div>Heating: {jsonAdvert.heating}</div>
        <div>Balcony: {jsonAdvert.balcony ? "Yes" : "No"}</div>
        <div>Elevator: {jsonAdvert.elevator ? "Yes" : "No"}</div>
        <div>From Home Owner: {jsonAdvert.fromHomeOwner ? "Yes" : "No"}</div>
        <div>From Agency: {jsonAdvert.fromAgency ? "Yes" : "No"}</div>
        <div>Furnished: {jsonAdvert.furnished ? "Yes" : "No"}</div>
        <div>Credit: {jsonAdvert.credit ? "Yes" : "No"}</div>
        <div>Swap: {jsonAdvert.swap ? "Yes" : "No"}</div>
        <div>Advert Type: {jsonAdvert.advertType}</div>
        <div>
          Create Date: {new Date(jsonAdvert.createDate).toLocaleDateString()}
        </div>
        <div>
          Update Date:{" "}
          {jsonAdvert.updateDate
            ? new Date(jsonAdvert.updateDate).toLocaleDateString()
            : "N/A"}
        </div>
        <div>Status: {jsonAdvert.advertStatus}</div>
      </div>
    );
  } catch (error) {
    console.error("Error fetching advert:", error);
    return <div>Error fetching advert: {(error as Error).message}</div>;
  }
};

export default Advert;
