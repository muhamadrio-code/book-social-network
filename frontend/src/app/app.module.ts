import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/pages/login/login.component';
import { RegisterComponent } from './auth/pages/register/register.component';
import {
  HttpHeaders,
  HttpInterceptorFn,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import { InputComponent } from './shared/components/input/input.component';
import { FormsModule } from '@angular/forms';
import { ActivateComponent } from './auth/pages/activate/activate.component';
import { BookModule } from './book/book.module';
import { ApiModule } from './api.module';

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
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    InputComponent,
    ActivateComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BookModule,
    ApiModule,
  ],
  providers: [provideHttpClient(withInterceptors([loggerInterceptor]))],
  bootstrap: [AppComponent],
})
export class AppModule {}
