import { computed, inject, Injectable } from '@angular/core';
import { IssueEventApi } from './issue-event-api';
import { IssueEvent } from './issue-event';

export interface IssueEventsState {
  issueEvents: IssueEvent[];
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
      } as IssueEvent,

      {
        uuid: "comment 2",
        createdAt: new Date(), 
        type: "COMMENT",
        authorUuid: "user 2",
      } as IssueEvent,

      {
        uuid: "change 1",
        createdAt: new Date(), 
        type: "CHANGE",
        authorUuid: "user 1",
      } as IssueEvent,

      {
        uuid: "change 2",
        createdAt: new Date(), 
        type: "CHANGE",
        authorUuid: "user 2",
      } as IssueEvent,

      {
        uuid: "comment 3",
        createdAt: new Date(), 
        type: "COMMENT",
        authorUuid: "user 3",
      } as IssueEvent,

    ] as IssueEvent[],
    loading: this.api.issueEventsResource.isLoading(),
    error: this.api.issueEventsResource.error()
  })); 

  readonly issueEvents = computed(() => this.state().issueEvents);
  readonly loading = computed(() => this.state().loading); 
  readonly error = computed(() => this.state().error);
}
