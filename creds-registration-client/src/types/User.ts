import { OrganizationResponse } from "../components/Organization";

export interface UserInfo {
    subjectId: string;
    name: string;
    firstName: string;
    lastName: string;
  }


 export interface DecodedToken {
    name: string;
    email: string;
    given_name: string;
    family_name: string;
  }


  export interface UserResponseInfo {
    id: number
    subjectId: string;
    name: string;
    firstName: string;
    lastName: string;
    organizations: OrganizationResponse[];
  }