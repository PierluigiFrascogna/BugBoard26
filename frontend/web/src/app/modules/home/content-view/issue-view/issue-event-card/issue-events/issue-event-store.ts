import { computed, inject, Injectable } from '@angular/core';
import { IssueEventApi } from './issue-event-api';
import { Comment } from './comment-event/comment-card/comment/comment';
import { TIssueEvent } from './issue-event-model';

export interface IssueEventsState {
  issueEvents: TIssueEvent[];
  loading: boolean;
  error: Error | undefined;
}

@Injectable({
  providedIn: 'root',
})
export class IssueEventStore {
  private readonly api = inject(IssueEventApi);
    
  private readonly state = computed<IssueEventsState>(() => ({
    issueEvents: this.api.issueEventsResource.hasValue() ? this.api.issueEventsResource.value() : [] as TIssueEvent[],
    loading: this.api.issueEventsResource.isLoading(),
    error: this.api.issueEventsResource.error()
  })); 

  readonly issueEvents = computed(() => this.state().issueEvents);
  readonly loading = computed(() => this.state().loading); 
  readonly error = computed(() => this.state().error);

  sendChanges(changes: any[]){
    changes.forEach(change => {
      this.api.sendChanges(change).subscribe({
        next: () => {
          this.api.issueEventsResource.reload();
        },
        error: (err: Error) => {
          console.error('Error sending changes', err);
        }
      })
    })
  }

  createComment(comment: Comment["text"]){
    this.api.createComment(comment).subscribe({
      next: (createdComment: Comment) => {
        this.api.issueEventsResource.reload();
      },
      error: (err: Error) => {
        console.error('Error creating comment: ', err);
      }
    })
  }
}
