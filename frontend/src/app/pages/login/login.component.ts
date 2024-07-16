import { AuthenticationResponse } from './../../services/models/authentication-response';
import { AuthenticationRequest } from 'src/app/services/models';
import { AuthService } from '../../auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/services/token.service';
import { Perform } from 'src/app/modules/utils/perform';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  authRequest: AuthenticationRequest = {
    email: '',
    password: '',
  };

  response = new Perform<AuthenticationResponse>();

  constructor(
    private authService: AuthService,
    private router: Router,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    const token = this.tokenService.token;
    if (token) {
      this.router.navigate(['books']);
    }
  }

  login() {
    this.response.load(
      this.authService.signin({
        body: this.authRequest,
      }),
      {
        next: (response) => {
          this.tokenService.token = response.token ?? '';
          this.router.navigate(['books']);
        },
      }
    );
  }
}
