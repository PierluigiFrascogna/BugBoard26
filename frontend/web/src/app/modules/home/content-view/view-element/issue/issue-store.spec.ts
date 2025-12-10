import { TestBed } from '@angular/core/testing';

import { IssueStore } from './issue-store';

describe('IssueStore', () => {
  let service: IssueStore;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssueStore);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
