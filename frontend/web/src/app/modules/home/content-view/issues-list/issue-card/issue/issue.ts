export interface INewIssue {
    title: string;
    description: string; 
    type: TIssueType;
    priority:  TIssuePriority;
    state: TIssueState;
    image: string;
    author: string;
}

export interface IIssue extends INewIssue {
    uuid: string;
}

export type TIssueType = "question" | "bug" | "documentation" | "feature";
export type TIssuePriority = 'low' | 'medium' | 'high'; 
export type TIssueState = 'todo' | 'pending' | 'done'; 
 