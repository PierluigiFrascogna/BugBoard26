import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssesList } from './issues-list';

describe('IssesList', () => {
  let component: IssesList;
  let fixture: ComponentFixture<IssesList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IssesList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IssesList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
