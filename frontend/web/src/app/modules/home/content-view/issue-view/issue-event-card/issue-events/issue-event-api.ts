import { inject, Injectable } from '@angular/core';
import { httpResource } from '@angular/common/http';
import { ENVIRONMENT_TOKEN } from '../../../../../../../environments/environment-model';
import { IssueStore } from '../../../issues-list/issue-card/issue/issue-store';
import { TIssueEvent } from './issue-event-model';

@Injectable({
  providedIn: 'root',
})
export class IssueEventApi {
  private readonly env = inject(ENVIRONMENT_TOKEN);
  private readonly project = inject(IssueStore);

  private readonly API_URL = this.env.urls.api;
  private readonly ISSUE_EVENTS_URL = "/issues"

  readonly issueEventsResource = httpResource<TIssueEvent[]>(() => ({
    url: `${this.API_URL}${this.ISSUE_EVENTS_URL}/${this.project.selectedIssue()?.uuid}`,
    method: 'GET',
  }));
}
