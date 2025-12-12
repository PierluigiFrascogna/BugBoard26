import { IssueEvent } from "../../../issue-event";

export interface Change extends IssueEvent {
    type: "CHANGE"
    changeType: "TITLE" | "DESCRIPTION" | "PRIORITY" | "STATE" 
}
