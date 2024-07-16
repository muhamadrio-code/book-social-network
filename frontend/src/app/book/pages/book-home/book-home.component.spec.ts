import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookHomeComponent } from './book-home.component';

describe('BookListComponent', () => {
  let component: BookHomeComponent;
  let fixture: ComponentFixture<BookHomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookHomeComponent],
    });
    fixture = TestBed.createComponent(BookHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
