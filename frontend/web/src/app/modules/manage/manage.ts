import { Component } from '@angular/core';
import { Navbar } from "../home/navbar/navbar";
import { AddUserCard } from "./add-user-card/add-user-card";
import { UsersList } from "./users-list/users-list";
import { AddProjectCard } from './add-project-card/add-project-card'; 

@Component({
  selector: 'app-manage',
  imports: [Navbar, AddUserCard, UsersList, AddProjectCard],
  templateUrl: './manage.html',
  styleUrl: './manage.css',
})
export class Manage {

}
