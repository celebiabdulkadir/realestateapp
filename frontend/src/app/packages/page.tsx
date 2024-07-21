import React from "react";
import { getOrdersByUserId } from "@/app/lib/getOrdersByUserId";
import { getAvailableAdvertRight } from "@/app/lib/getAvailableAdvertRights";
import { getServerSession } from "next-auth";
import { CustomUser } from "@/interfaces";
import { authOptions } from "../api/auth/[...nextauth]/route";
import { signOut } from "next-auth/react";
import { redirect } from "next/navigation";

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
  console.log("session", session);

  try {
    const orders = await getOrdersByUserId(
      (session?.user as CustomUser)?.userId?.toString(),
      (session?.user as CustomUser)?.token
    );

    const availableRights = await getAvailableAdvertRight(
      (session?.user as CustomUser)?.userId?.toString(),
      (session?.user as CustomUser)?.token
    );

    if (orders.status === 401 || availableRights.status === 401) {
      console.log("Unauthorized");
      redirect("/login");
    }

    return (
      <div>
        <div>
          <h1>My Orders</h1>
          <h2>Available Rights</h2>
          {availableRights && <div>{availableRights}</div>}
          <ul className="flex flex-wrap gap-2 ">
            {Array.isArray(orders) &&
              orders.map((order: Order) => (
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
      </div>
    );
  } catch (error) {
    console.error("Error fetching data:", error);
    return <div>Error loading data.</div>;
  }
};

export default Page;
