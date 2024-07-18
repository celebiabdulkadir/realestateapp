"use client";
import React from "react";

interface ErrorProps {
  message: string;
}

const Error: React.FC<ErrorProps> = ({ message }) => {
  return (
    <div className="min-h-dvh">
      <h2>Error</h2>
      <p>{message}</p>
    </div>
  );
};

export default Error;
