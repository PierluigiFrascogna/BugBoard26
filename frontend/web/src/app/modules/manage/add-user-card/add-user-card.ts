import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { UserStore } from '../../profile/user/user-store';
import { IUser, TUserRole } from '../../profile/user/user';
import { TemplateBindingParseResult } from '@angular/compiler';

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
    role: new FormControl<TUserRole>('VIEWER', [Validators.required, Validators.nullValidator])
  });


  createUser(){
    if(!this.userForm.valid)
      return;
    else{
      let user = {
        name: this.userForm.get("name")!.value,
        surname: this.userForm.get("surname")!.value,
        email: this.userForm.get("email")!.value,
        password: this.userForm.get("password")!.value,
        role: this.userForm.get("role")!.value
      } as IUser;

      this.userStore.createUser(user);
      console.log(user);
    }
  }
}
