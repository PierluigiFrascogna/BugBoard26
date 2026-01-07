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
        this.api.usersResource.reload();
      },
      error: (err: Error) => {
        console.error(`Error creating user: ${user}, error: ${err}`);
      }
    })
  }

  deleteUser(userUuid: IUser['uuid']){
    this.api.deleteUser(userUuid).subscribe({
      next: () => {
        this.api.usersResource.reload();
      },
      error: (err: Error) => {
        console.error(`Error deleting user: ${userUuid}, error: ${err}`);
      }
    })
  }

}
