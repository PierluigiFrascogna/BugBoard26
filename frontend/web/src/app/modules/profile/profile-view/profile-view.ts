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
  protected readonly authStore = inject(AuthStore);

  isEditing = signal<boolean>(false);

  profileForm = new FormGroup({
    email: new FormControl(this.authStore.email(), [Validators.email]),
    password: new FormControl('********', [Validators.minLength(8)])
  });

  activateEditMode(){
    this.isEditing.set(true);
    this.profileForm.reset();
  }

  deactivateEditMode(){
    this.isEditing.set(false);
  }

  sendChanges(){
    let updates: IUserUpdate = { email: undefined, password: undefined }
    if(!this.profileForm.controls.email.pristine && this.profileForm.controls.email.valid)
      updates.email=this.profileForm!.controls.email.value!;
    
    if(!this.profileForm.controls.password.pristine && this.profileForm.controls.password.valid)
      updates.password=this.profileForm!.controls.password.value!;

    this.authStore.modifyUser(updates);
  }

}
