import { TIssueState } from "../../../../../../issues-list/issue-card/issue/issue";
import { Change } from "./change";

export interface StateChange extends Change {
    changeType: "STATE";
    oldState: TIssueState;
    newState: TIssueState;
}
