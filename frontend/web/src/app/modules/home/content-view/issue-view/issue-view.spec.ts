import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueView } from './issue-view';

describe('IssueView', () => {
  let component: IssueView;
  let fixture: ComponentFixture<IssueView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IssueView]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IssueView);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
