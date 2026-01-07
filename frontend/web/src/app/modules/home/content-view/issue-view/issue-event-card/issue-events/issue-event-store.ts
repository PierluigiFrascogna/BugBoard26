import { afterNextRender, computed, inject, Injectable } from '@angular/core';
import { IssueEventApi } from './issue-event-api';
import { IssueEvent } from './issue-event';
import { Change } from './change-event/change-card/change/change';
import { Comment } from './comment-event/comment-card/comment/comment';
import { TitleChange } from './change-event/change-card/change/title-change';
import { DescriptionChange } from './change-event/change-card/change/description-change';
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
      this.api.sendChanges(changes).subscribe({
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
        //TODO: vedere cosa fare qui
      },
      error: (err: Error) => {
        console.error('Error creating comment: ', err);
      }
    })
  }
}
