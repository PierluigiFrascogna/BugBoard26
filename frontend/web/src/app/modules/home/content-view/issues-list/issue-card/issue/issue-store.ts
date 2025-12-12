import { computed, inject, Injectable, signal } from '@angular/core';
import { IssueApi } from './issue-api';
import { Issue } from './issue';

export interface IssueState {
  projectIssues: Issue[];
  loading: boolean;
  error: Error | undefined;
}

@Injectable({
  providedIn: 'root',
})
export class IssueStore {
  private readonly api  = inject(IssueApi);
  
  private readonly _state = computed<IssueState>(() => ({
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
      }as Issue,

      {
      uuid: "issue2",
        title: "issue n2",
        description: "issue n2 for testing",
        type: "feature",
        priority: 'medium',
        state: 'todo', 
        image: "",
        author: "user2"
      }as Issue,

      {
      uuid: "issue3",
        title: "issue n3",
        description: "issue n3 for testing",
        type: 'documentation',
        priority: "low",
        state: "pending", 
        image: "",
        author: "user3"
      }as Issue,

    ] as Issue[],
    loading: this.api.issuesResource.isLoading(),
    error: this.api.issuesResource.error()
  })); 

  readonly projectIssues = computed(() => this._state().projectIssues);
  readonly loading = computed(() => this._state().loading); 
  readonly error = computed(() => this._state().error);
  
  private readonly _selectedIssue = signal<Issue | null>(null);
  readonly selectedIssue = computed(() => this._selectedIssue());
  readonly title = computed(() => this._selectedIssue()!==null ? "."+ this._selectedIssue()!.title : '' );

  selectIssue(issue: Issue){
    this._selectedIssue.set(issue);
  }

  deselectIssue(){
    this._selectedIssue.set(null);
  }
}