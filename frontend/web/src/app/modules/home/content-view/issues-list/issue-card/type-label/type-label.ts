import { Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';
import { IssueType } from '../issue/issue';

@Component({
  selector: 'app-type-label',
  imports: [NgClass],
  templateUrl: './type-label.html',
  styleUrl: './type-label.css',
})
export class TypeLabel {
  @Input() type!: IssueType | undefined;
}
