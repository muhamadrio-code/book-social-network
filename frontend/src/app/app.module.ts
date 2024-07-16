import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {
  HttpHeaders,
  HttpInterceptorFn,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import { BookModule } from './book/book.module';
import { ApiModule } from './api.module';
import { FormsModule } from '@angular/forms';

export const loggerInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');
  if (token) {
    const newReq = req.clone({
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
    });

    return next(newReq);
  }

  return next(req);
};

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, AppRoutingModule, BookModule, ApiModule],
  providers: [provideHttpClient(withInterceptors([loggerInterceptor]))],
  bootstrap: [AppComponent],
})
export class AppModule {}
