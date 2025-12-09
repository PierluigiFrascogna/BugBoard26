import { Component, inject } from '@angular/core';
import { NgClass } from '@angular/common';
import { ProjectStore } from './project/project-store';

@Component({
  selector: 'app-sidebar',
  imports: [NgClass],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  
  private projectsStore = inject(ProjectStore);

  isSidebarOpen: boolean = true; 

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  constructor() {
    console.log("provaprovapeova", this.projectsStore.projects());
  }

}
