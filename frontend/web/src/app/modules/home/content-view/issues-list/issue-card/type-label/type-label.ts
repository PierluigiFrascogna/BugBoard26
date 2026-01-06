import { Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';
import { TIssueType } from '../issue/issue';
import { LowerCasePipe } from '@angular/common';

@Component({
  selector: 'app-type-label',
  imports: [NgClass, LowerCasePipe],
  templateUrl: './type-label.html',
  styleUrl: './type-label.css',
})
export class TypeLabel {
  @Input() type!: TIssueType;
}
