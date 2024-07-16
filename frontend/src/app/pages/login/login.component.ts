import { AuthenticationRequest } from 'src/app/services/models';
import { AuthService } from './../../services/services/auth.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  authRequest: AuthenticationRequest = {
    email: '',
    password: '',
  };

  constructor(
    private authService: AuthService,
    private router: Router,
    private tokenService: TokenService
  ) {}

  login() {
    this.authService
      .signin({
        body: this.authRequest,
      })
      .subscribe({
        next: (response) => {
          console.log(response);

          this.tokenService.token = response.token ?? '';
          this.router.navigate(['books']);
        },
      });
  }
}
