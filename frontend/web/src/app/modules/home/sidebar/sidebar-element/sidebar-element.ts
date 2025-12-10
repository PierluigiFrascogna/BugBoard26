import { Component, Input} from '@angular/core';
import { DatePipe } from '@angular/common';
import { Project } from './project/project-model';

@Component({
  selector: 'app-sidebar-element',
  imports: [DatePipe],
  templateUrl: './sidebar-element.html',
  styleUrl: './sidebar-element.css',
})
export class SidebarElement {
  @Input() project!: Project; 

}
