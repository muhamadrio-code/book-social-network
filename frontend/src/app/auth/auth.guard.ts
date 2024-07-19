import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from '../keycloak/keycloak.service';

export const authGuard = () => {
  const router = inject(Router);
  const token = inject(KeycloakService)?.keycloak.isTokenExpired;
  return token ?? router.parseUrl('/auth/login');
};
