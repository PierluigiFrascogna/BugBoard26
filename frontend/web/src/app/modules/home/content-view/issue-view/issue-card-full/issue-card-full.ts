import { Component, computed, inject, Signal, signal } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup, Validators,} from '@angular/forms';
import { IssueStore } from '../../issues-list/issue-card/issue/issue-store';
import { TypeLabel } from "../../issues-list/issue-card/type-label/type-label";
import { PriorityLabel } from "../../issues-list/issue-card/priority-label/priority-label";
import { StateLabel } from "../../issues-list/issue-card/state-label/state-label";
import { NgClass } from "@angular/common";
import { AuthStore } from '../../../../../core/auth/auth-store';
import { TIssuePriority, TIssueState } from '../../issues-list/issue-card/issue/issue';
import { IssueEventStore } from '../issue-event-card/issue-events/issue-event-store';

@Component({
  selector: 'app-issue-card-full',
  imports: [TypeLabel, PriorityLabel, StateLabel, NgClass, ReactiveFormsModule],
  templateUrl: './issue-card-full.html',
  styleUrl: './issue-card-full.css',
})
export class IssueCardFull {
  private readonly authStore = inject(AuthStore);
  private readonly issueStore = inject(IssueStore);
  private readonly issueEventStore = inject(IssueEventStore);
  readonly isViewer: Signal<boolean> = computed(() => this.authStore.role()==="VIEWER");
  readonly issue = computed(() => this.issueStore.selectedIssue());
  
  isEditing = signal<boolean>(false);
  issueForm = new FormGroup({
    title: new FormControl(this.issue()!.title, [Validators.minLength(2)]),
    description: new FormControl(this.issue()!.description, [Validators.minLength(2)]),
    state: new FormControl<TIssueState>(this.issue()!.state),
    priority: new FormControl<TIssuePriority>(this.issue()!.priority)
  })
  
  deactivateEditMode() {
    this.isEditing.set(false);
  }
  
  activateEditMode() {
    this.issueForm.reset();
    this.isEditing.set(true);
  }
  
  sendChanges(){
    if (this.issueForm.invalid){
      return;
    } else {
      const changes = this.getChangedValues();
      this.issueEventStore.sendChanges(changes);
    }
  }

  getChangedValues(){
    const changes: any[] = [];

    Object.keys(this.issueForm.controls).forEach(key => {
      const control = this.issueForm.get(key);

      if (!control || !control.dirty) return;

      if (control.dirty) {
        switch(key){
          case ("title"): changes.push({type:"TITLE", newTitle: control.value});
            break;
          case ("description"): changes.push({type:"DESCRIPTION", newDescription: control.value});
            break; 
          case ("state"): changes.push({type:"STATE", newState: control.value});
            break; 
          case ("priority"): changes.push({type:"PRIORITY", newPriority: control.value}); 
        }
      }
    });

    return changes;
  }
  
}
