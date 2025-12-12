import { Component, Input } from '@angular/core';
import { ChangeCard } from "./issue-events/change-event/change-card/change-card";
import { CommentCard } from "./issue-events/comment-event/comment-card/comment-card";
import { TIssueEvent } from './issue-events/issue-event-model';

@Component({
  selector: 'app-issue-event-card',
  imports: [ChangeCard, CommentCard],
  templateUrl: './issue-event-card.html',
  styleUrl: './issue-event-card.css',
})
export class IssueEventCard {
  @Input() issueEvent!: TIssueEvent;
  
}
