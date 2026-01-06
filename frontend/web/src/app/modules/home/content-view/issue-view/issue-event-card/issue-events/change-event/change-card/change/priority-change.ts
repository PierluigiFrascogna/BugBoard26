import { TIssuePriority } from "../../../../../../issues-list/issue-card/issue/issue";
import { Change } from "./change";

export interface PriorityChange extends Change{
    changeType: "PRIORITY";
    oldPriority: TIssuePriority; 
    newPriority: TIssuePriority;
}

