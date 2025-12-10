import { Component, Input } from '@angular/core';
import { Issue } from './issue/issue';
import { PriorityLabel } from "./priority-label/priority-label";
import { TypeLabel } from "./type-label/type-label";
import { StateLabel } from "./state-label/state-label";

@Component({
  selector: 'app-view-element',
  imports: [PriorityLabel, TypeLabel, StateLabel],
  templateUrl: './view-element.html',
  styleUrl: './view-element.css',
})
export class ViewElement {
  @Input() issue!: Issue; 
}
