import { Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-priority-label',
  imports: [NgClass],
  templateUrl: './priority-label.html',
  styleUrl: './priority-label.css',
})
export class PriorityLabel {
  @Input() priority!: "low" | "medium" | "high" | undefined;
}
