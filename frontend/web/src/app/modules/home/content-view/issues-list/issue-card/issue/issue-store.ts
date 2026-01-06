import { computed, inject, Injectable, signal } from '@angular/core';
import { IssueApi } from './issue-api';
import { IIssue, INewIssue, TIssuePriority, TIssueState, TIssueType } from './issue';

export interface IIssueState {
  projectIssues: IIssue[];
  loading: boolean;
  error: Error | undefined;
}

@Injectable({
  providedIn: 'root',
})
export class IssueStore {
  private readonly api  = inject(IssueApi);
  
  private readonly _selectedFilters = signal<IFilters>({
    type: null,
    priority: null,
    state: null
  });
  readonly selectedFilters = computed(() => this._selectedFilters());

  
  private readonly _state = computed<IIssueState>(() => ({
    projectIssues: this.api.issuesResource.hasValue() ? this.api.issuesResource.value() :[
      {
        uuid: "issue1",
        title: "issue n1",
        description: "issue n1 for testing",
        type: "bug",
        priority: "high",
        state: "pending", 
        image: "",
        author: "user1"
      }as IIssue,

      {
      uuid: "issue2",
        title: "issue n2",
        description: "issue n2 for testing",
        type: "feature",
        priority: 'medium',
        state: 'todo', 
        image: "",
        author: "user2"
      }as IIssue,

      {
      uuid: "issue3",
        title: "issue n3",
        description: "issue n3 for testing",
        type: 'documentation',
        priority: "low",
        state: "pending", 
        image: "",
        author: "user3"
      }as IIssue,

    ] as IIssue[],
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

  setSelectedFilters(filter: IFilters){
    this._selectedFilters.set({
      type: filter.type,
      priority: filter.priority,
      state: filter.state,
    })
    console.log("signal _selectedFilters: ", this._selectedFilters());
    console.log("signal selectedFilters: ", this.selectedFilters());

  }

  createIssue(issue: INewIssue) {
    this.api.createIssue(issue).subscribe({
      next: (newIssue) => {
        // Update the local state with the newly created issue
        this.api.issuesResource.reload();
      },
      error: (err) => {
        console.error('Error creating issue:', err);
      },
    });
  }

}

export interface IFilters {
  type: TIssueType | null;
  priority: TIssuePriority | null;
  state: TIssueState | null;
};