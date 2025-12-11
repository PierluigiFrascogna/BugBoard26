import { Component, Input } from '@angular/core';
import { IssueEvent } from './issue-events/issue-event';

@Component({
  selector: 'app-issue-event-card',
  imports: [],
  templateUrl: './issue-event-card.html',
  styleUrl: './issue-event-card.css',
})
export class IssueEventCard {
  @Input() issueEvent!: IssueEvent;

  
}
