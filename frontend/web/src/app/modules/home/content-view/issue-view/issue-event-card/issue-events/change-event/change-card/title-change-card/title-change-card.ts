import { Component, Input } from '@angular/core';
import { TitleChange } from '../change/title-change';
import { TChange } from '../change/tchange';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-title-change-card',
  imports: [DatePipe],
  templateUrl: './title-change-card.html',
  styleUrl: './title-change-card.css',
})
export class TitleChangeCard {
  @Input() titleChange!: TitleChange;
}
