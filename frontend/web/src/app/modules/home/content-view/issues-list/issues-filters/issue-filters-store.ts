import { Injectable, signal } from "@angular/core";
import { TIssueType, TIssuePriority, TIssueState } from "../issue-card/issue/issue";

@Injectable({
  providedIn: 'root',
})
export class IssueFiltersStore {

  readonly filtersModel = signal<IFilters>({
    type: "",
    priority: "",
    state: "",
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
      type: "",
      priority: "",
      state: "",
    });
  }
}

export interface IFilters {
  type: TIssueType | "";
  priority: TIssuePriority | "";
  state: TIssueState | "";
};

export interface IQueryParams {
  type?: TIssueType;
  priority?: TIssuePriority;
  state?: TIssueState;
}