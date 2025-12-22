import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddIssueEventCard } from './add-issue-event-card';

describe('AddIssueEventCard', () => {
  let component: AddIssueEventCard;
  let fixture: ComponentFixture<AddIssueEventCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddIssueEventCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddIssueEventCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
