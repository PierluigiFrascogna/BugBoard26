import { NgClass } from '@angular/common';
import { Component, Input } from '@angular/core';
import { TIssueState } from '../issue/issue';

@Component({
  selector: 'app-state-label',
  imports: [NgClass],
  templateUrl: './state-label.html',
  styleUrl: './state-label.css',
})
export class StateLabel {
  @Input() state!: TIssueState | undefined;
}
