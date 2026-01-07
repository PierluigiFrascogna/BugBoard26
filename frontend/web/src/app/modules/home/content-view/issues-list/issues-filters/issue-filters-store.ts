import { Injectable, signal } from "@angular/core";
import { TIssueType, TIssuePriority, TIssueState } from "../issue-card/issue/issue";

@Injectable({
  providedIn: 'root',
})
export class IssueFiltersStore {

  readonly filtersModel = signal<IFilters>({
    type: null,
    priority: null,
    state: null,
  });

  setFilters(filters: IFilters) {
    this.filtersModel.set({
      type: filters.type,
      priority: filters.priority,
      state: filters.state,
    });
  }

  resetFilters() {
    this.filtersModel.set({
      type: null,
      priority: null,
      state: null,
    });
  }
}

export interface IFilters {
  type: TIssueType | null;
  priority: TIssuePriority | null;
  state: TIssueState | null;
};

export interface IQueryParams {
  type?: TIssueType;
  priority?: TIssuePriority;
  state?: TIssueState;
}