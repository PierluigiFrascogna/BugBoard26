import { Component, computed, inject, Input} from '@angular/core';
import { DatePipe, NgClass } from '@angular/common';
import { Project } from './project/project-model';
import { ProjectStore } from './project/project-store';
import { IssueStore } from '../../content-view/issues-list/issue-card/issue/issue-store';


@Component({
  selector: 'app-sidebar-element',
  imports: [DatePipe, NgClass],
  templateUrl: './sidebar-element.html',
  styleUrl: './sidebar-element.css',
})
export class SidebarElement {

  @Input() project!: Project; 

  private readonly projectStore = inject(ProjectStore);
  private readonly issueStore = inject(IssueStore);

  isSelected = computed(() => {
    const selectedProject = this.projectStore.selectedProject();
    return selectedProject === this.project;
  });

  selectProject(){
    this.issueStore.deselectIssue();
    this.projectStore.selectProject(this.project);
  }


}
