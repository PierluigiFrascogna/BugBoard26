import { Component, computed, inject, signal } from '@angular/core';
import { IssueStore } from '../../issues-list/issue-card/issue/issue-store';
import { TypeLabel } from "../../issues-list/issue-card/type-label/type-label";
import { PriorityLabel } from "../../issues-list/issue-card/priority-label/priority-label";
import { StateLabel } from "../../issues-list/issue-card/state-label/state-label";

@Component({
  selector: 'app-issue-card-full',
  imports: [TypeLabel, PriorityLabel, StateLabel],
  templateUrl: './issue-card-full.html',
  styleUrl: './issue-card-full.css',
})
export class IssueCardFull {
  private readonly issueStore = inject(IssueStore);
  readonly issue = computed(() => this.issueStore.selectedIssue());

}
