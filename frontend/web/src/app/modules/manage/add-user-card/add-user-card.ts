import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { UserStore } from '../../profile/user/user-store';

@Component({
  selector: 'app-add-user-card',
  imports: [ReactiveFormsModule],
  templateUrl: './add-user-card.html',
  styleUrl: './add-user-card.css',
})
export class AddUserCard {
  private readonly userStore = inject(UserStore);

  userForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(2)]),
    surname: new FormControl('', [Validators.required, Validators.minLength(2)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    role: new FormControl('', [Validators.required])
  });


  createUser(){
    if(!this.userForm.valid)
      return;
    else{
      //TODO: chiamata alla user store per inserire la nuova utenza
    }
  }
}
