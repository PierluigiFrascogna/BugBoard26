import { ENVIRONMENT_TOKEN } from '../../../../../../environments/environment-model';
import { inject, Injectable, signal } from '@angular/core';
import { HttpClient, httpResource } from '@angular/common/http';
import { Project } from './project-model';
import { UserStore } from '../../../../profile/user/user-store'


@Injectable({
  providedIn: 'root',
})
export class ProjectApi {
  
  private readonly env = inject(ENVIRONMENT_TOKEN);
  private readonly http = inject(HttpClient);

  private readonly API_URL = this.env.urls.api;
  private readonly PROJECT_URL = "/projects"

  readonly projectsResource = httpResource<Project[]>(() => ({
    url: `${this.API_URL}${this.PROJECT_URL}`,
    method: 'GET',
  }));

  createProject(name: Project['name']) {
    return this.http.post<Project>(
      `${this.API_URL}${this.PROJECT_URL}`,
      { name: name }
    );
  }

}
