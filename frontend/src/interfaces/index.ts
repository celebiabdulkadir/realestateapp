import { AdapterUser } from "next-auth/adapters";

export interface CustomUser extends AdapterUser {
  userId: number;
  username: string;
  name: string;
  email: string;
  token: string;
}
