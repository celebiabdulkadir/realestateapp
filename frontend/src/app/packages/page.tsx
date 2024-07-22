import React from "react";
import { getOrdersByUserId } from "@/app/lib/getOrdersByUserId";
import { getAvailableAdvertRight } from "@/app/lib/getAvailableAdvertRights";
import { getServerSession } from "next-auth";
import { CustomUser } from "@/interfaces";
import { authOptions } from "../api/auth/[...nextauth]/route";
import { signOut } from "next-auth/react";
import { redirect } from "next/navigation";
import PackagePurchaseButton from "@/components/packages/PackagePurchaseButton";

interface Order {
  id: number;
  userId: number;
  packageId: number;
  packageQuantity: number;
  packagePrice: number;
  packageAdvertQuantity: number;
  advertCount: number;
  totalPrice: number;
  paymentAmount: number;
  paymentId: number;
  paymentDate: string;
  expiryDate: string;
  orderDate: string;
  updateDate: string;
  orderStatus: string;
  paymentStatus: string;
}

const Page = async () => {
  const session = await getServerSession(authOptions);
  console.log("session ın page", session);
  if (!session || !session.user) {
    console.log("No session found, redirecting to login");
    return redirect("/login");
  }

  try {
    const orders = await getOrdersByUserId(
      (session?.user as CustomUser)?.userId?.toString(),
      (session?.user as CustomUser)?.token
    );

    const jsonOrders = await orders.json();

    const availableRights = await getAvailableAdvertRight(
      (session?.user as CustomUser)?.userId?.toString(),
      (session?.user as CustomUser)?.token
    );
    const jsonAvailableRights = await availableRights.text();
    if (orders.status === 401 || availableRights.status === 401) {
      console.log("Unauthorized");
      redirect("/login");
    }

    return (
      <div className="p-2 flex flex-col gap-2">
        <h1 className="text-center font-bold text-lg">My Packages</h1>

        {jsonAvailableRights && (
          <div className="flex justify-between">
            <div>
              <h2 className="pr-6 ">
                <span className="font-bold">Available Rights :</span>{" "}
                <span>{jsonAvailableRights}</span>
              </h2>
            </div>
            <PackagePurchaseButton />
          </div>
        )}
        <ul className="flex flex-wrap gap-2 ">
          {jsonOrders &&
            jsonOrders.map((order: Order) => (
              <li className="border-2 rounded-md p-2 max-w-sm" key={order.id}>
                <p>
                  Order Date:{" "}
                  {new Date(order.orderDate).toLocaleDateString("tr-TR")}
                </p>
                <p>
                  Expiry Date:{" "}
                  {new Date(order.expiryDate).toLocaleDateString("tr-TR")}
                </p>
                <p>Order Status: {order.orderStatus}</p>
                <p>Payment Status: {order.paymentStatus}</p>
                <p>Total Price: {order.totalPrice} TL</p>
                <p>Payment Amount: {order.paymentAmount}</p>

                <p>Package ID: {order.packageId}</p>
                <p>Package Quantity: {order.packageQuantity}</p>
                <p>Package Price: {order.packagePrice}</p>
                <p>Package Advert Quantity: {order.packageAdvertQuantity}</p>
                <p>Advert Count: {order.advertCount}</p>
              </li>
            ))}
        </ul>
      </div>
    );
  } catch (error) {
    console.error("Error fetching data:", error);
    return <div>Error loading data.</div>;
  }
};

export default Page;
