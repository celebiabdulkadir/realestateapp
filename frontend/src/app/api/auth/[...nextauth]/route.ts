import NextAuth, { Session } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import { NextAuthOptions } from "next-auth";
import { signIn } from "@/auth/signIn";
import { CustomUser } from "@/interfaces";

interface Inputs {
  username: string;
  password: string;
}

interface CustomSession extends Session {
  user: CustomUser;
}

export const authOptions: NextAuthOptions = {
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: {
        username: { label: "username", type: "text" },
        password: { label: "password", type: "password" },
      },
      async authorize(credentials: Inputs | undefined, req: any) {
        const res = await signIn(credentials || { username: "", password: "" });
        const user = await (res as Response).json();

        if (res && res.ok && user) {
          return user;
        } else {
          return null;
        }
      },
    }),
  ],
  pages: {
    signIn: "/login",
  },
  callbacks: {
    async jwt({ token, user, account }) {
      if (user) {
        token.user = {
          userId: (user as CustomUser).userId,
          username: (user as CustomUser).username,
          name: (user as CustomUser).name,
          email: (user as CustomUser).email,
          token: (user as CustomUser).token,
        };
      }
      return token;
    },
    async session({ session, token }) {
      if (token.user) {
        session.user = token.user as CustomUser;
      }
      return session as CustomSession;
    },
  },
  session: {
    strategy: "jwt",
    maxAge: 60 * 60, // 1 hour in seconds
  },
  secret: process.env.NEXTAUTH_SECRET,
};

const handler = NextAuth(authOptions);

export { handler as GET, handler as POST };
