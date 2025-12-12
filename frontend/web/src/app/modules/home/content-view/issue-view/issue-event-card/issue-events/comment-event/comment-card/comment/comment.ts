import { IssueEvent } from "../../../issue-event";

export interface Comment extends IssueEvent {
    type: "COMMENT";
    text: string;
}
