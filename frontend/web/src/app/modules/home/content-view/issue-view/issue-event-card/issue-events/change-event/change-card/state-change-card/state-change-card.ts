import { Component, Input } from '@angular/core';
import { StateChange } from '../change/state-change';
import { DatePipe } from '@angular/common';
import { StateLabel } from "../../../../../../issues-list/issue-card/state-label/state-label";

@Component({
  selector: 'app-state-change-card',
  imports: [DatePipe, StateLabel],
  templateUrl: './state-change-card.html',
  styleUrl: './state-change-card.css',
})
export class StateChangeCard {
  @Input() stateChange!: StateChange;
}
