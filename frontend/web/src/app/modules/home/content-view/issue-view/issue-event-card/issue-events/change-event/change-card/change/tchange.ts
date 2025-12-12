import { DescriptionChange } from "./description-change";
import { PriorityChange } from "./priority-change";
import { StateChange } from "./state-change";
import { TitleChange } from "./title-change";

export type TChange = 
    TitleChange
    | DescriptionChange
    | PriorityChange
    | StateChange;