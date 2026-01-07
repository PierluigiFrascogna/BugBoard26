import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthStore } from '../auth-store';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  const auth = inject(AuthStore);

  if (!auth.isAuthenticated()) {
    return next(req);
  }

  const token = auth.JWT()!.token();

  return next(req.clone({
    headers: req.headers.append(
      `Authorization`, `Bearer ${token}`
    )
  }));
};
