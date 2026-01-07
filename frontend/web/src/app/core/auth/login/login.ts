import { Component, inject } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthStore } from '../auth-store';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  private router = inject(Router);
  private auth = inject(AuthStore);

  isValidEmail: boolean = true;
  isValidPassword: boolean = true;

  constructor() {}

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });

  onSubmit() {
    if(this.loginForm.valid){
      this.auth.login(this.loginForm.controls.email.value!, this.loginForm.controls.password.value!);
      this.router.navigateByUrl('/'); // TODO: navigate to returnUrl che sta in nell'authguard
    }
  }
}
