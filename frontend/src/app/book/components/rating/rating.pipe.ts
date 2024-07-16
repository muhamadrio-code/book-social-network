import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ratingRange',
})
export class RatingPipe implements PipeTransform {
  transform(rate: number, maxRating: number = 5): any {
    const array = new Array(maxRating);

    if (!rate || rate === 0.0) {
      return array;
    }

    for (let i = 0; i < maxRating; ++i) {
      array[i] = rate - i;
    }

    return array;
  }
}
