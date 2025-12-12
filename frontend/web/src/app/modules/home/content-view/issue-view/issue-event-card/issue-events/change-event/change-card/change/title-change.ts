import { Change } from "./change";

export interface TitleChange extends Change{
    changeType: "TITLE";
    old: string; 
    new: string; 
}
