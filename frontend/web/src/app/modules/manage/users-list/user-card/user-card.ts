import { Component, Input } from '@angular/core';
import { IUser } from '../../../profile/user/user';
import { RoleLabel } from "./role-label/role-label";

@Component({
  selector: 'app-user-card',
  imports: [RoleLabel],
  templateUrl: './user-card.html',
  styleUrl: './user-card.css',
})
export class UserCard {
  @Input() user!: IUser;

  deleteUser(){
    //TODO: chiamata allo userStore per eliminare l'utenza corrente
  }

}
