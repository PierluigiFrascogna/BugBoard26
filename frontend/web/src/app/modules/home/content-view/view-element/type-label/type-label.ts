import { Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-type-label',
  imports: [NgClass],
  templateUrl: './type-label.html',
  styleUrl: './type-label.css',
})
export class TypeLabel {
  @Input() type!: "question" | "bug" | "documentation" | "feature";
}
