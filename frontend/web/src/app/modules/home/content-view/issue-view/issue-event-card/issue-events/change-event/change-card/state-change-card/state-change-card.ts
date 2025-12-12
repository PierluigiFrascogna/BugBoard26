import { Component, Input } from '@angular/core';
import { StateChange } from '../change/state-change';

@Component({
  selector: 'app-state-change-card',
  imports: [],
  templateUrl: './state-change-card.html',
  styleUrl: './state-change-card.css',
})
export class StateChangeCard {
  @Input() stateChange!: StateChange;
}
