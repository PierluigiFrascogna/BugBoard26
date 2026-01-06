import { Component, inject } from '@angular/core';
import { UserStore } from '../../profile/user/user-store';
import { IUser } from '../../profile/user/user';
import { UserCard } from "./user-card/user-card";

@Component({
  selector: 'app-users-list',
  imports: [UserCard],
  templateUrl: './users-list.html',
  styleUrl: './users-list.css',
})
export class UsersList {
  private readonly userStore = inject(UserStore);
  //readonly users = this.userStore.appUsers();
  readonly users = [{
      uuid: "user1",
      name: "nome1",
      surname: "cognome1",
      email: "mail1@fake.com",
      role: "admin"
    }as IUser,

    {
      uuid: "user2",
      name: "nome2",
      surname: "cognome2",
      email: "mail2@fake.com",
      role: "viewer",
    }as IUser,


    {
      uuid: "user3",
      name: "nome3",
      surname: "cognome3",
      email: "mail3@fake.com",
      role: "developer",
    }as IUser,

  ]as IUser[]

  
}
