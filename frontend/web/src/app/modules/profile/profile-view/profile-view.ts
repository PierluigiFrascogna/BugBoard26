import { Component, signal } from '@angular/core';
import { User } from '../user/user';
import { FormControl, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule } from "@angular/forms";
import { NgClass } from "@angular/common";

@Component({
  selector: 'app-profile-view',
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule, NgClass],
  templateUrl: './profile-view.html',
  styleUrl: './profile-view.css',
})
export class ProfileView {
  user: User = {uuid: "1234", name: "Ale", surname: "Giglio", email: "ale@fakemail.com", password: "fakepassword", role: "dev" }

  isEditing = signal<boolean>(false);

  profileForm = new FormGroup({
    name: new FormControl('', [Validators.minLength(2)]),
    surname: new FormControl('', [Validators.minLength(2)]),
    email: new FormControl('', [Validators.email]),
    password: new FormControl('', [Validators.minLength(6)])
  });

  activateEditMode(){
    this.profileForm.reset({
      name: this.user.name,
      surname: this.user.surname,
      email: this.user.email, 
      password: '',
    });
    this.profileForm.markAsPristine();
    this.isEditing.set(true);
  }

  deactivateEditMode(){
    this.isEditing.set(false);
  }

  sendChanges(){
    if (this.profileForm.invalid) return;

    const payload = this.getChangedValues();

    if (Object.keys(payload).length === 0) {
      this.isEditing.set(false);
      return;
    }

    this.user = { ...this.user, ...payload };
    this.isEditing.set(false);
    console.log(`you would have sent the following changes: `,{...payload})
    //TODO: aggiungere l'effettiva logica per l'inivio della chiamata API
  }

  getChangedValues(){
    const changed: any = {};

    Object.keys(this.profileForm.controls).forEach(key => {
      const control = this.profileForm.get(key);

      if (!control || !control.dirty) return;

      // password: invia solo se valorizzata
      if (key === 'password' && !control.value) return;

      // evita invio se valore uguale all'originale
      if (control.value === (this.user as any)[key]) return;

      changed[key] = control.value;
    });

    return changed;
  }

}
