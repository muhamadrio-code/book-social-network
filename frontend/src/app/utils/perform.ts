import { delay, Observable, OperatorFunction, take } from 'rxjs';

export type ObserverOrNext<T> = {
  next?: ((value: T) => void) | null;
  error?: ((error: any) => void) | null;
  complete?: (() => void) | null;
};

export class Perform<T> {
  data: T | undefined;
  isLoading = false;
  hasError = false;
  private action$: Observable<T> | undefined;

  load(
    action$: Observable<T>,
    observerOrNext?: ObserverOrNext<T> | undefined,
    ...operators: OperatorFunction<any, any>[]
  ): void {
    this.isLoading = true;
    this.hasError = false;
    this.action$ = action$;
    this.action$.pipe(...(operators as []), take(1)).subscribe({
      next: (data: T) => {
        this.data = data;
        this.isLoading = false;
        this.hasError = false;
        observerOrNext?.next?.(data);
      },
      error: (err) => {
        this.data = undefined;
        this.isLoading = false;
        this.hasError = true;
        observerOrNext?.error?.(err);
      },
      complete: () => observerOrNext?.complete?.(),
    });
  }
}
