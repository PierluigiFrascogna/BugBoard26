import { Component, computed, inject, Input} from '@angular/core';
import { DatePipe, NgClass } from '@angular/common';
import { Project } from './project/project-model';
import { ProjectStore } from './project/project-store';


@Component({
  selector: 'app-sidebar-element',
  imports: [DatePipe, NgClass],
  templateUrl: './sidebar-element.html',
  styleUrl: './sidebar-element.css',
})
export class SidebarElement {

  @Input() project!: Project; 

  private readonly projectStore = inject(ProjectStore);
  isSelected = computed(() => {
    const selectedProject = this.projectStore.selectedProject();
    return selectedProject === this.project;
  });

  selectProject(){
    this.projectStore.selectProject(this.project);
  }


}
