/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../../shared/strict-http-response';
import { RequestBuilder } from '../../../shared/request-builder';

import { AuthenticationRequest } from '../../models/authentication-request';
import { AuthenticationResponse } from '../../models/authentication-response';

export interface Signin$Params {
  body: AuthenticationRequest;
}

export function signin(
  http: HttpClient,
  rootUrl: string,
  params: Signin$Params,
  context?: HttpContext
): Observable<StrictHttpResponse<AuthenticationResponse>> {
  const rb = new RequestBuilder(rootUrl, signin.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http
    .request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    )
    .pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<AuthenticationResponse>;
      })
    );
}

signin.PATH = '/auth/authenticate';
