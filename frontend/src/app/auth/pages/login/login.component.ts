import { AuthenticationResponse } from '../../types/authentication-response';
import { AuthService } from '../../auth.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Perform } from 'src/app/utils/perform';
import { TokenService } from '../../token.service';
import { AuthenticationRequest } from '../../types/authentication-request';

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
