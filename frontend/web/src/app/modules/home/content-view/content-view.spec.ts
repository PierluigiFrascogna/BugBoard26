import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentView } from './content-view';

describe('ContentView', () => {
  let component: ContentView;
  let fixture: ComponentFixture<ContentView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContentView]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContentView);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
