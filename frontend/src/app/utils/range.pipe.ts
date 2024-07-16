import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'range',
})
export class RangePipe implements PipeTransform {
  transform(n: number): any {
    if (!n) {
      return [];
    }

    const arr = new Array(n);

    for (let i = 0; i < n; i++) {
      arr[i] = i + 1;
    }

    return arr;
  }
}
