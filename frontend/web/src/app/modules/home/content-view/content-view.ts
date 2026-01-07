import { Component, computed, inject, linkedSignal } from '@angular/core';
import { IssesList } from "./issues-list/issues-list";
import { ProjectStore } from '../sidebar/sidebar-element/project/project-store';
import { IssueStore } from './issues-list/issue-card/issue/issue-store';
import { IssueView } from "./issue-view/issue-view";

@Component({
  selector: 'app-content-view',
  imports: [IssesList, IssueView],
  templateUrl: './content-view.html',
  styleUrl: './content-view.css',
})
export class ContentView {
  private readonly projectStore = inject(ProjectStore);
  private readonly issueStore = inject(IssueStore);

  readonly title = computed(() => this.projectStore.name() + this.issueStore.title() );

  isIssuesListVisible = linkedSignal(() => this.projectStore.selectedProject()!==null || this.issueStore.selectedIssue()!==null);
  isIssueViewVisible = linkedSignal(() => this.issueStore.selectedIssue()!==null);
}
