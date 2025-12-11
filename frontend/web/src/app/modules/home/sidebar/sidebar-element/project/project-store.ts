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

  private readonly _state = computed<ProjectsState>(() => ({
    projects: this.api.projectsResource.hasValue() ? this.api.projectsResource.value() : [
      {
        uuid: "abcde",
        name: "Backend",
        creationDate: new Date(),
      } as Project,

      {
        uuid: "fghi",
        name: "Frontend",
        creationDate: new Date(),
      }as Project
    ]as Project[],
    loading: this.api.projectsResource.isLoading(),
    error: this.api.projectsResource.error()
  })); 

  readonly projects = computed(() => this._state().projects);
  readonly loading = computed(() => this._state().loading); 
  readonly error = computed(() => this._state().error);

  private readonly _selectedProject = signal<Project | null>(null);
  readonly selectedProject = computed(() => this._selectedProject());
  readonly name= computed(() => this._selectedProject()===null ? '' : this._selectedProject()?.name);

  selectProject(project: Project) {
    this._selectedProject.set(project);
  }


}
