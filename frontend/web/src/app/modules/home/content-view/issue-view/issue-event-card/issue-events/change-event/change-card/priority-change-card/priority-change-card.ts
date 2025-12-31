import { Component, Input } from '@angular/core';
import { PriorityChange } from '../change/priority-change';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-priority-change-card',
  imports: [DatePipe],
  templateUrl: './priority-change-card.html',
  styleUrl: './priority-change-card.css',
})
export class PriorityChangeCard {
  @Input() priorityChange!: PriorityChange;
}
