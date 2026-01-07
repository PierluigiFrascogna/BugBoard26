import { Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';
import { TIssuePriority } from '../issue/issue';
import { LowerCasePipe } from '@angular/common';

@Component({
  selector: 'app-priority-label',
  imports: [NgClass, LowerCasePipe],
  templateUrl: './priority-label.html',
  styleUrl: './priority-label.css',
})
export class PriorityLabel {
  @Input() priority!: TIssuePriority | undefined;
}
