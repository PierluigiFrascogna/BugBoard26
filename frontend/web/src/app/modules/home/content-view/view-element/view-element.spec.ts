import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewElement } from './view-element';

describe('ViewElement', () => {
  let component: ViewElement;
  let fixture: ComponentFixture<ViewElement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewElement]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewElement);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
