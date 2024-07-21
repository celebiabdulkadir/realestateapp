"use client";

import { SessionProvider } from "next-auth/react";
import React, { useEffect, useState } from "react";
import { Session } from "next-auth";
import { set } from "react-hook-form";

interface CustomSessionProviderProps {
  children: React.ReactNode;
  initialSession: Session | null;
}

export const CustomSessionProvider: React.FC<CustomSessionProviderProps> = ({
  children,
  initialSession,
}) => {
  return <SessionProvider session={initialSession}>{children}</SessionProvider>;
};
