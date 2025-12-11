export interface IssueEvent {
    uuid: string;
    createdAt: Date; 
    type: "COMMENT" | "CHANGE"; 
    authorUuid: string
}
