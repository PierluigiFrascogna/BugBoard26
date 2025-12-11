import { TestBed } from '@angular/core/testing';

import { IssueApi } from './issue-api';

describe('IssueApi', () => {
  let service: IssueApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssueApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
