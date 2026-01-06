import { inject, Injectable } from '@angular/core';
import { ENVIRONMENT_TOKEN } from '../../../../environments/environment-model';
import { HttpClient } from '@angular/common/http';
import { INewUser, IUser } from './user';

@Injectable({
  providedIn: 'root',
})
export class UserApi {
  private readonly env = inject(ENVIRONMENT_TOKEN);
  private readonly http = inject(HttpClient);

  private readonly API_URL = this.env.urls.api;
  private readonly USERS_URL = "/users"

  createUser(user: INewUser){
    return this.http.post<IUser>(
      `${this.API_URL}${this.USERS_URL}`, user
    );
  }

}
