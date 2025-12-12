import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TitleChangeCard } from './title-change-card';

describe('TitleChangeCard', () => {
  let component: TitleChangeCard;
  let fixture: ComponentFixture<TitleChangeCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TitleChangeCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TitleChangeCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
