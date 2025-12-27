export interface Issue {
    uuid: string; 
    title: string; 
    description: string; 
    type: IssueType;
    priority:  IssuePriority;
    state: IssueState;
    image: string;
    author: string;
}

export type IssueType = "question" | "bug" | "documentation" | "feature";
export type IssuePriority = 'low' | 'medium' | 'high' | undefined; 
export type IssueState = 'todo' | 'pending' | 'done'; 
 