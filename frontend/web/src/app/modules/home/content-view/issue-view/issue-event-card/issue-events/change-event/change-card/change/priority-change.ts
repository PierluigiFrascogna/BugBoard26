import { Change } from "./change";

export interface PriorityChange extends Change{
    changeType: "PRIORITY";
    old: "low" | "medium" | "high"; 
    new: "low" | "medium" | "high";
}
