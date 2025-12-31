import { Component, computed, EventEmitter, inject, output, Output, signal, WritableSignal } from '@angular/core';
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
  @Output() toggle = new EventEmitter<boolean>();
  
  private readonly _isOpen: WritableSignal<boolean> = signal(true);
  readonly isOpen = computed<boolean>(() => this._isOpen());

  private readonly projectsStore = inject(ProjectStore);
  readonly projects = computed<Project[]>(() => this.projectsStore.projects());

  toggleSidebar() {
    this._isOpen.update(isOpen => !isOpen);
    this.toggle.emit(this.isOpen());
  }
  
}
