import { inject, Injectable } from '@angular/core';
import { HttpClient, httpResource } from '@angular/common/http';
import { INewIssue, IIssue } from './issue';
import { ENVIRONMENT_TOKEN } from '../../../../../../../environments/environment-model';
import { ProjectStore } from '../../../../sidebar/sidebar-element/project/project-store';

@Injectable({
  providedIn: 'root',
})
export class IssueApi {
  
  private readonly env = inject(ENVIRONMENT_TOKEN);
  private readonly project = inject(ProjectStore);
  private readonly http = inject(HttpClient);

  private readonly API_URL = this.env.urls.api;
  private readonly PROJECTS_URL = "/projects";
  private readonly ISSUES_URL = "/issues";

  readonly issuesResource = httpResource<IIssue[]>(() => ({
    url: `${this.API_URL}${this.PROJECTS_URL}/${this.project.selectedProject()?.uuid}${this.ISSUES_URL}`,
    method: 'GET',
  }));

  createIssue(issue: INewIssue) {
    return this.http.post<IIssue>(
      `${this.API_URL}${this.PROJECTS_URL}/${this.project.selectedProject()?.uuid}${this.ISSUES_URL}`,
      issue
    );
  }
  
}
