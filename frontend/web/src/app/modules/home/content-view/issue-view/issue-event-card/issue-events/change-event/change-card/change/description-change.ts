import { Change } from "./change";

export interface DescriptionChange extends Change{
    changeType: "DESCRIPTION";
    old: string; 
    new: string; 
}
