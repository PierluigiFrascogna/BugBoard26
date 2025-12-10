import { computed, inject, Injectable, signal, Signal, WritableSignal } from '@angular/core';
import { ProjectApi } from './project-api';
import { Project } from './project-model';
import { core } from '@angular/compiler';

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

  private readonly state = computed<ProjectsState>(() => ({
    projects: this.api.projectsResource.hasValue() ? this.api.projectsResource.value() : [
      {
        uuid: "abcde",
        name: "Backend",
        creation_date: new Date(),
      } as Project,

      {
        uuid: "fghi",
        name: "Frontend",
        creation_date: new Date(),
      }as Project
    ]as Project[],
    loading: this.api.projectsResource.isLoading(),
    error: this.api.projectsResource.error()
  })); 

  readonly projects = computed(() => this.state().projects);
  readonly loading = computed(() => this.state().loading); 
  readonly error = computed(() => this.state().error);

  private readonly _selectedProject = signal<Project | null>(null);
  readonly selectedProject = computed(() => this._selectedProject());

  selectProject(project: Project) {
    this._selectedProject.set(project);
  }


}
