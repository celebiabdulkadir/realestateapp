"use client";

import { SessionProvider } from "next-auth/react";
import React, { useEffect, useState } from "react";
import { Session } from "next-auth";
import { set } from "react-hook-form";
import { SnackbarProvider } from "notistack";

interface CustomSessionProviderProps {
  children: React.ReactNode;
  initialSession: Session | null;
}

export const CustomSessionProvider: React.FC<CustomSessionProviderProps> = ({
  children,
  initialSession,
}) => {
  return (
    <SnackbarProvider
      autoHideDuration={3000}
      anchorOrigin={{ vertical: "top", horizontal: "right" }}
    >
      <SessionProvider session={initialSession}>{children}</SessionProvider>;
    </SnackbarProvider>
  );
};
