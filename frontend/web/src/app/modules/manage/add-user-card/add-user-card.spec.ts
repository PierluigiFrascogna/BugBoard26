import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserCard } from './add-user-card';

describe('AddUserCard', () => {
  let component: AddUserCard;
  let fixture: ComponentFixture<AddUserCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddUserCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddUserCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
