import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriorityLabel } from './priority-label';

describe('PriorityLabel', () => {
  let component: PriorityLabel;
  let fixture: ComponentFixture<PriorityLabel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PriorityLabel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PriorityLabel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
