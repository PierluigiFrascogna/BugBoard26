import { Component, Input } from '@angular/core';
import { DescriptionChange } from '../change/description-change';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-description-change-card',
  imports: [DatePipe],
  templateUrl: './description-change-card.html',
  styleUrl: './description-change-card.css',
})
export class DescriptionChangeCard {
  @Input() descriptionChange!: DescriptionChange;
}
