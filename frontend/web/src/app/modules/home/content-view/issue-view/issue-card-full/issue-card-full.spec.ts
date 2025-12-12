import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueCardFull } from './issue-card-full';

describe('IssueCardFull', () => {
  let component: IssueCardFull;
  let fixture: ComponentFixture<IssueCardFull>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IssueCardFull]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IssueCardFull);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
