import { computed, inject, Injectable, signal, Signal, WritableSignal } from '@angular/core';
import { ProjectApi } from './project-api';
import { Project } from './project-model';

export interface ProjectsState {
  projects: Project[]
  loading: boolean
  error: Error | undefined
}

@Injectable({
  providedIn: 'root',
})
export class ProjectStore {

  private api = inject(ProjectApi);

  readonly state = computed<ProjectsState>(() => ({
    projects: this.api.projectsResource.hasValue() ? this.api.projectsResource.value() : [],
    loading: this.api.projectsResource.isLoading(),
    error: this.api.projectsResource.error()
  })); 

  readonly projects = computed(() => this.state().projects);
  readonly loading = computed(() => this.state().loading); 
  readonly error = computed(() => this.state().error);

}
