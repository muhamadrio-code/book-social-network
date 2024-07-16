import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { UserRegisterRequest } from 'src/app/services/models';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnDestroy {
  registerRequest: UserRegisterRequest = {
    firstname: '',
    lastname: '',
    email: '',
    password: '',
  };

  isOnProcess = false;

  constructor(private authService: AuthService, private router: Router) {
    this.authService = authService;
    this.router = router;
  }

  ngOnDestroy(): void {}

  register() {
    this.isOnProcess = true;
    this.authService
      .register({
        body: this.registerRequest,
      })
      .subscribe({
        next: () => {
          this.isOnProcess = false;
          this.router.navigate(['activate']);
        },
        error: (err) => {
          this.isOnProcess = false;
          console.error(err);
        },
      });
  }
}
