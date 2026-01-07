import { Component, Input } from '@angular/core';
import { PriorityChange } from '../change/priority-change';
import { DatePipe } from '@angular/common';
import { PriorityLabel } from "../../../../../../issues-list/issue-card/priority-label/priority-label";

@Component({
  selector: 'app-priority-change-card',
  imports: [DatePipe, PriorityLabel],
  templateUrl: './priority-change-card.html',
  styleUrl: './priority-change-card.css',
})
export class PriorityChangeCard {
  @Input() priorityChange!: PriorityChange;
}
