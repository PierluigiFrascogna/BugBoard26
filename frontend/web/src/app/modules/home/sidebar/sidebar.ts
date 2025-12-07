import { Component } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  imports: [NgClass],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  isSidebarOpen: boolean = true; 
  
  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

}
