export interface IUserBase {
    name: string;
    surname: string;
    email: string;
    role: TUserRole;
}

export interface INewUser extends IUserBase {
    password: string;
}

export interface IUser extends IUserBase {
    uuid: string;
}

export interface IUserUpdate{
    email?: string;
    password?: string;
}

export type TUserRole = "VIEWER" | "DEVELOPER" | "ADMIN";

