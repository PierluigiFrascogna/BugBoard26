import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueEventCard } from './issue-event-card';

describe('IssueEventCard', () => {
  let component: IssueEventCard;
  let fixture: ComponentFixture<IssueEventCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IssueEventCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IssueEventCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
