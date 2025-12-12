import { Component, Input } from '@angular/core';
import { DescriptionChange } from '../change/description-change';

@Component({
  selector: 'app-description-change-card',
  imports: [],
  templateUrl: './description-change-card.html',
  styleUrl: './description-change-card.css',
})
export class DescriptionChangeCard {
  @Input() descriptionChange!: DescriptionChange;
}
