import { computed, inject, Injectable } from '@angular/core';
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
    issueEvents: this.api.issueEventsResource.hasValue() ? this.api.issueEventsResource.value() : [
      {
        uuid: "comment 1",
        createdAt: new Date(), 
        type: "COMMENT",
        authorUuid: "user 1",
        text: "blablablba"
      } as Comment,

      {
        uuid: "comment 2",
        createdAt: new Date(), 
        type: "COMMENT",
        authorUuid: "user 2",
      } as Comment,

      {
        uuid: "change 1",
        createdAt: new Date(), 
        type: "CHANGE",
        authorUuid: "user 1",
        changeType: "TITLE",
        old: "issue1",
        new: "issue1mlmlmlml"
      } as TitleChange,

      {
        uuid: "change 2",
        createdAt: new Date(), 
        type: "CHANGE",
        authorUuid: "user 2",
        changeType: "DESCRIPTION",
        old: "mlmlmlmlml",
        new: "brbrbrbrbrbr"
      } as DescriptionChange,

      {
        uuid: "comment 3",
        createdAt: new Date(), 
        type: "COMMENT",
        authorUuid: "user 3",
      } as Comment,

    ] as TIssueEvent[],
    loading: this.api.issueEventsResource.isLoading(),
    error: this.api.issueEventsResource.error()
  })); 

  readonly issueEvents = computed(() => this.state().issueEvents);
  readonly loading = computed(() => this.state().loading); 
  readonly error = computed(() => this.state().error);

  createIssueEvent(issueEvent: TIssueEvent) {
    this.api.createIssueEvent(issueEvent).subscribe({
      next: (createdEvent: TIssueEvent) => {
        // Aggiorna lo stato locale aggiungendo il nuovo evento creato
      },
      error: (err: Error) => {
        console.error('Error creating issue event:', err);
      }
    })
  }
}
