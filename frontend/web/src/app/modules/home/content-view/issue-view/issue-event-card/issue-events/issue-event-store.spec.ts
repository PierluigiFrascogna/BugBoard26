import { TestBed } from '@angular/core/testing';

import { IssueEventStore } from './issue-event-store';

describe('IssueEventsStore', () => {
  let service: IssueEventStore;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssueEventStore);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
