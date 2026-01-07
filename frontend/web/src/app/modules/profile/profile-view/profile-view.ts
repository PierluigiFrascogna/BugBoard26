import { Component, inject, signal } from '@angular/core';
import { IUserUpdate, IUser } from '../user/user';
import { FormControl, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule } from "@angular/forms";
import { NgClass } from "@angular/common";
import { AuthStore } from '../../../core/auth/auth-store';

@Component({
  selector: 'app-profile-view',
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule, NgClass],
  templateUrl: './profile-view.html',
  styleUrl: './profile-view.css',
})
export class ProfileView {
  user: IUser = {uuid: "1234", name: "Ale", surname: "Giglio", email: "ale@fakemail.com", password: "fakepassword", role: "developer" }
  private readonly authStore = inject(AuthStore);

  isEditing = signal<boolean>(false);

  profileForm = new FormGroup({
    email: new FormControl('', [Validators.email]),
    password: new FormControl('********', [Validators.minLength(8)])
  });

  activateEditMode(){
    this.profileForm.reset({
      email: this.user.email, 
      password: '********',
    });
    this.profileForm.markAsPristine();
    this.isEditing.set(true);
  }

  deactivateEditMode(){
    this.isEditing.set(false);
  }

  sendChanges(){
    let updates: IUserUpdate = { email: undefined, password: undefined }
    if(!this.profileForm.controls.email.pristine && this.profileForm.controls.email.valid)
      updates.email=this.profileForm!.controls.email.value!;
    
    if(!this.profileForm.controls.password.pristine && this.profileForm.controls.password.valid)
      updates.password=this.profileForm!.controls.email.value!;

    this.authStore.modifyUser(updates);
  }

}
