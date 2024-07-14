import { AuthenticationRequest } from 'src/app/services/models';
import { AuthService } from './../../services/services/auth.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

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

  constructor(private authService: AuthService, private router: Router) {
    this.authService = authService;
    this.router = router;
  }

  login() {
    this.authService
      .signin({
        body: this.authRequest,
      })
      .subscribe({
        next: () => {
          this.router.navigate(['books']);
        },
      });
  }
}
