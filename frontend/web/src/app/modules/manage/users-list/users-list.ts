import { Component, computed, inject } from '@angular/core';
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
  readonly users = computed(() => this.userStore.appUsers());
}
