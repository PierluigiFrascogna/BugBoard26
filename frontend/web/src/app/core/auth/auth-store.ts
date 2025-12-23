import { computed, inject, Injectable, signal, Signal, WritableSignal } from '@angular/core';
import { Jwt } from './JWT/jwt';
import { AuthApi } from './auth-api';
import { JwtResponse } from './JWT/jwt-response';

@Injectable({
  providedIn: 'root',
})
export class AuthStore {

  private readonly authApi = inject(AuthApi);

  private readonly jwt: WritableSignal<Jwt | null> = signal(null);

  readonly JWT: Signal<Jwt | null> = computed(() => this.jwt());

  // TODO: spostare tutte queste informazioni nello user-store
  readonly uuid: Signal<string | null> = computed(() => this.jwt()?.payload().sub || null);
  readonly name: Signal<string | null> = computed(() => this.jwt()?.payload().name || null);
  readonly surname: Signal<string | null> = computed(() => this.jwt()?.payload().surname || null);
  readonly email: Signal<string | null> = computed(() => this.jwt()?.payload().email || null);
  readonly password: Signal<string | null> = computed(() => this.jwt()?.payload().password || null);
  readonly role: Signal<string | null> = computed(() => this.jwt()?.payload().role || null);
  // fino a qui

  readonly isAuthenticated: Signal<boolean> = computed(() => this.jwt() !== null);

  constructor() {
    this.loadTokenFromStorage();
  }

  loadTokenFromStorage() {
    const token = localStorage.getItem('auth');
    this.jwt.set(token ? new Jwt(token) : null);
  }
  
  setJwt(token: Jwt) {
    this.saveTokenToStorage(token);
    this.jwt.set(token);
  }

  unsetJwt() {
    this.deleteTokenFromStorage();
    this.jwt.set(null);
  }

  private saveTokenToStorage(token: Jwt) {
    localStorage.setItem('auth', token.token());
  }

  private deleteTokenFromStorage() {
    localStorage.removeItem('auth');
  }


  login(email: string, password: string) {
    if (!email || !password) {
      throw new Error('Email and password must be provided');
    }
    this.authApi.login({ email, password }).subscribe({
      next: (response: JwtResponse) => {
        const jwt = new Jwt(response.token);
        this.setJwt(jwt);
      },
      error: (err) => {
        console.error('Login failed', err);
        this.unsetJwt();
      }
    });
  }

  sudologin(){
    this.setJwt(new Jwt("admin"));
  }

}
