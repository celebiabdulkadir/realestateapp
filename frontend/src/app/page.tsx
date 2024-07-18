"use client";

import React, { useEffect } from "react";

export default function Home() {
  useEffect(() => {
    fetchSomething();
  }, []);

  const fetchSomething = async () => {
    fetch("http://localhost:8080/auth/auth/token", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: "abdulkadir", // replace 'your-username' with the actual username
        password: "Aa123456", // replace 'your-password' with the actual password
      }),
    })
      .then((response) => response.json())
      .then((data) => console.log(data))
      .catch((error) => console.error("Error:", error));
  };
  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <div>Home page</div>
    </main>
  );
}
