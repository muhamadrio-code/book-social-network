/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { activate } from '../fn/auth/activate';
import { Activate$Params } from '../fn/auth/activate';
import { AuthenticationResponse } from '../models/authentication-response';
import { register } from '../fn/auth/register';
import { Register$Params } from '../fn/auth/register';
import { signin } from '../fn/auth/signin';
import { Signin$Params } from '../fn/auth/signin';

@Injectable({ providedIn: 'root' })
export class AuthService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `register()` */
  static readonly RegisterPath = '/auth/register';

  /**
   * Public endpoint.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `register()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  register$Response(
    params: Register$Params,
    context?: HttpContext
  ): Observable<StrictHttpResponse<{}>> {
    return register(this.http, this.rootUrl, params, context);
  }

  /**
   * Public endpoint.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `register$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  register(params: Register$Params, context?: HttpContext): Observable<{}> {
    return this.register$Response(params, context).pipe(
      map((r: StrictHttpResponse<{}>): {} => r.body)
    );
  }

  /** Path part for operation `signin()` */
  static readonly SigninPath = '/auth/authenticate';

  /**
   * Public endpoint.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `signin()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  signin$Response(
    params: Signin$Params,
    context?: HttpContext
  ): Observable<StrictHttpResponse<AuthenticationResponse>> {
    return signin(this.http, this.rootUrl, params, context);
  }

  /**
   * Public endpoint.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `signin$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  signin(
    params: Signin$Params,
    context?: HttpContext
  ): Observable<AuthenticationResponse> {
    return this.signin$Response(params, context).pipe(
      map(
        (
          r: StrictHttpResponse<AuthenticationResponse>
        ): AuthenticationResponse => r.body
      )
    );
  }

  /** Path part for operation `activate()` */
  static readonly ActivatePath = '/auth/activate-account';

  /**
   * Public endpoint.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `activate()` instead.
   *
   * This method doesn't expect any request body.
   */
  activate$Response(
    params: Activate$Params,
    context?: HttpContext
  ): Observable<StrictHttpResponse<{}>> {
    return activate(this.http, this.rootUrl, params, context);
  }

  /**
   * Public endpoint.
   *
   *
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `activate$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  activate(params: Activate$Params, context?: HttpContext): Observable<{}> {
    return this.activate$Response(params, context).pipe(
      map((r: StrictHttpResponse<{}>): {} => r.body)
    );
  }
}
