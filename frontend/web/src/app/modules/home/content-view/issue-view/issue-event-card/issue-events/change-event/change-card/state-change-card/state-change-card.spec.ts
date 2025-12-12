import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StateChangeCard } from './state-change-card';

describe('StateChangeCard', () => {
  let component: StateChangeCard;
  let fixture: ComponentFixture<StateChangeCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StateChangeCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StateChangeCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
