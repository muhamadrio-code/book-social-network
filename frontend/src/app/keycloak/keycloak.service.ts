import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { UserProfile } from './user-profile';

@Injectable({
  providedIn: 'root',
})
export class KeycloakService {
  private _keycloak: Keycloak | undefined;

  public get keycloak(): Keycloak {
    if (!this._keycloak) {
      this._keycloak = new Keycloak({
        realm: 'book-social-network',
        clientId: 'bsn',
        url: 'http://localhost:9090',
      });
    }

    return this._keycloak;
  }

  async init() {
    await this.keycloak.init({
      onLoad: 'login-required',
    });
  }

  login() {
    return this._keycloak?.login();
  }

  logout() {
    return this._keycloak?.logout({
      redirectUri: 'http://localhost:4200',
    });
  }
}
