import { Component, Input } from '@angular/core';
import { PriorityChange } from '../change/priority-change';

@Component({
  selector: 'app-priority-change-card',
  imports: [],
  templateUrl: './priority-change-card.html',
  styleUrl: './priority-change-card.css',
})
export class PriorityChangeCard {
  @Input() priorityChange!: PriorityChange;
}
