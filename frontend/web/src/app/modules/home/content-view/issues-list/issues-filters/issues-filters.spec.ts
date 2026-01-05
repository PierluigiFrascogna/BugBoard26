import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssuesFilters } from './issues-filters';

describe('IssuesFilters', () => {
  let component: IssuesFilters;
  let fixture: ComponentFixture<IssuesFilters>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IssuesFilters]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IssuesFilters);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
