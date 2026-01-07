import { Component, EventEmitter, inject, Output } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { IssueStore } from '../issue-card/issue/issue-store';
import { IIssue, INewIssue } from '../issue-card/issue/issue';


@Component({
  selector: 'app-add-issue-card',
  imports: [ReactiveFormsModule],
  templateUrl: './add-issue-card.html',
  styleUrl: './add-issue-card.css',
})
export class AddIssueCard {
  @Output() close = new EventEmitter();

  private readonly issueStore = inject(IssueStore);
  
  issueForm= new FormGroup({
    title: new FormControl('',[Validators.required, Validators.minLength(1)]),
    description: new FormControl('', [Validators.required, Validators.minLength(1)]),
    type: new FormControl('', [Validators.required]),
    priority: new FormControl('', [Validators.required])
  });

  createIssue() {
    if(this.issueForm.valid){
      const newIssue = {
        title: this.issueForm.controls.title.value!,
        description: this.issueForm.controls.description.value!,
        type: this.issueForm.controls.type.value!,
        priority: this.issueForm.controls.priority.value!,
      } as INewIssue;
      this.issueStore.createIssue(newIssue);
    }else{
      return
    }
  }

  closeNewIssueCard(){
    this.issueForm.reset;
    this.close.emit();
  }
}
