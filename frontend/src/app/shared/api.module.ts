/* tslint:disable */
/* eslint-disable */
import {
  NgModule,
  ModuleWithProviders,
  SkipSelf,
  Optional,
  APP_INITIALIZER,
} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfiguration, ApiConfigurationParams } from './api-configuration';

import { BookService } from '../book/book.service';
import { AuthService } from '../auth/auth.service';
import { FeedbackService } from '../feedback/feedback.service';
import { KeycloakService } from '../keycloak/keycloak.service';

export function kcFactory(service: KeycloakService): () => Promise<any> {
  return () => service.init();
}

/**
 * Module that provides all services and configuration.
 */
@NgModule({
  imports: [],
  exports: [],
  declarations: [],
  providers: [
    FeedbackService,
    BookService,
    AuthService,
    ApiConfiguration,
    {
      useFactory: kcFactory,
      provide: APP_INITIALIZER,
      deps: [KeycloakService],
      multi: true,
    },
  ],
})
export class ApiModule {
  static forRoot(
    params: ApiConfigurationParams
  ): ModuleWithProviders<ApiModule> {
    return {
      ngModule: ApiModule,
      providers: [
        {
          provide: ApiConfiguration,
          useValue: params,
        },
      ],
    };
  }

  constructor(
    @Optional() @SkipSelf() parentModule: ApiModule,
    @Optional() http: HttpClient
  ) {
    if (parentModule) {
      throw new Error(
        'ApiModule is already loaded. Import in your base AppModule only.'
      );
    }
    if (!http) {
      throw new Error(
        'You need to import the HttpClientModule in your AppModule! \n' +
          'See also https://github.com/angular/angular/issues/20575'
      );
    }
  }
}
