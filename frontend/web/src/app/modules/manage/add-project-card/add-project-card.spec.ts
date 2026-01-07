import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProjectCard } from './add-project-card';

describe('AddProjectCard', () => {
  let component: AddProjectCard;
  let fixture: ComponentFixture<AddProjectCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddProjectCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddProjectCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
