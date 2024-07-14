import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/services';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss'],
})
export class ActivateComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.activatedRoute = activatedRoute;
    this.router = router;
    this.authService = authService;
  }

  ngOnInit(): void {
    const activateToken = this.activatedRoute.snapshot.queryParamMap.get('t');
    console.log(activateToken);

    if (activateToken) {
      this.authService
        .activate({
          token: activateToken,
        })
        .subscribe({
          next: () => {
            this.router.navigate(['login']);
          },
        });
    }
  }
}
