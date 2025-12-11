import { TestBed } from '@angular/core/testing';

import { IssueEventApi } from './issue-event-api';

describe('IssueEventsApi', () => {
  let service: IssueEventApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssueEventApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
