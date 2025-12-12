import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DescriptionChangeCard } from './description-change-card';

describe('DescriptionChangeCard', () => {
  let component: DescriptionChangeCard;
  let fixture: ComponentFixture<DescriptionChangeCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DescriptionChangeCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DescriptionChangeCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
