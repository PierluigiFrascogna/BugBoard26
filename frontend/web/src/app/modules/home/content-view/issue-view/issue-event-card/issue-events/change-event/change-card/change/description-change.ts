import { Change } from "./change";

export interface DescriptionChange extends Change{
    changeType: "DESCRIPTION";
    oldDescription: string; 
    newDescription: string; 
}
