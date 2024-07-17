import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from './token.service';

/**
 * An example of how to use authGuard to protect private route,
 * In real world scenario, this must be confirmed in backend side
 *
 * @returns true if token exist, otherwise redirect to `/auth/login`
 */
export const authGuard = () => {
  const router = inject(Router);
  const token = inject(TokenService).token;
  return token ?? router.parseUrl('/auth/login');
};
