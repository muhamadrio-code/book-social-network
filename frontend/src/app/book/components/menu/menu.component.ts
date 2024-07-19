import { KeycloakService } from './../../../keycloak/keycloak.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent {
  constructor(private keycloakService: KeycloakService) {}

  async logout() {
    this.keycloakService.logout();
  }
}
