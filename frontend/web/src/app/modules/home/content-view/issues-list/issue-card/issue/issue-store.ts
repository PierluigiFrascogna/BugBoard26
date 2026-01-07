import { computed, inject, Injectable, signal } from '@angular/core';
import { IssueApi } from './issue-api';
import { IIssue, INewIssue } from './issue';
import { IssueFiltersStore } from '../../issues-filters/issue-filters-store';

export interface IIssueState {
  projectIssues: IIssue[];
  loading: boolean;
  error: Error | undefined;
}

@Injectable({
  providedIn: 'root',
})
export class IssueStore {

  private readonly api = inject(IssueApi);
  
  private readonly _state = computed<IIssueState>(() => ({
    projectIssues: this.api.issuesResource.hasValue() ? this.api.issuesResource.value() :[] as IIssue[],
    loading: this.api.issuesResource.isLoading(),
    error: this.api.issuesResource.error()
  })); 

  readonly projectIssues = computed(() => this._state().projectIssues);
  readonly loading = computed(() => this._state().loading); 
  readonly error = computed(() => this._state().error);
  
  private readonly _selectedIssue = signal<IIssue | null>(null);
  readonly selectedIssue = computed(() => this._selectedIssue());
  readonly title = computed(() => this._selectedIssue()!==null ? "."+ this._selectedIssue()!.title : '' );

  selectIssue(issue: IIssue){
    this._selectedIssue.set(issue);
  }

  deselectIssue(){
    this._selectedIssue.set(null);
  }

  createIssue(issue: INewIssue) {
    this.api.createIssue(issue).subscribe({
      next: (newIssue) => {
        this.api.issuesResource.reload();
      },
      error: (err) => {
        console.error('Error creating issue:', err);
      },
    });
  }

}