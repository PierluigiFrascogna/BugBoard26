import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthStore } from '../auth-store';

export const authGuard: CanActivateFn = (route, state) => {
  const auth = inject(AuthStore);
  const router = inject(Router);

  if (!auth.isAuthenticated()) {
    return router.createUrlTree(
      ['/login'],
      { queryParams: { returnUrl: state.url } }
    );
  }

  return true;
};

