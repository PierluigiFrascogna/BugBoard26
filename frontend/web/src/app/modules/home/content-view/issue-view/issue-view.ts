import { Component, computed, inject, Signal } from '@angular/core';
import { ProjectStore } from '../../sidebar/sidebar-element/project/project-store';
import { IssueStore } from '../issues-list/issue-card/issue/issue-store';
import { IssueEventStore } from './issue-event-card/issue-events/issue-event-store';
import { IssueEventCard } from "./issue-event-card/issue-event-card";
import { NgClass } from '@angular/common';
import { IssueCardFull } from "./issue-card-full/issue-card-full";
import { TIssueEvent } from './issue-event-card/issue-events/issue-event-model';
import { AddCommentCard } from "./add-comment-card/add-comment-card";
import { AuthStore } from '../../../../core/auth/auth-store';

@Component({
  selector: 'app-issue-view',
  imports: [IssueEventCard, NgClass, IssueCardFull, AddCommentCard],
  templateUrl: './issue-view.html',
  styleUrl: './issue-view.css',
})
export class IssueView {

  private readonly authStore = inject(AuthStore);
  private readonly projectStore = inject(ProjectStore);
  private readonly issueStore = inject(IssueStore);
  private readonly issueEventStore = inject(IssueEventStore);

  readonly title: Signal<string> = computed(() => this.projectStore.name()+this.issueStore.title());
  readonly issueEvents: Signal<TIssueEvent[]> = computed(() => this.issueEventStore.issueEvents());
  readonly isVisible: Signal<boolean> = computed(() => this.issueStore.selectedIssue()!==null);
  readonly isViewer: Signal<boolean> = computed(() => this.authStore.role()==="viewer");

  deselectIssue(){
    this.issueStore.deselectIssue();
  }
  
}
