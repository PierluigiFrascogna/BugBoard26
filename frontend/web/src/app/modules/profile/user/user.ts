export interface INewUser{
    name: string; 
    surname: string; 
    email: string;
    password: string; 
    role: TUserRole;
}

export interface IUser extends INewUser{
    uuid: string | null;  
}

export interface IUserUpdate{
    email?: string;
    password?: string;
}

export type TUserRole = "viewer" | "developer" | "admin";

