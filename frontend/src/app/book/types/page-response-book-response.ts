/* tslint:disable */

import { BookResponse } from './book-response';

/* eslint-disable */
export interface PageResponseBookResponse {
  content?: Array<BookResponse>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}
