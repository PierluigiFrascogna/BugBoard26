import { Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-role-label',
  imports: [NgClass],
  templateUrl: './role-label.html',
  styleUrl: './role-label.css',
})
export class RoleLabel {
  @Input() role!: string;
}
