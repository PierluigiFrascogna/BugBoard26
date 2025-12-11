import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeLabel } from './type-label';

describe('TypeLabel', () => {
  let component: TypeLabel;
  let fixture: ComponentFixture<TypeLabel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TypeLabel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TypeLabel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
