import { Component, computed, inject, Signal, signal } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup, Validators,} from '@angular/forms';
import { IssueStore } from '../../issues-list/issue-card/issue/issue-store';
import { TypeLabel } from "../../issues-list/issue-card/type-label/type-label";
import { PriorityLabel } from "../../issues-list/issue-card/priority-label/priority-label";
import { StateLabel } from "../../issues-list/issue-card/state-label/state-label";
import { NgClass } from "@angular/common";
import { AuthStore } from '../../../../../core/auth/auth-store';

@Component({
  selector: 'app-issue-card-full',
  imports: [TypeLabel, PriorityLabel, StateLabel, NgClass, ReactiveFormsModule],
  templateUrl: './issue-card-full.html',
  styleUrl: './issue-card-full.css',
})
export class IssueCardFull {
  private readonly authStore = inject(AuthStore);
  private readonly issueStore = inject(IssueStore);
  //readonly isViewer: Signal<boolean> = computed(() => this.authStore.role()==="viewer");
  readonly isViewer: Signal<boolean> = computed(() => false);
  readonly issue = computed(() => this.issueStore.selectedIssue());
  
  isEditing = signal<boolean>(false);
  issueForm = new FormGroup({
    title: new FormControl('', [Validators.minLength(2)]),
    description: new FormControl('', [Validators.minLength(2)]),
    state: new FormControl('', [Validators.minLength(6)]),
    priority: new FormControl('', [Validators.minLength(6)])
  })
  
  deactivateEditMode() {
    this.isEditing.set(false);
  }
  
  activateEditMode() {
    const issue = this.issue();

    if (!issue) return;

    this.issueForm.setValue({
      title: issue.title,
      description: issue.description,
      state: issue.state,
      priority: issue.priority
    });

    this.issueForm.markAsPristine();
    this.isEditing.set(true);
  }
  
  sendChanges(){
    if (this.issueForm.invalid) return;

    const payload = this.getChangedValues();

    if (Object.keys(payload).length === 0) {
      this.isEditing.set(false);
      return;
    }

    this.isEditing.set(false);
    console.log(`you would have sent the following changes: `,{...payload})
    //TODO: aggiungere l'effettiva logica per l'inivio della chiamata API
  }

  getChangedValues(){
    const changed: any = {};

    Object.keys(this.issueForm.controls).forEach(key => {
      const control = this.issueForm.get(key);

      if (!control || !control.dirty) return;

      // evita invio se valore uguale all'originale
      if (control.dirty) {
        changed[key] = control.value;
      }
    });

    return changed;
  }
  
}
