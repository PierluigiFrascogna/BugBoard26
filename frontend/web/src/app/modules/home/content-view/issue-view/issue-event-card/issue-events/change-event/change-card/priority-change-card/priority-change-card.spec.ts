import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriorityChangeCard } from './priority-change-card';

describe('PriorityChangeCard', () => {
  let component: PriorityChangeCard;
  let fixture: ComponentFixture<PriorityChangeCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PriorityChangeCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PriorityChangeCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
