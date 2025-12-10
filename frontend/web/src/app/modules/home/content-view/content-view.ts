import { Component, computed, inject } from '@angular/core';
import { ProjectStore } from '../sidebar/sidebar-element/project/project-store';
import { Project } from '../sidebar/sidebar-element/project/project-model';
import { IssueStore } from './view-element/issue/issue-store';
import { ViewElement } from "./view-element/view-element";

@Component({
  selector: 'app-content-view',
  imports: [ViewElement],
  templateUrl: './content-view.html',
  styleUrl: './content-view.css',
})
export class ContentView {
  private readonly projectStore = inject(ProjectStore);
  private readonly issueStore = inject(IssueStore);

  readonly project = computed(() => this.projectStore.selectedProject());
  readonly issues = computed(() => this.issueStore.projectIssues())

}
