import { Component, computed, inject, Signal, signal } from '@angular/core';
import { IssueStore } from './issue-card/issue/issue-store';
import { ProjectStore } from '../../sidebar/sidebar-element/project/project-store';
import { IssueCard } from "./issue-card/issue-card";
import { NgClass } from '@angular/common';
import { AddIssueCard } from "./add-issue-card/add-issue-card";
import { IssuesFilters } from "./issues-filters/issues-filters";
import { AuthStore } from '../../../../core/auth/auth-store';

@Component({
  selector: 'app-issues-list',
  imports: [IssueCard, NgClass, AddIssueCard, IssuesFilters],
  templateUrl: './issues-list.html',
  styleUrl: './issues-list.css',
})
export class IssesList {
  private readonly issueStore = inject(IssueStore); 
  private readonly projectStore = inject(ProjectStore);
  private readonly authStore = inject(AuthStore);
  
  readonly isNewIssueCardOpen = signal<boolean>(false);
  
  readonly title = computed(() => this.projectStore.name());
  readonly issues = computed(() => this.issueStore.projectIssues());

  readonly isTitleVisible = computed(() => this.title() !== '' );
  readonly isVisible = computed(() => this.issueStore.selectedIssue()===null);

  readonly isViewer = computed(() => false);
  
  openNewIssueCard() {
    this.isNewIssueCardOpen.set(true);
  }
  
  closeNewIssueCard() {
    this.isNewIssueCardOpen.set(false);
  }

  

}
