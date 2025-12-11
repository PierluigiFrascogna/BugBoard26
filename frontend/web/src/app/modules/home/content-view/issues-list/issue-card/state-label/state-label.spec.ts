import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StateLabel } from './state-label';

describe('StateLabel', () => {
  let component: StateLabel;
  let fixture: ComponentFixture<StateLabel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StateLabel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StateLabel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
