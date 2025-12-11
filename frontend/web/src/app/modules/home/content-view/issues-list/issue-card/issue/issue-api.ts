import { inject, Injectable } from '@angular/core';
import { httpResource } from '@angular/common/http';
import { Issue } from './issue';
import { ENVIRONMENT_TOKEN } from '../../../../../../../environments/environment-model';
import { ProjectStore } from '../../../../sidebar/sidebar-element/project/project-store';

@Injectable({
  providedIn: 'root',
})
export class IssueApi {
  
  private readonly env = inject(ENVIRONMENT_TOKEN);
  private readonly project = inject(ProjectStore);

  private readonly API_URL = this.env.urls.api;
  private readonly ISSUES_URL = "/issues"

  readonly issuesResource = httpResource<Issue[]>(() => ({
    url: `${this.API_URL}${this.ISSUES_URL}/${this.project.selectedProject()?.uuid}`,
    method: 'GET',
  }));

  
}
