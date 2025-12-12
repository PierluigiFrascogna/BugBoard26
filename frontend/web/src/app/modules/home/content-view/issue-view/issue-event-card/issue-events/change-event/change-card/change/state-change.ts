import { Change } from "./change";

export interface StateChange extends Change {
    changeType: "STATE";
    old: "todo" | "pending" | "done",
    new: "todo" | "pending" | "done"
}
