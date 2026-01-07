import { Component } from '@angular/core';
import { Navbar } from "../home/navbar/navbar";
import { ProfileView } from './profile-view/profile-view';

@Component({
  selector: 'app-profile',
  imports: [Navbar, ProfileView],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile {

}
