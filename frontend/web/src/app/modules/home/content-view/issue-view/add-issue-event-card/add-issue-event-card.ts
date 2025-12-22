import { Component, signal } from '@angular/core';
import { AddCommentCard } from "./add-comment-card/add-comment-card";
import { AddChangeCard } from "./add-change-card/add-change-card";

@Component({
  selector: 'app-add-issue-event-card',
  imports: [AddCommentCard, AddChangeCard],
  templateUrl: './add-issue-event-card.html',
  styleUrl: './add-issue-event-card.css',
})
export class AddIssueEventCard {
  readonly visibleCard = signal<"comment" | "change">("comment")
  
  showAddChangeCard() {
    this.visibleCard.set("change");
  }
  showAddCommentCard() {
    this.visibleCard.set("comment"); 
  }

}
