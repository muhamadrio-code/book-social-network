import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'app-pager',
  templateUrl: './pager.component.html',
  styleUrls: ['./pager.component.scss'],
})
export class PagerComponent implements OnInit, OnChanges {
  @Input() maxDisplayedPage: number = 5;
  @Input({ required: true }) totalPage!: number;
  @Input({
    required: true,
    transform: (value: number) => value + 1,
  })
  currentPage!: number;
  @Output('selectedPage') selectedPage = new EventEmitter<number>();

  pages: number[] = [];

  ngOnInit(): void {
    const displayedPage = Math.max(
      1,
      Math.min(this.maxDisplayedPage, this.totalPage)
    );
    this.pages = new Array(displayedPage);
    for (let i = 0; i < displayedPage; i++) {
      this.pages[i] = i + 1;
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['currentPage']) {
      const lastPage = this.pages[this.pages.length - 1];
      const firstPage = this.pages[0];

      if (this.currentPage > lastPage) {
        this.pages.push(this.currentPage);
        this.pages.shift();
      }

      if (this.currentPage < firstPage) {
        this.pages.unshift(this.currentPage);
        this.pages.pop();
      }
    }
  }

  setSelected(page: number) {
    if (page < 0 || page > this.totalPage) return;
    this.selectedPage.emit(page);
  }

  nextPage() {
    const page = this.currentPage;
    if (page > this.totalPage) return;
    this.setSelected(page);
  }

  prevPage() {
    const page = this.currentPage - 2;
    if (page < 0) return;
    this.setSelected(page);
  }
}
