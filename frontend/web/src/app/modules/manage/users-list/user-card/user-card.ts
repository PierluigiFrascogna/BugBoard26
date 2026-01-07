import { Component, inject, Input } from '@angular/core';
import { IUser } from '../../../profile/user/user';
import { RoleLabel } from "./role-label/role-label";
import { UserStore } from '../../../profile/user/user-store';

@Component({
  selector: 'app-user-card',
  imports: [RoleLabel],
  templateUrl: './user-card.html',
  styleUrl: './user-card.css',
})
export class UserCard {

  private readonly userStore = inject(UserStore);

  @Input() user!: IUser;

  deleteUser(){
    this.userStore.deleteUser(this.user.uuid);
  }

}
