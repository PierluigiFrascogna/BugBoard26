import { inject, Injectable } from "@angular/core";
import { ENVIRONMENT_TOKEN } from "../../../environments/environment-model";
import { HttpClient } from "@angular/common/http";
import { JwtResponse } from "./JWT/jwt-response";
import { JwtRequest } from "./JWT/jwt-request";
import { IUserUpdate } from "../../modules/profile/user/user";

@Injectable({
  providedIn: 'root',
})
export class AuthApi {

  private readonly env = inject(ENVIRONMENT_TOKEN);
  private readonly http = inject(HttpClient);

  private readonly API_URL = this.env.urls.api;
  private readonly AUTH_URL = "/auth"

  login(request: JwtRequest) {
    return this.http.post<JwtResponse>(`${this.API_URL}${this.AUTH_URL}/login`, request);
  };

  modifyUser(update: IUserUpdate){
    return this.http.patch<JwtResponse>(`${this.API_URL}${this.AUTH_URL}/change`, update);
  }

}
