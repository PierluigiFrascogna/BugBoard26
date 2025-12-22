import { Component, computed, Input, WritableSignal } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-add-comment-card',
  imports: [NgClass],
  templateUrl: './add-comment-card.html',
  styleUrl: './add-comment-card.css',
})
export class AddCommentCard {
  @Input() currentVisibleCard!:WritableSignal<"change" | "comment">; 
  isVisible=computed(() => this.currentVisibleCard()==="comment");
}
