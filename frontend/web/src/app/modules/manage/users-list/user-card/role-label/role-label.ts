import { Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';
import { LowerCasePipe } from '@angular/common';

@Component({
  selector: 'app-role-label',
  imports: [NgClass, LowerCasePipe],
  templateUrl: './role-label.html',
  styleUrl: './role-label.css',
})
export class RoleLabel {
  @Input() role!: string;
}
