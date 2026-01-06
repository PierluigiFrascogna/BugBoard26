import { computed, inject, Injectable, Signal } from '@angular/core';
import { UserApi } from './user-api';
import { AuthStore } from '../../../core/auth/auth-store';
import { INewUser, IUser, TUserRole } from './user';

export interface IUserState{
  appUsers: IUser[];
  loading: boolean; 
  error: Error | undefined;
}

@Injectable({
  providedIn: 'root',
})
export class UserStore {
  private readonly api = inject(UserApi);
  private auth = inject(AuthStore);
  
  readonly uuid: Signal<string | null> = computed(() => this.auth.uuid());
  readonly name: Signal<string | null>  = computed(() => this.auth.name());
  readonly surname: Signal<string | null>  = computed(() => this.auth.surname());
  readonly email: Signal<string | null> = computed(() => this.auth.email())
  readonly password: Signal<string | null> = computed(() => this.auth.password())
  readonly role: Signal<TUserRole | null> = computed(() => this.auth.role());

  private readonly isValid: Signal<boolean> = computed(() => {
    return [
      this.uuid(),
      this.name(),
      this.surname(),
      this.email(),
      this.password(),
      this.role()
    ].every((e) => e !== null);
  });

  readonly object: Signal<IUser | null> = computed(() => {
    if(!this.isValid()) {
      return null;
    }

    return {
      uuid: this.uuid()!,
      name: this.name()!,
      surname: this.surname()!,
      email: this.email()!,
      password: this.password()!,
      role: this.role()!
    }
  });

  private readonly _state = computed<IUserState>(() => ({
      appUsers: this.api.usersResource.hasValue() ? this.api.usersResource.value() :[] as IUser[],
      loading: this.api.usersResource.isLoading(),
      error: this.api.usersResource.error()
    })); 
  
    readonly appUsers = computed(() => this._state().appUsers);
    readonly loading = computed(() => this._state().loading); 
    readonly error = computed(() => this._state().error);
  
  createUser(user: INewUser){
    this.api.createUser(user).subscribe({
      next: (createdUser: IUser) => {

      },
      error: (err: Error) => {
        console.error('Error creating issue event: ', err);
      }
    })
  }

}
