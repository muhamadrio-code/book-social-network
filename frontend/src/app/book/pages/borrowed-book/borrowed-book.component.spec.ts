import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowedBookComponent } from './borrowed-book.component';

describe('BorrowedBookComponent', () => {
  let component: BorrowedBookComponent;
  let fixture: ComponentFixture<BorrowedBookComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BorrowedBookComponent]
    });
    fixture = TestBed.createComponent(BorrowedBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
