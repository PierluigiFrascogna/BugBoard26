import { Component, Input } from '@angular/core';
import { IssueEvent } from './issue-events/issue-event';
import { ChangeCard } from "./issue-events/change-event/change-card/change-card";
import { CommentCard } from "./issue-events/comment-event/comment-card/comment-card";

@Component({
  selector: 'app-issue-event-card',
  imports: [ChangeCard, CommentCard],
  templateUrl: './issue-event-card.html',
  styleUrl: './issue-event-card.css',
})
export class IssueEventCard {
  @Input() issueEvent!: IssueEvent;

  prova(){
    console.log(this.issueEvent);
  }
  
}
