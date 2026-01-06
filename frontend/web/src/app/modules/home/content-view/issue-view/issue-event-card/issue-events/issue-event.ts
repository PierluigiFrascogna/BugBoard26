import { IUser } from "../../../../../profile/user/user";

export interface IssueEvent {
    uuid: string;
    createdAt: string; 
    type: "COMMENT" | "CHANGE"; 
    author: IUser
}
