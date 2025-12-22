import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddChangeCard } from './add-change-card';

describe('AddChangeCard', () => {
  let component: AddChangeCard;
  let fixture: ComponentFixture<AddChangeCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddChangeCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddChangeCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
