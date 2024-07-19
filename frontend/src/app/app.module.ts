import { NgModule, inject } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {
  HttpHeaders,
  HttpInterceptorFn,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import { ApiModule } from './shared/api.module';
import { KeycloakService } from './keycloak/keycloak.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const service = inject(KeycloakService);
  const token = service.keycloak.token;

  if (token) {
    const newReq = req.clone({
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
    });

    return next(newReq);
  }

  throw Error('No token retrived, please contact administrator');
};

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, AppRoutingModule, ApiModule],
  providers: [provideHttpClient(withInterceptors([tokenInterceptor]))],
  bootstrap: [AppComponent],
})
export class AppModule {}
