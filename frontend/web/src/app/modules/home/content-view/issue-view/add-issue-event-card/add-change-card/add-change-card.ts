import { Component, computed, Input, WritableSignal } from '@angular/core';
import { NgClass } from "@angular/common";

@Component({
  selector: 'app-add-change-card',
  imports: [NgClass],
  templateUrl: './add-change-card.html',
  styleUrl: './add-change-card.css',
})
export class AddChangeCard {
  @Input() currentVisibleCard!: WritableSignal<"change" | "comment">; 
  readonly isVisible = computed(() => this.currentVisibleCard()==="change");
}
