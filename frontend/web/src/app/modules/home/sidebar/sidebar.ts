import { Component, computed, inject } from '@angular/core';
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
  
  isSidebarOpen: boolean = true; 
  private readonly projectsStore = inject(ProjectStore);
  readonly projects = computed<Project[]>(() => this.projectsStore.projects());

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  
}
