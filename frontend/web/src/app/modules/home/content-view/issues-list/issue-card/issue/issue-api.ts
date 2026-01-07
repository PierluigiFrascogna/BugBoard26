import { computed, inject, Injectable, Signal } from '@angular/core';
import { HttpClient, httpResource } from '@angular/common/http';
import { INewIssue, IIssue } from './issue';
import { ENVIRONMENT_TOKEN } from '../../../../../../../environments/environment-model';
import { ProjectStore } from '../../../../sidebar/sidebar-element/project/project-store';
import { IQueryParams, IssueFiltersStore } from '../../issues-filters/issue-filters-store';

@Injectable({
  providedIn: 'root',
})
export class IssueApi {
  
  private readonly env = inject(ENVIRONMENT_TOKEN);
  private readonly project = inject(ProjectStore);
  private readonly http = inject(HttpClient);
  private readonly filtersStore = inject(IssueFiltersStore);

  private readonly API_URL = this.env.urls.api;
  private readonly PROJECTS_URL = "/projects";
  private readonly ISSUES_URL = "/issues";

  private readonly filters: Signal<IQueryParams> = computed(() => ({
    type: this.filtersStore.filtersModel().type || undefined,
    priority: this.filtersStore.filtersModel().priority || undefined,
    state: this.filtersStore.filtersModel().state || undefined,
  }));

  readonly issuesResource = httpResource<IIssue[]>(() => ({
    url: `${this.API_URL}${this.PROJECTS_URL}/${this.project.selectedProject()?.uuid}${this.ISSUES_URL}`,
    method: 'GET',
    params: { ...this.filters() }
  }));

  createIssue(issue: INewIssue) {
    return this.http.post<IIssue>(
      `${this.API_URL}${this.PROJECTS_URL}/${this.project.selectedProject()?.uuid}${this.ISSUES_URL}`,
      issue
    );
  }
  
}


