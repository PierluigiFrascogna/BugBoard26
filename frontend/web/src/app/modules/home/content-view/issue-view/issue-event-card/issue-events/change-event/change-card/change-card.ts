import { Component, Input } from '@angular/core';
import { TitleChangeCard } from "./title-change-card/title-change-card";
import { DescriptionChangeCard } from "./description-change-card/description-change-card";
import { StateChangeCard } from "./state-change-card/state-change-card";
import { PriorityChangeCard } from "./priority-change-card/priority-change-card";
import { TChange } from './change/tchange';

@Component({
  selector: 'app-change-card',
  imports: [TitleChangeCard, DescriptionChangeCard, StateChangeCard, PriorityChangeCard],
  templateUrl: './change-card.html',
  styleUrl: './change-card.css',
})
export class ChangeCard {
  @Input() change!: TChange;


}
