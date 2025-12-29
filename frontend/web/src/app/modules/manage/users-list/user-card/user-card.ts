import { Component, Input } from '@angular/core';
import { User } from '../../../profile/user/user';

@Component({
  selector: 'app-user-card',
  imports: [],
  templateUrl: './user-card.html',
  styleUrl: './user-card.css',
})
export class UserCard {
  @Input() user!: User;

  deleteUser(){
    //TODO: chiamata allo userStore per eliminare l'utenza corrente
  }

}
