/* tslint:disable */

import { BorrowedBookResponse } from './borrowed-book-response';

/* eslint-disable */
export interface PageResponseBorrowedBookResponse {
  content?: Array<BorrowedBookResponse>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}
