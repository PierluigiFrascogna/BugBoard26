import { inject, Injectable } from '@angular/core';
import { HttpClient, httpResource } from '@angular/common/http';
import { ENVIRONMENT_TOKEN } from '../../../../../../../environments/environment-model';
import { IssueStore } from '../../../issues-list/issue-card/issue/issue-store';
import { TIssueEvent } from './issue-event-model';
import { Comment } from './comment-event/comment-card/comment/comment';

@Injectable({
  providedIn: 'root',
})
export class IssueEventApi {
  private readonly env = inject(ENVIRONMENT_TOKEN);
  private readonly project = inject(IssueStore);
  private readonly http = inject(HttpClient);

  private readonly API_URL = this.env.urls.api;
  private readonly ISSUES_URL = "/issues";
  private readonly ISSUE_EVENTS_URL = "/events";

  readonly issueEventsResource = httpResource<TIssueEvent[]>(() => ({
    url: `${this.API_URL}${this.ISSUES_URL}/${this.project.selectedIssue()?.uuid}`,
    method: 'GET',
  }));

  createIssueEvent(issueEvent: TIssueEvent) {
    return this.http.post<TIssueEvent>(
      `${this.API_URL}${this.ISSUES_URL}/${this.project.selectedIssue()?.uuid}${this.ISSUE_EVENTS_URL}`,
      issueEvent
    );
  }

  createComment(comment: Comment["text"]){
    return this.http.post<Comment>(
      `${this.API_URL}${this.ISSUES_URL}/${this.project.selectedIssue()?.uuid}`, comment
    );
  }
}
