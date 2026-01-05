import { Component, computed, Input, WritableSignal } from '@angular/core';
import { NgClass } from '@angular/common';
import { FormControl, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-comment-card',
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  templateUrl: './add-comment-card.html',
  styleUrl: './add-comment-card.css',
})
export class AddCommentCard {
  commentForm = new FormGroup({
    comment: new FormControl('', [Validators.minLength(2)])
  })

  saveComment(){
    if(this.commentForm.controls.comment.valid){
      console.log("you would have sent this comment: ", this.commentForm.controls.comment.value)
      //TODO: chiamata allo store per mandare la chiamata API
    }else{
      console.log("commento non pubblicato, non valido!");
    }
  }
}
