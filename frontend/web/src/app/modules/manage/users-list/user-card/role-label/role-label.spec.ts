import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleLabel } from './role-label';

describe('RoleLabel', () => {
  let component: RoleLabel;
  let fixture: ComponentFixture<RoleLabel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoleLabel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoleLabel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
