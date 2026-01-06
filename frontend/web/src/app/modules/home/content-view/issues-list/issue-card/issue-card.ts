import { Component, inject, Input } from '@angular/core';
import { IIssue } from './issue/issue';
import { PriorityLabel } from "./priority-label/priority-label";
import { TypeLabel } from "./type-label/type-label";
import { StateLabel } from "./state-label/state-label";
import { IssueStore } from './issue/issue-store';

@Component({
  selector: 'app-issue-card',
  imports: [PriorityLabel, TypeLabel, StateLabel],
  templateUrl: './issue-card.html',
  styleUrl: './issue-card.css',
})
export class IssueCard {
  @Input() issue!: IIssue; 
  private readonly issueStore = inject(IssueStore);
  
  selectIssue() {
    this.issueStore.selectIssue(this.issue);
  }

}
