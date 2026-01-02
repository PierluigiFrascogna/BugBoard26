import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddIssueCard } from './add-issue-card';

describe('AddIssueCard', () => {
  let component: AddIssueCard;
  let fixture: ComponentFixture<AddIssueCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddIssueCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddIssueCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
