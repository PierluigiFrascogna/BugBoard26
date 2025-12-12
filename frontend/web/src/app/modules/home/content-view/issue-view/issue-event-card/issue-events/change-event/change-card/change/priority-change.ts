import { Change } from "./change";

export interface PriorityChange extends Change{
    changeType: "PRIORITY";
    old: "low" | "medium" | "high" | undefined,
    new: "low" | "medium" | "high" | undefined,
}
