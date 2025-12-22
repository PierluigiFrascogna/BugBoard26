import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCommentCard } from './add-comment-card';

describe('AddCommentCard', () => {
  let component: AddCommentCard;
  let fixture: ComponentFixture<AddCommentCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddCommentCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCommentCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
