import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeCard } from './change-card';

describe('ChangeCard', () => {
  let component: ChangeCard;
  let fixture: ComponentFixture<ChangeCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChangeCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChangeCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
