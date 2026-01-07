import { Component, inject } from "@angular/core";
import { form, Field } from "@angular/forms/signals";
import { IssueFiltersStore } from "./issue-filters-store";

@Component({
  selector: 'app-issues-filters',
  imports: [Field],
  templateUrl: './issues-filters.html',
  styleUrl: './issues-filters.css',
})
export class IssuesFilters {

  private readonly filtersStore = inject(IssueFiltersStore);

  readonly filtersForm = form(this.filtersStore.filtersModel);
  
  resetFilters(){
    this.filtersStore.resetFilters();
  }

  sendFilters(){
    this.filtersStore.setFilters(this.filtersForm().value());
  }

}