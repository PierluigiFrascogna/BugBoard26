import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";


@Component({
  selector: 'app-add-issue-card',
  imports: [ReactiveFormsModule],
  templateUrl: './add-issue-card.html',
  styleUrl: './add-issue-card.css',
})
export class AddIssueCard {
  @Output() close = new EventEmitter();
  
  issueForm= new FormGroup({
    title: new FormControl('',[Validators.required, Validators.minLength(1)]),
    description: new FormControl('', [Validators.required, Validators.minLength(1)]),
    type: new FormControl('', [Validators.required]),
    priority: new FormControl('', [Validators.required])
  });

  createIssue() {
    if(this.issueForm.valid){
      //TODO: aggiungere chiamata api per inserire una nuova issue
    }else{
      return
    }
  }

  closeNewIssueCard(){
    this.issueForm.reset;
    this.close.emit();
  }
}
