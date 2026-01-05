import { Component, inject } from '@angular/core';
import { IIssueState, IssueStore } from '../issue-card/issue/issue-store';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { TIssuePriority, TIssueState, TIssueType } from '../issue-card/issue/issue';

@Component({
  selector: 'app-issues-filters',
  imports: [ReactiveFormsModule],
  templateUrl: './issues-filters.html',
  styleUrl: './issues-filters.css',
})
export class IssuesFilters {
  private readonly issueStore = inject(IssueStore);

  readonly filtersForm = new FormGroup({
    type : new FormControl<TIssueType | null>(null, Validators.required),
    priority : new FormControl<TIssuePriority | null>(null, Validators.required),
    state : new FormControl<TIssueState | null>(null, Validators.required)
  })
  
  
  resetFilters(){
    this.filtersForm.reset
  }

  sendFilters(){
    if(this.filtersForm.valid){
      console.log(this.filtersForm.getRawValue());
      this.issueStore.setSelectedFilters(this.filtersForm.getRawValue());
    }else{
      return
    }
  }

}