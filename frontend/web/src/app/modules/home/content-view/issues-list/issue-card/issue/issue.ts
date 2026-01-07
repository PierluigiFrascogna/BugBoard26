import { IUser } from "../../../../../profile/user/user";

export interface INewIssue {
    title: string;
    description: string; 
    type: TIssueType;
    priority:  TIssuePriority;
    state: TIssueState;
}

export interface IIssue extends INewIssue {
    uuid: string;
    author: IUser;
}

export type TIssueType = "QUESTION" | "BUG" | "DOCUMENTATION" | "FEATURE";
export type TIssuePriority = 'LOW' | 'MEDIUM' | 'HIGH'; 
export type TIssueState = 'TODO' | 'PENDING' | 'DONE'; 
 