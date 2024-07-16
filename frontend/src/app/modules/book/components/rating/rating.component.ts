import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
})
export class RatingComponent {
  @Input('rating') rating: number = 0.0;
  getClass(n: number): string {
    if (n > 0 && n < 1) return 'fas fa-star-half-stroke';
    if (n > 0) return 'fas fa-star';
    return 'fa-regular fa-star';
  }
}
