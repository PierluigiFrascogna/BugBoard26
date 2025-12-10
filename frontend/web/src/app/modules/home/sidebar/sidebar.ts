import { Component, inject } from '@angular/core';
import { NgClass } from '@angular/common';
import { ProjectStore } from './sidebar-element/project/project-store';
import { Project } from './sidebar-element/project/project-model';
import { SidebarElement } from './sidebar-element/sidebar-element';

@Component({
  selector: 'app-sidebar',
  imports: [NgClass, SidebarElement],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  
  //private projectsStore = inject(ProjectStore);
  readonly projectsStore = [
    {
      uuid: "abcde",
      name: "Backend",
      creation_date: new Date(),
    } as Project,

    {
      uuid: "fghi",
      name: "Frontend dsdaassdasdasd",
      creation_date: new Date(),
    }as Project

  ]
  isSidebarOpen: boolean = true; 

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  
}
