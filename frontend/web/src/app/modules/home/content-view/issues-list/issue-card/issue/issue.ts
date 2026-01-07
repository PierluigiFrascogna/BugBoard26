import { IUser } from "../../../../../profile/user/user";

export interface INewIssue {
    title: string;
    description: string; 
    type: TIssueType;
    priority:  TIssuePriority;
    state: TIssueState;
    author: IUser;
}

export interface IIssue extends INewIssue {
    uuid: string;
}

export type TIssueType = "question" | "bug" | "documentation" | "feature";
export type TIssuePriority = 'low' | 'medium' | 'high'; 
export type TIssueState = 'todo' | 'pending' | 'done'; 
 