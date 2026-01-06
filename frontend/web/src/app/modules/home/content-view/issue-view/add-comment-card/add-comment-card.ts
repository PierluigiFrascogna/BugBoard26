import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Comment } from '../issue-event-card/issue-events/comment-event/comment-card/comment/comment';
import { IssueEventStore } from '../issue-event-card/issue-events/issue-event-store';

@Component({
  selector: 'app-add-comment-card',
  imports: [ReactiveFormsModule],
  templateUrl: './add-comment-card.html',
  styleUrl: './add-comment-card.css',
})
export class AddCommentCard {
  private readonly issueEventsStore = inject(IssueEventStore);

  commentForm = new FormGroup({
    comment: new FormControl('', [Validators.required, Validators.minLength(2)])
  })

  saveComment(){
    if(!this.commentForm.valid){
      return
    }else{
      const comment: Comment["text"] = this.commentForm.get("comment")!.value!;
      this.issueEventsStore.createComment(comment);   
    }
  }
}
