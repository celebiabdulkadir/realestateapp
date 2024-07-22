import type { NextRequest } from "next/server";
import { NextResponse } from "next/server";

export function middleware(request: NextRequest) {
  const currentUser = request.cookies.get("user")?.value;

  // Protect the /packages route
  if (request.nextUrl.pathname.startsWith("/packages")) {
    // If the user is not logged in, redirect to the login page
    if (!currentUser) {
      return NextResponse.redirect(new URL("/login", request.url));
    }
  }

  // Allow the request to proceed
  return NextResponse.next();
}

export const config = {
  matcher: ["/packages/:path*"], // Apply this middleware only to the /packages route
};
