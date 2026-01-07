import { Change } from "./change";

export interface TitleChange extends Change{
    changeType: "TITLE";
    oldTitle: string; 
    newTitle: string; 
}
