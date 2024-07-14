import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { TokenService } from 'src/app/services/services/token.service';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {
  constructor(private tokenService: TokenService) {
    this.tokenService = tokenService;
  }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.tokenService.token;
    console.log('=======INTERCEPT');

    if (!token) {
      const newReq = req.clone({
        headers: new HttpHeaders({
          Authorization: `Bearer ${token}`,
        }),
      });

      return next.handle(newReq);
    }

    return next.handle(req);
  }
}
