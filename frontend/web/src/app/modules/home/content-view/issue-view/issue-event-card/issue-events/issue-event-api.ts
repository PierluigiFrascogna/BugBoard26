import { inject, Injectable } from '@angular/core';
import { HttpClient, httpResource } from '@angular/common/http';
import { ENVIRONMENT_TOKEN } from '../../../../../../../environments/environment-model';
import { IssueStore } from '../../../issues-list/issue-card/issue/issue-store';
import { TIssueEvent } from './issue-event-model';
import { Comment } from './comment-event/comment-card/comment/comment';
import { ProjectStore } from '../../../../sidebar/sidebar-element/project/project-store';

@Injectable({
  providedIn: 'root',
})
export class IssueEventApi {
  private readonly env = inject(ENVIRONMENT_TOKEN);
  private readonly projectStore = inject(ProjectStore);
  private readonly issueStore = inject(IssueStore);
  private readonly http = inject(HttpClient);

  private readonly API_URL = this.env.urls.api;
  private readonly ISSUES_URL = "/issues";
  private readonly PROJECTS_URL = "/projects";
  private readonly ISSUE_EVENTS_URL = "/events";
  private readonly COMMENTS_URL = "/comment";

  readonly issueEventsResource = httpResource<TIssueEvent[]>(() => ({
    url: `${this.API_URL}${this.PROJECTS_URL}/${this.projectStore.selectedProject()?.uuid}/${this.issueStore.selectedIssue()?.uuid}${this.ISSUE_EVENTS_URL}`,
    method: 'GET',
  }));

  createIssueEvent(issueEvent: TIssueEvent) {
    return this.http.post<TIssueEvent>(
      `${this.API_URL}${this.ISSUES_URL}/${this.issueStore.selectedIssue()?.uuid}${this.ISSUE_EVENTS_URL}`,
      issueEvent
    );
  }

  createComment(comment: Comment["text"]){
    return this.http.post<Comment>(
      `${this.API_URL}${this.PROJECTS_URL}/${this.projectStore.selectedProject()?.uuid}/${this.issueStore.selectedIssue()?.uuid}${this.COMMENTS_URL}`, comment
    );
  }
}
